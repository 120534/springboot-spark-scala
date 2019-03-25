package edu.chd.wanghl.controller

import java.io.{File, IOException}
import java.nio.file.{Files, Paths}

import edu.chd.wanghl.service.SparkService
import edu.chd.wanghl.util.Utils.outPath
import javax.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.{ClassPathResource, FileUrlResource}
import org.springframework.http.MediaType
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam, RestController}

import scala.tools.nsc.interpreter.InputStream

/**
  * @ Author     ：wanghl
  * @ Date       ：Created in 15:34 2019-3-22
  * @ Description：None
  * @ Modified By：
  */
@RestController
class SparkController {

  @Autowired
  private var service: SparkService = _

  @RequestMapping(method = Array(RequestMethod.POST), path = Array("/wordcount"))
  def count(@RequestParam words: String): java.util.Map[String, Long] = {
    val seperator = raw"/"
    val list = words.split(seperator).toList
    import scala.collection.JavaConversions._
    service.getCount(list)
  }

  //输入文件名，获取其投影信息
  @RequestMapping(method = Array(RequestMethod.GET), path = Array("/raster"))
  def getCrs(@RequestParam name: String): String = {
    val path = "data/" + name
    val uri = new File(path).getAbsolutePath
    service.getCrs(uri)
  }

  @RequestMapping(method = Array(RequestMethod.POST), path = Array("/ndvi"))
  def getNdvi(@RequestParam name: String)= {
    //for example, the name is "L8-Elkton-VA"
    service.getNdvi(name)
  }

  @RequestMapping(value = Array("/png"), method = Array(RequestMethod.GET), produces = Array(MediaType.IMAGE_PNG_VALUE))
  @throws[IOException]
  def getImage(@RequestParam name: String, response: HttpServletResponse): Unit = {

    import edu.chd.wanghl.util.Utils._
    if (!Files.exists(Paths.get(outPath(name, "png")))) service.getNdvi(name)

    response.setContentType(MediaType.IMAGE_PNG_VALUE)
    val imgFile = new FileUrlResource(outPath(name, "png"))
    StreamUtils.copy(imgFile.getInputStream, response.getOutputStream)
  }
}
