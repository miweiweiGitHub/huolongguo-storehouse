package org.meteorite.com.mq.sender;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.meteorite.com.base.ExcelUtils;
import org.meteorite.com.domain.PushDataDto;

import java.util.*;

/**
 * @author EX_052100260
 * @title: SimpleKafkaProducer
 * @projectName huolongguo-storehouse
 * @description: 生产者
 * @date 2020-10-26 10:19
 */
@Slf4j
public class SimpleKafkaProducer {

    private static KafkaProducer<String, String> producer;
    private final static String TOPIC = "MSG_PUSH-TOPIC";

    public SimpleKafkaProducer() {
        Properties props = new Properties();
//        props.put("bootstrap.servers", "localhost:9092");
        props.put("bootstrap.servers", "10.101.40.93:9092");
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
        long start = System.currentTimeMillis();
        Set<String> dataByExcel = ExcelUtils.getDataByExcel("C:\\Users\\EX_052100260\\Documents\\WeChat Files\\wxid_to2prydnx9se22\\FileStorage\\File\\2020-11\\mobile (5).xlsx");
//        Set<String> dataByExcel = Collections.singleton("18566503223");
        dataByExcel.stream().forEach(p -> {

            producer.send(new ProducerRecord<String, String>(TOPIC, getUserData(p)));
        });
        log.info("produce end cost time :{}",System.currentTimeMillis()-start);
        producer.close();
    }

    private String getUserData(String p) {

        String key = UUID.randomUUID().toString().replace("-","");

        String title = "push优化升级测试";
        StringBuilder builder = new StringBuilder(p.substring(7));
        builder.append("用户你好，测试数据序号：");
        builder.append(key);
        builder.append(",打扰了。");

        JSONObject json = new JSONObject();
        PushDataDto pushDataDto = new PushDataDto();
        pushDataDto.setContent(builder.toString());
        pushDataDto.setTitle(title);
        pushDataDto.setPhone(Collections.singleton(p));
        Map<String, String> exdata = new HashMap<>();
        exdata.put("jumpType","0");
        exdata.put("method","1000");
        pushDataDto.setExtData(exdata);
        json.put("operation","SEND_BY_ALIAS");
        json.put("refId","2020110416");
        json.put("priority",0);
        json.put("pushData",pushDataDto);
        json.put("multiData",null);
        json.put("type2","0");

        return json.toJSONString();
    }


}
