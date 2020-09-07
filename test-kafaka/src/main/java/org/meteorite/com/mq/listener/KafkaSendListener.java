package org.meteorite.com.mq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaSendListener implements ProducerListener {
	@Override
	public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
		log.info("消息主题：【{}】，消息体: {}", producerRecord.topic(), producerRecord.value());
	}

	@Override
	public void onError(ProducerRecord producerRecord, Exception e) {
		log.error("消息主题：【{}】，消息体：{}，异常信息：{}", producerRecord.topic(), producerRecord.value(), e.getMessage());
	}
}
