package com.sugar.realprocess.bean

import com.alibaba.fastjson.JSON

/**
 * 创建时间:   2019/12/25 
 *
 * @author ZhangQing
 *         功能描述: 
 */
case class ClickLog(
                     var channelID: String,
                     var categoryID: String,
                     var produceID: String,
                     var country: String,
                     var province: String,
                     var city: String,
                     var network: String,
                     var source: String,
                     var browserType: String,
                     var entryTime: String,
                     var leaveTime: String,
                     var userID: String
                   )

object ClickLog {

  def apply(json: String): ClickLog = {
    val jSONObject = JSON.parseObject(json)
    val channelID = jSONObject.getString("channelID")
    val categoryID = jSONObject.getString("categoryID")
    val produceID = jSONObject.getString("produceID")
    val country = jSONObject.getString("country")
    val province = jSONObject.getString("province")
    val city = jSONObject.getString("city")
    val network = jSONObject.getString("network")
    val source = jSONObject.getString("source")
    val browserType = jSONObject.getString("browserType")
    val entryTime = jSONObject.getString("entryTime")
    val leaveTime = jSONObject.getString("leaveTime")
    val userID = jSONObject.getString("userID")

    ClickLog(channelID, categoryID, produceID, country, province, city, network, source, browserType, entryTime, leaveTime, userID)
  }

}