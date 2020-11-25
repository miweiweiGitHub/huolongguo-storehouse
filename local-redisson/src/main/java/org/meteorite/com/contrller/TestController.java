package org.meteorite.com.contrller;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.base.BaseContant;
import org.meteorite.com.cache.RedissonLock;
import org.meteorite.com.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author EX_052100260
 * @title: TestController
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-10 14:51
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    CacheService cacheService;
    @Autowired
    RedissonLock redissonLock;


    @GetMapping("{key}")
    public String test(@PathVariable String key){
        return cacheService.test(key);
    }

    @GetMapping("stock")
    public String lockDecreaseStock() throws InterruptedException {

        redissonLock.lock("lock", 60L);
       Boolean result =  cacheService.reduce(BaseContant.POLL_KEY_NUM);
//        if (TOTAL > 0) {
//            TOTAL--;
//        }
//        Thread.sleep(50);
//        log.info("===lock===减完库存后,当前库存===" + TOTAL);
        //如果该线程还持有该锁，那么释放该锁。如果该线程不持有该锁，说明该线程的锁已到过期时间，自动释放锁
        if (redissonLock.isHeldByCurrentThread("lock")) {
            redissonLock.unlock("lock");
        }
        return "=================================";
    }

}
