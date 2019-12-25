package com.sugar.report;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 创建时间:   2019/12/25
 *
 * @author ZhangQing
 * 功能描述:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaTest {
    @Autowired
    KafkaTemplate kafkaTemplate;

    @Test
    public void sendMsg() {
        for (int i = 0; i < 100; i++) {
            kafkaTemplate.send("test", "key", "hello,testkafka");

        }
    }

}
