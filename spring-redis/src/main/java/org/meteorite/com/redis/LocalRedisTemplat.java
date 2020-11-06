package org.meteorite.com.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @author EX_052100260
 * @title: LocalRedisTemplat
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-5 19:24
 */
@Component
public class LocalRedisTemplat {

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    public void setKey(String key, String value) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key, value);
    }

    public String getValue(String key) {
        ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
        return ops.get(key);
    }


}
