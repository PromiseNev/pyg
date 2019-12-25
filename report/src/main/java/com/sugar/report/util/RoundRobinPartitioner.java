package com.sugar.report.util;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建时间:   2019/12/25
 *
 * @author ZhangQing
 * 功能描述:
 */
public class RoundRobinPartitioner implements Partitioner {
    // 计数器，每次生产一条消息+1
    private AtomicInteger counter = new AtomicInteger();
    private String topic = "";

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 获取分区数量
        Integer partitions = cluster.partitionCountForTopic(topic);
        int curPartition = counter.incrementAndGet() % partitions;
        if (counter.get() > 65535) {
            counter.set(0);
        }
        return curPartition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
