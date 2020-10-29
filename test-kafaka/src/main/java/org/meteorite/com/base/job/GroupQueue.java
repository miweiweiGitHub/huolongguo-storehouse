package org.meteorite.com.base.job;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.meteorite.com.base.config.KafkaConsumerFactory;
import org.springframework.scheduling.annotation.Async;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author EX_052100260
 * @title: GroupQueue
 * @projectName huolongguo-storehouse
 * @description: 定时任务的消费
 * @date 2020-10-26 10:18
 */
public class GroupQueue {

    static boolean RUN = false;

    @Async
//    @Scheduled(cron = "0 * * * * ?")
    public void task() {
        if (RUN) {
            return;
        }
        RUN = true;
        KafkaConsumer consumer = KafkaConsumerFactory.getKafkaConsumer("group2");
        consumer.subscribe(Arrays.asList("GroupQueue", "FriendQueue"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            System.out.println("批次：" + UUID.randomUUID().toString());

            for (ConsumerRecord<String, String> record : records) {
                System.out.printf(record.topic() +
                                "一条新消息 offset = %d, key = %s, value = %s", record.offset(),
                        record.key(), record.value());
                System.out.println(record.topic() + "partition:" +
                        record.partition());

                // 业务处理 TODO
            }

            // 同步提交
            if (records.count() > 0) {
                consumer.commitSync();
                System.out.println("批次提交");
            }
        }
    }

}
