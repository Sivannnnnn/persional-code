package com.liuxw.kafka.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 自定义分区，若消息的key是important，则划入指定分区，没指定key就按默认方式分区。
 * Author  XiaoWen
 * Date  2019/8/5 14:27
 * Version 1.0
 */
public class MyPartitioner implements Partitioner {

    private Random random;

    public int partition(String topic, Object keyObj, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        String key = (String) keyObj;
        List<PartitionInfo> partitionInfos = cluster.availablePartitionsForTopic(topic);
        int partitionCount = partitionInfos.size();
        int importantPartation = partitionCount - 1;
        return key == null || key.isEmpty() || !key.contains("important") ?
                random.nextInt(partitionCount - 1) : importantPartation;
    }

    public void close() {

    }

    public void configure(Map<String, ?> configs) {
        //实现必要的初始化工作
        random = new Random();
    }
}
