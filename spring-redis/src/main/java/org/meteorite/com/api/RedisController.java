package org.meteorite.com.api;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.redis.RedisLockCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author EX_052100260
 * @title: RedisController
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-6 8:46
 */
@Slf4j
@RestController
@RequestMapping("api/redis")
public class RedisController {

    @Autowired
    RedisLockCommon redisLock;

    /**
     * 根据tags推送消息
     * @return
     */
    @PostMapping("/{id}")
    public String pushByTags(@PathVariable String id){

       decrementProductStore(id);

        return "test";
    }

    @Async
    public boolean decrementProductStore(String id) {
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
