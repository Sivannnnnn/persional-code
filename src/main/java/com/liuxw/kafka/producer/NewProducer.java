package com.liuxw.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.RetriableException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Author  XiaoWen
 * Date  2019/7/29 17:37
 * Version 1.0
 */
public class NewProducer {
    private static Logger logger = LogManager.getLogger(NewProducer.class);

   public static KafkaProducer getProducer() {

       Properties props = new Properties();
       // Kafka服务端的主机名和端口号
       props.put("bootstrap.servers", "192.168.0.31:9092");
       // 等待所有副本节点的应答
       props.put("acks", "1");
       // 消息发送最大尝试次数
       props.put("retries", 0);
       // 一批消息处理大小
       props.put("batch.size", 16384);
       // 请求延时
       props.put("linger.ms", 1);
       // 发送缓存区内存大小,producer端缓冲区大小，默认就是32MB
       props.put("buffer.memory", 33554432);
       // key序列化
       props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
       // value序列化
       props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
       //自定义分区器
       props.put("partitioner.class","com.liuxw.kafka.producer.MyPartitioner");

       KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

       return producer;

   }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        KafkaProducer producer = getProducer();

        for (int i = 1; i < 10; i++) {

            //不理会结果，直接发送
            //生产者可以指定时间戳，但最好不要指定，会打乱kafka，影响消息查找及保留时间
//            producer.send(new ProducerRecord("kafka_test",String.valueOf(i),String.valueOf(i)));


            //异步发送
//        producer.send(new ProducerRecord("kafka_test", String.valueOf(i), String.valueOf(i)), new Callback() {
//            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                if (exception == null) {
//                    //发送成功了不处理
//                }else {
//                    //发送失败，按不同异常分类处理
//                    if (exception instanceof RetriableException) {
//                        //处理可重试瞬时异常
//                    } else {
//                        //不可重试异常
//                    }
//                }
//            }
//        });

            //同步发送,调用get方法返回。如果发送成功，则返回RecordMetadata,反之返回exception
            Object result = producer.send(new ProducerRecord("kafka_test", "important", String.valueOf(i))).get();
            RecordMetadata recordMetadata = (RecordMetadata) result;
            logger.info("分区为"+recordMetadata.partition() + ">>>>topic为"+recordMetadata.topic());

        }


        producer.close();
    }

}
