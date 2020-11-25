package org.meteorite.com.task;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.base.BaseContant;
import org.meteorite.com.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author EX_052100260
 * @title: RedisTask
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-5 19:26
 */
@Component
@EnableScheduling
@Slf4j
public class RedisTask {

    @Autowired
    CacheService cacheService;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void fresh() {
        log.info("fresh redis start ...");
        //定时刷新缓存
        cacheService.addCountInMins(BaseContant.POLL_KEY_NUM,String.valueOf(BaseContant.TRICE));

        log.info("fresh redis end ...");
    }



}
