package org.meteorite.com.task;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.redis.RedisLockCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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


    @Resource
    private Executor asyncSendMsgExecutor;

    @Autowired
    RedisLockCommon redisLock;


//    @Scheduled(cron = "0/1 * * * * ? ")
    public void test() {


        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 10; i++) {
                decrementProductStore(i%2);
            }
        }, asyncSendMsgExecutor);

    }

    public boolean decrementProductStore(int id) {
        String key = "local_test_lock_" + id;
        long time = System.currentTimeMillis();
        try {
            //如果加锁失败
            if (!redisLock.tryLock(key, String.valueOf(time))) {
                return false;
            }
            log.info("测试业务逻辑");


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //解锁
            redisLock.unlock(key, String.valueOf(time));
        }
        return true;

    }

}
