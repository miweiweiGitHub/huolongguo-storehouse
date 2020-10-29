//package org.meteorite.com.mq.task;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
//import org.apache.kafka.common.PartitionInfo;
//import org.apache.kafka.common.TopicPartition;
//import org.meteorite.com.base.config.MqConfig;
//import org.meteorite.com.base.config.TaskExecutorConfig;
//import org.meteorite.com.base.util.DateUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicLong;
//
///**
// * @author EX_052100260
// * @title: MqTask
// * @projectName huolongguo-storehouse
// * @description: TODO
// * @date 2020-10-26 11:46
// */
////@Component
//@Configuration
//@EnableScheduling
//@Slf4j
//public class MqTask {
//
//
//    @Autowired
//    TaskExecutorConfig taskExecutorConfig;
//
//    @Autowired
//    MqConfig mqConfig;
//
//    @Autowired
////    SourceDataService sourceDataService;
//
//    /**
//     * @author yangpin
//     * @Description kafka定时消费
//     * @Date 2019/5/21 18:06
//     * @Param []
//     * @return void
//     **/
//    //每天凌晨0点
////    @Scheduled(cron = "0 0 00 * * ?")
//    private void MqTask() {
//        try {
//            log.info("MqTask  mq消息队列消费线程初始化开始！......");
//            ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) taskExecutorConfig.getAsyncExecutor();
//            KafkaConsumer<Integer, String> test1Consumer = new KafkaConsumer(mqConfig.consumerConfigs());
//            KafkaConsumer<Integer, String> test2Consumer = new KafkaConsumer(mqConfig.consumerConfigs());
//
//            List<PartitionInfo> test1PartitionInfos = test1Consumer.partitionsFor(mqConfig.getTest1Topic());
//            List<PartitionInfo> test2PartitionInfos = test2Consumer.partitionsFor(mqConfig.getTest2Topic());
//
//            List<TopicPartition> test1TopicPartitions = new ArrayList<>();
//            List<TopicPartition> test2TopicPartitions = new ArrayList<>();
//
//            Map<TopicPartition, Long> test1StartTimestampsToSearch = new HashMap<>();
//            Map<TopicPartition, Long> test1EndTimestampsToSearch = new HashMap<>();
//
//            Map<TopicPartition, Long> test2StartTimestampsToSearch = new HashMap<>();
//            Map<TopicPartition, Long> test2EndTimestampsToSearch = new HashMap<>();
//
//            final AtomicLong test1StartOffset = new AtomicLong(0L);
//            final AtomicLong test1EndOffset = new AtomicLong(0L);
//
//
//            final AtomicLong test2StartOffset = new AtomicLong(0L);
//            final AtomicLong test2EndOffset = new AtomicLong(0L);
//
//            //是否开启偏移消费
//            if (mqConfig.getOffset() == true  && mqConfig.getAutoOffsetReset().equals("latest")){
//                log.info("偏移消费开启！......");
//
//                Date now = new Date();
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(now);
//                calendar.add(calendar.DATE, -1);
//                SimpleDateFormat sd= new SimpleDateFormat(DateUtil.DEFALT_DATE_FORMAT);
//                SimpleDateFormat df= new SimpleDateFormat(DateUtil.DATE_FORMATE_YYYYMMDDHHMMSS);
//
//                log.info("当前时间：   " + DateUtil.getDate(DateUtil.DATE_FORMATE_YYYYMMDDHHMMSS) +"");
//                log.info("偏移消费时间段：" + sd.format(calendar.getTime()) + " 00:00:00" +  " 至 " + sd.format(calendar.getTime()) + " 23:59:59" );
//
//                test1PartitionInfos.forEach(n ->{
//                    test1TopicPartitions.add(new TopicPartition(n.topic(), n.partition()));
//                    //开始时间
//                    test1StartTimestampsToSearch.put(new TopicPartition(n.topic(), n.partition()), DateUtil.getLastDayStartTimeStamp(0));
//                    test1EndTimestampsToSearch.put(new TopicPartition(n.topic(), n.partition()), DateUtil.getLastDayStartTimeStamp(1));
//                });
//
//                test2PartitionInfos.forEach(n ->{
//                    test2TopicPartitions.add(new TopicPartition(n.topic(), n.partition()));
//                    test2StartTimestampsToSearch.put(new TopicPartition(n.topic(), n.partition()), DateUtil.getLastDayStartTimeStamp(0));
//                    test2EndTimestampsToSearch.put(new TopicPartition(n.topic(), n.partition()), DateUtil.getLastDayStartTimeStamp(1));
//                });
//                test1Consumer.assign(test1TopicPartitions);
//                test2Consumer.assign(test2TopicPartitions);
//                // 获取每个partition指定时间之前的偏移量
//                Map<TopicPartition, OffsetAndTimestamp> test1StartTimeMap = test1Consumer.offsetsForTimes(test1StartTimestampsToSearch);
//                Map<TopicPartition, OffsetAndTimestamp> test1EndTimeMap = test1Consumer.offsetsForTimes(test1EndTimestampsToSearch);
//
//                Map<TopicPartition, OffsetAndTimestamp> test2StartTimeMap = test2Consumer.offsetsForTimes(test2StartTimestampsToSearch);
//                Map<TopicPartition, OffsetAndTimestamp> test2EndTimeMap = test2Consumer.offsetsForTimes(test2EndTimestampsToSearch);
//
//                log.info("开始设置各分区初始偏移量！......");
//                offsetHandle(test1StartTimeMap,test1EndTimeMap,test1StartOffset,test1EndOffset,test1EndTimestampsToSearch,test1Consumer,df);
//                offsetHandle(test2StartTimeMap,test2EndTimeMap,test2StartOffset,test2EndOffset,test2EndTimestampsToSearch,test2Consumer,df);
//                log.info("设置各分区初始偏移量完毕！......");
//
//
//            }else if (mqConfig.getAutoOffsetReset().equals("earliest") && mqConfig.getOffset() == false){
//                test1PartitionInfos.forEach(n ->{
//                    test1TopicPartitions.add(new TopicPartition(n.topic(), n.partition()));
//                });
//                test2PartitionInfos.forEach(n ->{
//                    test2TopicPartitions.add(new TopicPartition(n.topic(), n.partition()));
//                });
//                log.info("isSetOffsetTime = " + mqConfig.getOffset() + "消费策略 = " + mqConfig.getAutoOffsetReset() );
//                test1Consumer.assign(test1TopicPartitions);
//                test2Consumer.assign(test2TopicPartitions);
//            }else {
//                log.error("mq消息参数配置有误，请检查配置文件！");
//                System.exit(-1);
//            }
////            executor.execute(new MqTest1Consumer(mqConfig,test1Consumer,test1StartOffset.get(),test1EndOffset.get()));
////            executor.execute(new MqTest2Consumer(mqConfig,test2Consumer,test2StartOffset.get(),test2EndOffset.get()));
//            log.info("mq消息队列消费线程初始化完成！......");
//        }catch (Exception e){
//            e.printStackTrace();
//            log.error("mq消息队列消费线程初始化失败！......" + e.getMessage());
//            System.exit(-1);
//        }
//    }
//
//
//
//
//    /**
//     * @author yangpin
//     * @Description offset偏移处理
//     * @Date 2019/5/21 18:05
//     * @Param [startTimeMap, endTimeMap, startOffset, endOffset, endTimestampsToSearch, consumer, df]
//     * @return void
//     **/
//    private void offsetHandle(Map<TopicPartition, OffsetAndTimestamp> startTimeMap,
//                              Map<TopicPartition, OffsetAndTimestamp> endTimeMap,
//                              final AtomicLong startOffset,
//                              final AtomicLong endOffset,
//                              Map<TopicPartition, Long> endTimestampsToSearch,
//                              KafkaConsumer<Integer, String> consumer,
//                              SimpleDateFormat df){
//
//        startTimeMap.forEach((k,v) ->{
//            OffsetAndTimestamp startOffsetTimestamp =  v;
//            OffsetAndTimestamp endOffsetTimestamp =  endTimeMap.get(k);
//            if(startOffsetTimestamp != null) {
//                long endTimestamp = 0L;
//                String topic = k.topic();
//                int partition = k.partition();
//                long startTimestamp = startOffsetTimestamp.timestamp();
//                long startOffsetTmp = startOffsetTimestamp.offset();
//                if (endOffsetTimestamp != null){
//                    //86,400,000
//                    //86,399,000
//                    endTimestamp = endOffsetTimestamp.timestamp();
//                    endOffset.set(endOffsetTimestamp.offset());
//                    long lastDayEndTime = DateUtil.getLastDayStartTimeStamp(1);
//                    boolean flag = false;
//                    if (endTimestamp > lastDayEndTime){
//                        while (true){
//                            endTimestamp = endTimestamp - 1000;
//                            //往后回溯一秒查找
//                            endTimestampsToSearch.put(new TopicPartition(k.topic(), k.partition()), endTimestamp);
//                            Map<TopicPartition, OffsetAndTimestamp> offsetsForTimes = consumer.offsetsForTimes(endTimestampsToSearch);
//                            for(Map.Entry<TopicPartition, OffsetAndTimestamp> entry : offsetsForTimes.entrySet()) {
////                                        logger.info("反向查找时间节点 = " + df.format(new Date(entry.getValue().timestamp())));
//                                if (entry.getValue().timestamp() <= lastDayEndTime){
//                                    endTimestamp = entry.getValue().timestamp();
//                                    endOffset.set(entry.getValue().offset());
//                                    flag = true;
//                                    break;
//                                }
//                            }
//                            if (flag == true) break;
//                        }
//                    }
//                }
//                log.info("consumer : " + " topic = " + topic + " , partition = " +
//                        partition + " , period of time = " + df.format(new Date(startTimestamp))+" - " + df.format(new Date(endTimestamp))
//                        + " , period of offset = " + startOffsetTmp + " - " + endOffset.get() +" ,共计： " + (endOffset.get() - startOffsetTmp));
//                // 设置读取消息的偏移量
//                startOffset.set(startOffsetTmp);
//                consumer.seek(k, startOffsetTmp);
//            }
//        });
//
//    }
//
//
//
//}
