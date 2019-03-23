package edu.chd.wanghl.service

import geotrellis.raster.io.geotiff.SinglebandGeoTiff
import org.apache.spark.SparkContext
import org.apache.spark.sql.{Row, SparkSession}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
  * @ Author     ：wanghl
  * @ Date       ：Created in 15:35 2019-3-22
  * @ Description：None
  * @ Modified By：
  */
@Service
class WordCountService {
  @Autowired
  private var sc:SparkContext = _

  @Autowired
  private var spark: SparkSession = _

  def getCount(wordList: List[String]):scala.collection.Map[String,Long] = {
    val words = sc.parallelize(wordList)
    val wordCounts = words.countByValue
    wordCounts
  }

  def getCrs(path: String):String = {
    import astraea.spark.rasterframes._
    implicit val ss = spark.withRasterFrames
    val geotiff = SinglebandGeoTiff(path)
    val rf = geotiff.projectedRaster.toRF
    val metadata = rf.tileLayerMetadata.left.get
    metadata.crs.toString()
  }
}
