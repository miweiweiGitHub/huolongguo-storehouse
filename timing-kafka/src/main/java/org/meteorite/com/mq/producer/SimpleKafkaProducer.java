package org.meteorite.com.mq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.meteorite.com.base.contant.CommonContant;

import java.util.Properties;
import java.util.UUID;

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


    public SimpleKafkaProducer() {
        Properties props = new Properties();
        //dev
        props.put("bootstrap.servers", "10.101.40.40:9092");
        //sit
//        props.put("bootstrap.servers", "10.101.40.93:9092");
        //本地
//        props.put("bootstrap.servers", "localhost:9092");
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
        for (int i = 0; i < 10; i++) {
            String key = UUID.randomUUID().toString();
            String refId = "{\"event\":\"visit_protect_over\",\"extEventParam\":{\"customerId\":\"1288594340561641472\"},\"messageParam\":{\"IMAIL\":{\"var1\":\"开哪上\"},\"PUSH\":{\"var1\":\"开哪上\"}},\"receiver\":[{\"uid\":\"1270183428859375616\",\"phone\":\"18476543082\"}]}";
            String phone = "18566503223";
            String title = "{\"appVersion\":\"1.0.0\",\"createTime\":1603173936000,\"customerId\":1318433163286106113,\"eventType\":\"login\",\"jpushId\":\"121c83f7609ec0df517\",\"lbsCityCode\":\"440300\",\"mobilePhone\":\"18676362041\",\"osType\":\"IOS\",\"siteCityCode\":\"440300\",\"unionId\":\"23E0CB21260CB04798FAB0D69A652415\"}";
            StringBuilder builder = new StringBuilder("{\"operation\":\"SEND_BY_ALIAS\",\"refId\":\"");
           builder.append("\",\"priority\":99,\"pushData\":{\"tags\":null,\"phone\":[");
           builder.append("\"],\"title\":\"");
           builder.append("\",\"subTitle\":null,\"content\":\"测试内容，请忽略"+key+"\",\"sendTime\":null,\"extData\":{\"jumpType\":\"2\",\"imageUrl\":\"\"},\"type2\":null},\"multiData\":null,\"type2\":\"0\"}");

            producer.send(new ProducerRecord<String, String>(CommonContant.MSG_TOPIC, title));

            log.info("data");
        }
        producer.close();
    }

    public static void main(String[] args) {
        new SimpleKafkaProducer().produce();
    }
}
