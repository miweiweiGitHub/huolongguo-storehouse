package org.meteorite.com.task;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.mq.sender.SimpleKafkaProducer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

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


//    @Scheduled(cron = "0 0/1 * * * ? ")
//    @Scheduled(cron = "0 * * * * ? ")
    public void producer() {
        log.info("begin producer !!!");
        new SimpleKafkaProducer().produce();
        log.info("end producer !!!");
    }


}
