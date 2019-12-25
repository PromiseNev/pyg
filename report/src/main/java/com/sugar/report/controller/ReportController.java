package com.sugar.report.controller;

import com.alibaba.fastjson.JSON;
import com.sugar.report.bean.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间:   2019/12/25
 *
 * @author ZhangQing
 * 功能描述:
 */
@RestController
public class ReportController {
    @Value("${kafka.topic}")
    private String topic;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping("/receive")
    public Map receive(@RequestBody String json) {
        Map<String, String> result = new HashMap<>();

        try {
            Message msg = new Message();

            msg.setMessage(json);
            msg.setCount(1L);
            msg.setTimeStamp(System.currentTimeMillis());

            String msgJSON = JSON.toJSONString(msg);

            kafkaTemplate.send(topic, msgJSON);
            System.out.println(msgJSON);
            result.put("success", "true");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
        }
        return result;

    }


}
