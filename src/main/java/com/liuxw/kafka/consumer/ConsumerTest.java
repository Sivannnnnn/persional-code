package com.liuxw.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Author  XiaoWen
 * Date  2019/8/6 16:45
 * Version 1.0
 */
public class ConsumerTest {

    public static KafkaConsumer getConsumer() {
        String topicName = "kafka-test";
        String groupID = "";

        Properties properties = new Properties();
        properties.put("bootstrap.servers","192.168.0.33:9092");
        properties.put("group.id",groupID);
        properties.put("enable.auto.commit","true");
        properties.put("auto.commit.interval.ms","true");
        properties.put("auto.commit.interval.ms","1000");
        // 从最早的消息开始读取
        properties.put("auto.offset.reset","earliest");
        // key反序列化
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeSerializer");
        // value反序列化
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeSerializer");

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList(topicName));

        return consumer;
    }

    public static void main(String[] args) {

        KafkaConsumer consumer = getConsumer();

        while (true) {
            ConsumerRecords<String,String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("offset=%d");
            }
        }

    }

}
