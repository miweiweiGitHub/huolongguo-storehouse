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
            String refId = "";
            String phone = "18566503223";
            String title = "{\"age\":24,\"brokerId\":1271280149131808800,\"brokerType\":0,\"createTime\":1591931781000,\"eventType\":\"login\",\"gender\":1,\"jpushId\":\"18171adc03ba55be8a9\",\"lbsCityCode\":\"440300\",\"memberLevel\":\"普通会员\",\"memberLevelCode\":0,\"mobilePhone\":\"13614785204\",\"name\":\"卢奇\",\"osType\":\"Web\",\"siteCityCode\":\"440300\",\"unionId\":\"3BF613132F14DEEB794BE9361049D6B7\",\"userType\":5}";
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
