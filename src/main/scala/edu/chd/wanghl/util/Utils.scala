package edu.chd.wanghl.util

import com.typesafe.config.ConfigFactory
import geotrellis.raster.io.geotiff.SinglebandGeoTiff
import geotrellis.raster.render.ColorMap
import org.apache.spark.sql.SparkSession

/**
  * @ Author     ：wanghl
  * @ Date       ：Created in 9:31 2019-3-25
  * @ Description：None
  * @ Modified By：
  */
object Utils {
  def readTiff(name: String): SinglebandGeoTiff = SinglebandGeoTiff(s"$name" + ".tiff")
  val ndvi_colorMap:ColorMap = ColorMap.fromStringDouble(ConfigFactory.load().getString("colorMap.ndvi")).get
  val ndwi_colorMap:ColorMap = ColorMap.fromStringDouble(ConfigFactory.load().getString("colorMap.ndwi")).get
  //设置输出路径
  def outPath(name: String, t: String):String = "src/main/resources/image/" + name + "." + t
}
