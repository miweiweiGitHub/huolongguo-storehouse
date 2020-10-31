package org.meteorite.com.base.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author EX_052100260
 * @title: MqConfig
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-10-26 11:44
 */
@Configuration
@EnableKafka
@Slf4j
public class MqConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private Boolean autoCommit;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @Value("${spring.kafka.consumer.session-timeout}")
    private String sessionTimeout;

    @Value("${spring.kafka.consumer.auto-commit-interval}")
    private String autoCommitInterval;

    @Value("${spring.kafka.consumer.max-poll-records}")
    private String maxPollRecords;

    @Value("${spring.kafka.consumer.max-poll-interval}")
    private String maxPollInterval;

    @Value("${spring.kafka.listener.concurrency}")
    private Integer concurrency;

    @Value("${kafka.app.topic.test1}")
    private String test1Topic;

    @Value("${kafka.app.topic.test2}")
    private String test2Topic;

    @Value("${kafka.app.topic.test3}")
    private String test3Topic;

    @Value("${kafka.consumer.offset}")
    private Boolean offset;

    @Value("${kafka.consumer.frequency}")
    private Long frequency;

    @Bean
    public KafkaListenerContainerFactory kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory factory =new ConcurrentKafkaListenerContainerFactory();
        factory.setConcurrency(concurrency);
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setPollTimeout(300000);
        return factory;
    }

    public Map consumerConfigs() {
        Map props =new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,autoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,autoCommitInterval);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,maxPollRecords);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,maxPollInterval);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,sessionTimeout);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,valueDeserializer);
        return props;
    }

    @Bean
    public ConsumerFactory consumerFactory(){
        DefaultKafkaConsumerFactory consumerFactory =new DefaultKafkaConsumerFactory(consumerConfigs());
        return consumerFactory;
    }

    public String getAutoOffsetReset() { return autoOffsetReset; }
    public Boolean getOffset() { return offset; }
    public Long getFrequency() { return frequency; }
    public String getTest1Topic() { return test1Topic; }
    public String getTest2Topic() { return test2Topic; }
    public String getTest3Topic() { return test3Topic; }
}
