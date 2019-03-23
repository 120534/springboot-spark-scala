package edu.chd.wanghl.controller

import java.io.File

import edu.chd.wanghl.service.WordCountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam, RestController}

/**
  * @ Author     ：wanghl
  * @ Date       ：Created in 15:34 2019-3-22
  * @ Description：None
  * @ Modified By：
  */
@RestController
class WordCountController {

  @Autowired
  private var service:WordCountService = _

  @RequestMapping(method = Array(RequestMethod.POST), path = Array("/wordcount"))
  def count(@RequestParam(required = false) words: String):java.util.Map[String,Long] = {
    val seperator = raw"/"
    val list = words.split(seperator).toList
    import scala.collection.JavaConversions._
    service.getCount(list)
  }

  //输入文件名，获取其投影信息
  @RequestMapping(method = Array(RequestMethod.GET), path = Array("/raster"))
  def getCrs(@RequestParam(required = false) name: String):String = {
    val path = "data/" + name
    val uri = new File(path).getAbsolutePath
    service.getCrs(uri)
  }
}
