package edu.chd.wanghl.config

import org.apache.spark.{SparkConf, SparkContext}
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.{Bean, Configuration, PropertySource}

/**
  * @ Author     ：wanghl
  * @ Date       ：Created in 15:25 2019-3-22
  * @ Description：None
  * @ Modified By：
  */
@Configuration
@PropertySource(Array("classpath:spark.properties"))
@ConfigurationProperties(prefix = "spark")
class SparkConfig {

  private var appName:String = _
  private var master:String = _

  def getAppName: String = appName
  def setAppName(appName: String): Unit = this.appName = appName
  def getMaster: String = master
  def setMaster(master: String): Unit = this.master = master

  @Bean
  def conf: SparkConf = {
    new SparkConf().setAppName(appName).setMaster(master)
  }

  @Bean
  def sc = new SparkContext(conf)
}
