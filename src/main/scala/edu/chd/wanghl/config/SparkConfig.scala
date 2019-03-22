package edu.chd.wanghl.config

import org.apache.spark.{SparkConf, SparkContext}
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * @ Author     ：wanghl
  * @ Date       ：Created in 15:25 2019-3-22
  * @ Description：None
  * @ Modified By：
  */
@Configuration
class SparkConfig {

  @Value("${spark.name}")
  private var appName:String = _
  @Value("${spark.master}")
  private var masterUri:String = _
  //@Value("${spark.home}")
  //private String sparkHome;

  @Bean
  def conf: SparkConf = {
    new SparkConf().setAppName(appName).setMaster(masterUri)
  }

  @Bean
  def sc = new SparkContext(conf)

}
