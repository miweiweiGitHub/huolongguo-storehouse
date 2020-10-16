package org.meteorite.com.mq.sender;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author EX_052100260
 * @title: KafkaSender
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-10-10 9:43
 */
@Component
@Slf4j
public class KafkaSender {
    private final KafkaTemplate<String, String> KAFKA_TEMPLATE;

    @Autowired
    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.KAFKA_TEMPLATE = kafkaTemplate;
    }

    public void sendMessage(String topic, String message){

        ListenableFuture<SendResult<String, String>> sender = KAFKA_TEMPLATE.send(new ProducerRecord<>(topic, message));
//        //发送成功
//        SuccessCallback successCallback = result -> log.info("数据发送成功!");
//        //发送失败回调
//        FailureCallback failureCallback = ex -> log.error("数据发送失败!");

        sender.addCallback(result -> {}, ex -> log.error("数据发送失败!"));
    }
}
