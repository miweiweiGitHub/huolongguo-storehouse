package org.meteorite.com.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author EX_052100260
 * @title: LocalRedisHandler
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-10 15:22
 */
@Component
public class LocalRedisHandler {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public void add(String key,String value){
        stringRedisTemplate.opsForValue().set(key, value);
        stringRedisTemplate.opsForValue().set(key, value,60L, TimeUnit.SECONDS);
    }
    public String get(String key){
       return stringRedisTemplate.opsForValue().get(key);
    }

}
