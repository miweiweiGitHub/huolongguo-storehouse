package org.meteorite.com.mq.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.meteorite.com.base.contants.Contants;
import org.meteorite.com.domain.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author EX_052100260
 * @title: KafkaListeners
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-10-10 9:46
 */
@Slf4j
@Component
public class KafkaListeners {
    @KafkaListener(containerFactory = "kafkaBatchListener6",topics = {"#{'${kafka.listener.topics}'.split(',')[0]}"})
    public void batchListener(List<ConsumerRecord<?,?>> records, Acknowledgment ack){

        List<User> userList = new ArrayList<>();
        try {
            records.forEach(record -> {
                User user = JSON.parseObject(record.value().toString(),User.class);
                user.getCreateTime().format(DateTimeFormatter.ofPattern(Contants.DateTimeFormat.DATE_TIME_PATTERN));
                userList.add(user);
            });
        } catch (Exception e) {
            log.error("Kafka监听异常"+e.getMessage(),e);
        } finally {
            ack.acknowledge();//手动提交偏移量
        }

    }
}