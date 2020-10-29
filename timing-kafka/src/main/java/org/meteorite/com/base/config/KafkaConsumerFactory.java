package org.meteorite.com.base.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * @author EX_052100260
 * @title: KafkaConsumerFactory
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-10-27 14:37
 */
public class KafkaConsumerFactory {


   static KafkaConsumer<String, String> consumer;
    public static KafkaConsumer getKafkaConsumer(String group) {
        Properties props = new Properties();
//        props.put("bootstrap.servers", "localhost:9092");
        props.put("bootstrap.servers", "10.101.40.40:9092");
        //每个消费者分配独立的组号
        props.put("group.id", group);
        //如果value合法，则自动提交偏移量
        props.put("enable.auto.commit", "false");
        // 每次拉取60条
        props.put("max.poll.records", 20);
        //设置多久一次更新被消费消息的偏移量
        props.put("auto.commit.interval.ms", "100");
        //设置会话响应的时间，超过这个时间kafka可以选择放弃消费或者消费下一条消息
        props.put("session.timeout.ms", "30000");
        //自动重置offset
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        return consumer = new KafkaConsumer<String, String>(props);
    }

}
