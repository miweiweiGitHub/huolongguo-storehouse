package org.meteorite.com.mq.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.meteorite.com.base.config.MqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author EX_052100260
 * @title: KafkaConsumerDemo
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-10-27 10:31
 */
@Component
@Configuration
@EnableScheduling
@Slf4j
public class KafkaConsumerDemo {

    @Autowired
    MqConfig mqConfig;

    // 获取某个Topic的所有分区以及分区最新的Offset
    public void getPartitionsForTopic() {
//        final Consumer<Long, String> consumer = createConsumer();
        KafkaConsumer<Integer, String> test1Consumer = new KafkaConsumer(mqConfig.consumerConfigs());

        Collection<PartitionInfo> partitionInfos = test1Consumer.partitionsFor(mqConfig.getTest1Topic());
        System.out.println("Get the partition info as below:");
        List<TopicPartition> tp =new ArrayList<TopicPartition>();
        partitionInfos.forEach(str -> {
            log.info("Partition Info:{}",str);

            tp.add(new TopicPartition(mqConfig.getTest1Topic(),str.partition()));
            test1Consumer.assign(tp);
            test1Consumer.seekToEnd(tp);

            log.info("Partition:{} 's latest offset is:{} '" ,str.partition() , test1Consumer.position(new TopicPartition(mqConfig.getTest1Topic(), str.partition())));
        });
    }

    // 持续不断的消费数据
//    @Scheduled(cron = "0 0/1 * * * ? ")
    public  void run() throws InterruptedException {
        final Consumer<Integer, String> consumer =  new KafkaConsumer(mqConfig.consumerConfigs());
        consumer.subscribe(Collections.singletonList(mqConfig.getTest1Topic()));
        final int giveUp = 100; int noRecordsCount = 0;

        while(true){
            final ConsumerRecords<Integer, String> consumerRecords = consumer.poll(10);

            if(consumerRecords.count()==0){
                noRecordsCount++;
                if(noRecordsCount > giveUp) break;
                else continue;
            }

            // int i = 0;
            consumerRecords.forEach(record -> {
                // i = i + 1;
                log.info("Consumer Record:({}, {}, {}, {})",
                        record.key(), record.value(),
                        record.partition(), record.offset());
            });

            // System.out.println("Consumer Records " + i);
            consumer.commitAsync();
        }

        consumer.close();
        log.info("Kafka Consumer Exited");
    }
}
