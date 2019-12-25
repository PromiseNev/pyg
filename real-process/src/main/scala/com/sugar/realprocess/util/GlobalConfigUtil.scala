package com.sugar.realprocess.util

import com.typesafe.config.ConfigFactory

/**
 * 创建时间:   2019/12/25 
 *
 * @author ZhangQing
 *         功能描述:   加载配置文件工具类
 */
object GlobalConfigUtil {

  private val config = ConfigFactory.load()

  //  Kafka配置
  val bootstrapServers = config.getString("bootstrap.servers")
  val zookeeperConnect = config.getString("zookeeper.connect")
  val inputTopic = config.getString("input.topic")
  val groupId = config.getString("group.id")
  val enableAutoCommit = config.getString("enable.auto.commit")
  val autoCommitIntervalMs = config.getString("auto.commit.interval.ms")
  val autoOffsetReset = config.getString("auto.offset.reset")

  // 测试配置文件读取类
  def main(args: Array[String]): Unit = {
    println(bootstrapServers)
    println(zookeeperConnect)
    println(inputTopic)
    println(groupId)
    println(enableAutoCommit)
    println(autoCommitIntervalMs)
    println(autoOffsetReset)

  }

}
