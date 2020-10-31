package org.meteorite.com.mq.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.meteorite.com.base.config.MqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author EX_052100260
 * @title: GroupQueue
 * @projectName huolongguo-storehouse
 * @description: 获取当前kafka的基础数据
 * @date 2020-10-27 11:07
 */
@Component
@Slf4j
public class KafkaBasicData {

    @Autowired
    MqConfig mqConfig;
    /**
     * 获取当前topic及消费者组上次消费的offset信息
     *
     * @param topics
     * @return
     */
    public Map<TopicPartition, Long> getLastOffsets(Set<String> topics) {
        Map map = mqConfig.consumerConfigs();
        KafkaConsumer consumer =  new KafkaConsumer(map);
        Map<TopicPartition, Long> resultMap = new HashMap<TopicPartition, Long>();
        List<PartitionInfo> infos = new ArrayList<>();
        for (String topic : topics) {
            log.info(" KafkaBasicData topic:{}", topic);
            //分配
            infos = consumer.partitionsFor(topic);
            List<TopicPartition> tpList = new ArrayList<TopicPartition>();
            for (PartitionInfo info : infos) {
                log.info("KafkaBasicData PartitionInfo:{}", info);
                tpList.add(new TopicPartition(info.topic(), info.partition()));
            }
            consumer.assign(tpList);

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    log.info("KafkaBasicData Runtime consumer close !!!");
                    if (consumer != null) {
                        consumer.close();
                    }
                }
            }));

            //获取
            for (PartitionInfo info : infos) {
                resultMap.put(new TopicPartition(info.topic(), info.partition()),
                        consumer.position(new TopicPartition(info.topic(), info.partition())));
            }
        }
        log.info("KafkaBasicData result:{}", resultMap);
        return resultMap;
    }
}
