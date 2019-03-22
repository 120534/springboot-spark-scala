package edu.chd.wanghl.service

import org.apache.spark.SparkContext
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
  private val sc:SparkContext = null

  def getCount(wordList: List[String]):scala.collection.Map[String,Long] = {
    val words = sc.parallelize(wordList)
    val wordCounts = words.countByValue
    wordCounts
  }
}
