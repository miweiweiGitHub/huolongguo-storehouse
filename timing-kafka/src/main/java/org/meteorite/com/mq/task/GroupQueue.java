package org.meteorite.com.mq.task;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.meteorite.com.base.config.KafkaConsumerFactory;
import org.meteorite.com.base.config.MqConfig;
import org.meteorite.com.base.config.MyPatition;
import org.meteorite.com.base.contant.CommonContant;
import org.meteorite.com.mq.producer.SimpleKafkaProducer;
import org.meteorite.com.service.LocalCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * @author EX_052100260
 * @title: GroupQueue
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-10-27 11:07
 */
//@EnableAsync
@Component
@EnableScheduling
@Slf4j
public class GroupQueue {

    @Autowired
    LocalCache localCache;
    @Autowired
    MqConfig mqConfig;

    @Scheduled(cron = "0 27 * * * ? ")
    public void producer() {
        log.info("begin producer !!!");
        new SimpleKafkaProducer().produce();
        log.info("end producer !!!");
    }


//    @Scheduled(cron = "0,59 13 9 31 * ? ")
    public void consumerByAssign() {
        log.info("begin consumerByAssign !!!");
//        String sizeRecord = localCache.read(CommonContant.KAFKA_MAX_POLL_RECORDS_CONFIG);
        Map map = mqConfig.consumerConfigs();
        map.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Integer.valueOf(300));
        KafkaConsumer consumer =  new KafkaConsumer(map);
        try {

            HashSet<String> hashSet = new HashSet();
            hashSet.add(CommonContant.TOPIC);
            String read = "[{\"partition\": 0,\"position\": 2109,\"topic\": \"MSG_PUSH-TOPIC\"},{\"partition\": 1,\"position\": 2111,\"topic\": \"MSG_PUSH-TOPIC\"}]";

            List<MyPatition> lastOffsets = JSONObject.parseArray(read, MyPatition.class);

            List<ConsumerRecord<String, String>> buffer = new ArrayList<>();

            lastOffsets.forEach(e -> {

                HashSet<TopicPartition> topicPartitionHashSet = new HashSet();
                topicPartitionHashSet.add(new TopicPartition(e.getTopic(), e.getPartition()));

                consumer.assign(topicPartitionHashSet);
                                    ConsumerRecords<String, String> records = consumer.poll(1000);
                records.forEach(x -> {
                    log.info("records key:{},value:{},headers:{},offset:{},partition:{},topic:{}", x.key(), x.value(), x.headers(), x.offset(), x.partition(), x.topic());
                });
                for (ConsumerRecord<String, String> record : records) {
                    buffer.add(record);
                }
                log.info("获取的kafka 数据：{}", buffer.size());
                consumer.commitSync();

            });
            //  todo 业务逻辑处理
            log.info("总计拉取数据 size:{}", buffer.size());

            buffer.clear();
            log.info("end consumerByAssign !!!");

        } catch (Exception e) {
            e.printStackTrace();
            log.error(" consumerByAssign error info:{}", e);
        } finally {
            if (consumer != null){
                consumer.close();
                log.info("consumerByAssign consumer close !!!");
            }
        }
    }

//    @Scheduled(cron = "10 0/2 * * * ?")
    public void consumerBySubscribe() throws FileNotFoundException, IOException {
        log.info("consumerBySubscribe start 。。。。。");
        Map map = mqConfig.consumerConfigs();
        KafkaConsumer consumer =  new KafkaConsumer(map);
        try {
            consumer.subscribe(Arrays.asList(CommonContant.TOPIC));
            boolean y = true;
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                log.info("records:" + records);
                for (TopicPartition partition : records.partitions()) {
                    // 疑问，这个list里的数据顺序是怎么确定的
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);

                    long firstoffset = partitionRecords.get(0).offset();
                    try {
                        for (ConsumerRecord<String, String> record : partitionRecords) {
//                            if (this.handler != null)
//                                this.handler.handle(record.offset(), record.key(), record.value());
                            // TODO insert db
                            log.info("records key:{},value:{},headers:{},offset:{},partition:{},topic:{}",
                                    record.key(), record.value(), record.headers(), record.offset(), record.partition(), record.topic());
                        }
                    } catch (Exception e) {
                        log.info("insert db filuer");
                        consumer.seek(partition, firstoffset);
                        y = false;
                    }
                    if (y) {
                        long lastoffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                        consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastoffset + 1)));// singletonXxx()：返回一个只包含指定对象的，不可变的集合对象。
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (consumer != null){
                consumer.close();
                log.info("consumerBySubscribe consumer close !!!");
            }
        }
    }


    /**
     * 获取当前topic及消费者组上次消费的offset信息
     *
     * @param topics
     * @return
     */
//    @Scheduled(cron = "0 0/1 * * * ?")
    private static Map<TopicPartition, Long> getLastOffsets(Set<String> topics) {
        KafkaConsumer consumer = KafkaConsumerFactory.getKafkaConsumer("hdb-msg-push");
        Map<TopicPartition, Long> resultMap = new HashMap<TopicPartition, Long>();
        for (String topic : topics) {
            log.info("topic:{}", topic);
            //分配
            List<PartitionInfo> infos = consumer.partitionsFor(topic);
            List<TopicPartition> tpList = new ArrayList<TopicPartition>();
            for (PartitionInfo info : infos) {
                log.info("PartitionInfo:{}", info);
                tpList.add(new TopicPartition(info.topic(), info.partition()));
            }
            consumer.assign(tpList);

            //获取
            for (PartitionInfo info : infos) {
                resultMap.put(new TopicPartition(info.topic(), info.partition()),
                        consumer.position(new TopicPartition(info.topic(), info.partition())));
            }
        }
        consumer.close();
        log.info("result:{}", resultMap);
        return resultMap;
    }

    /**
     * 线程关闭的时候，会管理未关闭的连接
     */
    public static void test(){
       Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
           @Override
           public void run() {
               log.info("Runtime consumer close !!!");
//               if (consumer != null) {
//                   consumer.close();
//               }
           }
       }));
   }


}
