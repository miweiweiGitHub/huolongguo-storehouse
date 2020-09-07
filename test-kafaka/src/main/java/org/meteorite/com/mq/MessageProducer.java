package org.meteorite.com.mq;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.meteorite.com.mq.listener.KafkaSendListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * kafka消息生产类
 */
@Component
@Slf4j
public class MessageProducer {

	@Autowired
	private KafkaTemplate kafkaTemplate;
	@Autowired
	private KafkaSendListener kafkaSendListener;

	public void send(String topic, Object obj) {
		log.info("messageProducer send begin.");
		if (StringUtils.isEmpty(topic)) {
			log.info("messageProducer send end. no topic");
			return;
		}
		kafkaTemplate.setProducerListener(kafkaSendListener);	// 设置回调
		kafkaTemplate.send(topic, JSONObject.toJSONString(obj));	// 发送
		log.info("messageProducer send end.");

//		"{\"event\":\"customer_buy_remind\",\"messageParam\":{\"SMS\":{}},\"receiver\":[{\"phone\":\"18566503223\"}]}"
	}
}
