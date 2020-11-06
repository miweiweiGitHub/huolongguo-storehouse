package org.meteorite.com.task;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.service.AccessLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;

/**
 * @author EX_052100260
 * @title: AccessLimitTask
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-5 17:14
 */
@Component
@EnableScheduling
@Slf4j
public class AccessLimitTask {

    @Autowired
    AccessLimitService accessLimitService;
    @Resource
    private Executor asyncSendMsgExecutor;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0/2 * * * * ? ")
    public  void test(){
        for (int i = 0; i <8 ; i++) {
            pushAll();
        }
    }

    private void pushAll() {

        if(accessLimitService.getTokenCard()){
            log.info("access success [{}]",sdf.format(new Date()));
        }else{
            log.info("access limit [{}]",sdf.format(new Date()));
        }

    }


}
