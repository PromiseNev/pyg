package com.sugar.realprocess

import java.util.Properties

import com.alibaba.fastjson.JSON
import com.sugar.realprocess.bean.{ClickLog, Message}
import com.sugar.realprocess.util.GlobalConfigUtil
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010

/**
 * 创建时间:   2019/12/25 
 *
 * @author ZhangQing
 *         功能描述: 
 */
object App {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    // 设置流的处理时间为事件时间
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)
    // 本地测试,
    /*   val localDataStream = env.fromCollection(List("flink", "hadoop", "spark", "flink"))
       localDataStream.print()*/
    // 添加ck支持
    env.enableCheckpointing(5000)
    // 设置ck模式
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    //设置两次ck的最小时间间隔
    env.getCheckpointConfig.setMinPauseBetweenCheckpoints(1000L)
    // 设置ck的超时时长
    env.getCheckpointConfig.setCheckpointTimeout(60000L)
    // 设置ck的并行度
    env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)
    // 当程序关闭时,触发额外的ck
    env.getCheckpointConfig.enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)
    // 设置ck的地址
    env.setStateBackend(new FsStateBackend("hdfs://cdh01:8020/flink_ck/"))

    val prop = new Properties()
    prop.setProperty("bootstrap.servers", GlobalConfigUtil.bootstrapServers)
    prop.setProperty("zookeeper.connect", GlobalConfigUtil.zookeeperConnect)
    prop.setProperty("input.topic", GlobalConfigUtil.inputTopic)
    prop.setProperty("group.id", GlobalConfigUtil.groupId)
    prop.setProperty("enable.auto.commit", GlobalConfigUtil.enableAutoCommit)
    prop.setProperty("auto.commit.interval.ms", GlobalConfigUtil.autoCommitIntervalMs)
    prop.setProperty("auto.offset.reset", GlobalConfigUtil.autoOffsetReset)

    val kafka_data = new FlinkKafkaConsumer010[String](GlobalConfigUtil.inputTopic, new SimpleStringSchema, prop)

    val kafkaStream = env.addSource(kafka_data)

    val tupleDataStream = kafkaStream.map(msg => {
      val msg_bean = JSON.parseObject(msg)

      val message = msg_bean.getString("message")
      val count = msg_bean.getLong("count")
      val timeStamp = msg_bean.getLong("timeStamp")

      //(ClickLog(message), count, timeStamp)
      Message((ClickLog(message)), count, timeStamp)
    }
    )

    tupleDataStream.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[Message] {
      var biggerTime = 0L
      //延迟时间
      val maxDelay = 2000L

      override def getCurrentWatermark = {
        new Watermark(biggerTime - maxDelay)
      }

      override def extractTimestamp(element: Message, previousElementTimestamp: Long) = {
        biggerTime = Math.max(element.timestamp, previousElementTimestamp)
        biggerTime
      }
    })
    tupleDataStream.print()
    //kafkaStream.print()
    env.execute("realprocess")

  }
}