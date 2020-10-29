package org.meteorite.com.mq.consumer;

import kafka.utils.ShutdownableThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.meteorite.com.base.config.MqConfig;

import java.time.Duration;

/**
 * @author EX_052100260
 * @title: MqTest2Consumer
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-10-26 11:45
 */
@Slf4j
public class MqTest2Consumer extends ShutdownableThread {


    private final KafkaConsumer consumer;
    private final long endOffset ;
    private final long startOffset ;
    private final MqConfig configs;
//    SourceDataService sourceDataService;
    private long counts  ;

    public MqTest2Consumer(MqConfig configs, KafkaConsumer consumer, long startOffset, long endOffset) {
        super("test2-consumer", false);
        this.configs = configs;
        this.consumer = consumer;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
//        this.sourceDataService = sourceDataService;
        //consumer = new KafkaConsumer<>(configs.consumerConfigs());
    }

    @Override
    public void doWork() {
        try {
            //consumer.assign(topicPartitions);
//            configs.mqConfigProperties.getFrequency()
            ConsumerRecords records =consumer.poll(Duration.ofSeconds(configs.getFrequency()));

            if (records ==null ||records.count() ==0 ){
                consumer.close();
                shutdown();
            }


            for (Object item : records) {
                ConsumerRecord record = (ConsumerRecord)item;
                if (record.offset() <=endOffset){
                    //此处为你的消息数据业务处理
                    counts++;
                    log.info("总计需要处理条数： " + (endOffset-startOffset) +" ,test2第： "+counts+ " 条 , test2结束offset = " +
                            endOffset + " , test2当前offset = " + record.offset());
                    consumer.commitSync();
                }else {
                    break;
                }
            }
            if ((endOffset -startOffset) ==counts){
                consumer.close();
                shutdown();
            }
        }catch (Exception e){
            log.error("mq消息队列处理异常！" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean isInterruptible() { return false;  }
}
