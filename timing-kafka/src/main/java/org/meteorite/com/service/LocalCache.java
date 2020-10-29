package org.meteorite.com.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


/**
 * Created by KaiFengGe on 2018/10/30.
 */
@Service
@Slf4j
public class LocalCache {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 保存key和value
     * @param key
     * @param value
     */
    public void save(String key ,String value){
        log.info("LocalCache save key:{},value:{}",key,value);
        redisTemplate.opsForValue().set(key,value);

    }

    /**
     * 获取 key 对应的字符串
     * @param key
     * @return
     */
    public String read(String key) {
        log.info("LocalCache read key:{}",key);
        return redisTemplate.opsForValue().get(key);
    }


}
