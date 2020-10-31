package org.meteorite.com;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.meteorite.com.base.config.MyPatition;
import org.meteorite.com.base.contant.CommonContant;
import org.meteorite.com.mq.task.KafkaBasicData;
import org.meteorite.com.service.LocalCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 容器初始化完成后，清理缓存, 避免升级版本缓存问题
 */
@Component
@Slf4j
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private KafkaBasicData kafkaBasicData;
    @Autowired
    private LocalCache localCache;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            log.info("application init start...");

            Map<TopicPartition, Long> test = kafkaBasicData.getLastOffsets(Collections.singleton(CommonContant.TOPIC));

            if (test==null) {
                return;
            }
            List<MyPatition> list = new ArrayList<>();
            test.forEach((k,v)->{
                MyPatition myPatition = new MyPatition();
                myPatition.setTopic(k.topic());
                myPatition.setPartition(k.partition());
                myPatition.setPosition(v);
                list.add(myPatition);
            });
//            localCache.save(CommonContant.KAFKA_PARTITION_BASIC_DATA, JSONObject.toJSONString(list));

//            localCache.save(CommonContant.KAFKA_MAX_POLL_RECORDS_CONFIG, String.valueOf(600 / test.size()));

            log.info("application init end...");
        } catch (Exception e) {
            log.error("init is invoked,occurs exception:", e);
            System.exit(-1);
        }
    }
}
