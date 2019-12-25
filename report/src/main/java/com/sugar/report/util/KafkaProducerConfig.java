package com.sugar.report.util;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间:   2019/12/25
 *
 * @author ZhangQing
 * 功能描述:
 */
@Configuration
public class KafkaProducerConfig {
    @Value("${kafka.bootstrap_servers_config}")
    private String bootstrap_servers_config;

    @Value("${kafka.retries_config}")
    private int retries_config;

    @Value("${kafka.batch_size_config}")
    private int batch_size_config;

    @Value("${kafka.linger_ms_config}")
    private int linger_ms_config;

    @Value("${kafka.buffer_memory_config}")
    private int buffer_memory_config;

    @Bean
    public KafkaTemplate kafkaTemplate() {
        Map<String, Object> confs = new HashMap<>();
        confs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_servers_config);
        confs.put(ProducerConfig.RETRIES_CONFIG, retries_config);
        confs.put(ProducerConfig.BATCH_SIZE_CONFIG, batch_size_config);
        confs.put(ProducerConfig.LINGER_MS_CONFIG, linger_ms_config);
        confs.put(ProducerConfig.BUFFER_MEMORY_CONFIG, buffer_memory_config);
        confs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        confs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        confs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class);

        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(confs);
        return new KafkaTemplate(producerFactory);
    }
}