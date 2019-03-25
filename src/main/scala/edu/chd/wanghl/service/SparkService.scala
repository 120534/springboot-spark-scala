package edu.chd.wanghl.service

import geotrellis.raster.io.geotiff.SinglebandGeoTiff
import org.apache.spark.SparkContext
import org.apache.spark.sql.{Row, SparkSession}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import astraea.spark.rasterframes._
import edu.chd.wanghl.util.Utils.{ndvi_colorMap, outPath, readTiff}
import geotrellis.raster.render.{ColorMap, ColorRamp, ColorRamps}
import geotrellis.raster.{Raster, Tile}
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.util.StreamUtils

/**
  * @ Author     ：wanghl
  * @ Date       ：Created in 15:35 2019-3-22
  * @ Description：None
  * @ Modified By：
  */
@Service
class SparkService {
  @Autowired
  private var sc: SparkContext = _

  @Autowired
  private var spark: SparkSession = _

  def getCount(wordList: List[String]): scala.collection.Map[String, Long] = {
    val words = sc.parallelize(wordList)
    val wordCounts = words.countByValue
    wordCounts
  }

  def getCrs(path: String): String = {
    //这段代码可以调整到config
    implicit val ss = spark.withRasterFrames
    val geotiff = SinglebandGeoTiff(path)
    val rf = geotiff.projectedRaster.toRF
    val metadata = rf.tileLayerMetadata.left.get
    metadata.crs.toString()
  }

  def getNdvi(name: String) = {
    //for example, the name is "L8-Elkton-VA.tiff"
    implicit val ss = spark.withRasterFrames
    import ss.implicits._
    //根据传入的影像名称，获取其每个波段对应的路径信息
    val seq = name.split("-").toList
    val newList = seq.head :: "B%d" :: seq.tail
    val filePattern = "data/" + newList.mkString("-")

    val bandNums = (4 to 5).toList

    val joinedRF = bandNums.
      map{ b =>(b, filePattern.format(b))}.
      map{ case (b, f) => (b, readTiff(f))}.
      map{case (b, t) => t.projectedRaster.toRF(s"band_$b")}.
      reduce( _ spatialJoin _)

    val metadata = joinedRF.tileLayerMetadata.left.get
    val tlm = metadata.tileLayout

    val rf = joinedRF.withColumn("ndvi",
      normalized_difference(convert_cell_type($"band_5", "float32"),
        convert_cell_type($"band_4", "float32"))).asRF

    val raster_ndvi = rf.toRaster($"ndvi", tlm.totalCols.toInt, tlm.totalRows.toInt)

    import edu.chd.wanghl.util.Utils._
    raster_ndvi.tile.renderPng(ndvi_colorMap).write(outPath(name, "png"))
    SinglebandGeoTiff(raster_ndvi, metadata.extent, metadata.crs).write(outPath(name, "tiff"))
  }

  def getPNG(name: String): Unit = {

  }
}