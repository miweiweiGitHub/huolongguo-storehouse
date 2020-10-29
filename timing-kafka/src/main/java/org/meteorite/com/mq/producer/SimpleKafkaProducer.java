package org.meteorite.com.mq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author EX_052100260
 * @title: SimpleKafkaProducer
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-10-27 14:40
 */
@Slf4j
public class SimpleKafkaProducer {
    private static KafkaProducer<String, String> producer;
    private final static String TOPIC = "test";

    public SimpleKafkaProducer() {
        Properties props = new Properties();
//        props.put("bootstrap.servers", "10.101.40.40:9092");
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        //设置分区类,根据key进行数据分区
        producer = new KafkaProducer<String, String>(props);
    }

    public void produce() {
        for (int i = 0; i < 1000; i++) {
            String key = String.valueOf(System.currentTimeMillis());
            String data = "hello kafka message：" + key;
            producer.send(new ProducerRecord<String, String>(TOPIC, key, data));
            log.info(data);
        }
        producer.close();
    }

    public static void main(String[] args) {
        new SimpleKafkaProducer().produce();
    }
}
