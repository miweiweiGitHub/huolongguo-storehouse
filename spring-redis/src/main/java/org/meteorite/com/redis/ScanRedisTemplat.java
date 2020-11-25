package org.meteorite.com.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author EX_052100260
 * @title: LocalRedisTemplat
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-5 19:24
 */
@Component
@Slf4j
public class ScanRedisTemplat {

    @Autowired
    RedisTemplate redisTemplate;


    public void setKey(String key, String value) {
        Object execute = redisTemplate.execute(new RedisCallback<Set<Object>>() {
            @Override
            public Set<Object> doInRedis(RedisConnection redisConnection) throws DataAccessException {

                Set<Object> partUers = new HashSet<>();
                // 放在try中自动释放cursor
                try (Cursor<byte[]> cursor = redisConnection.scan(new ScanOptions.ScanOptionsBuilder()
                        .match(key).count(50000).build())) {
                    while (cursor.hasNext()) {
                        partUers.add(new String(cursor.next()));
                    }
                } catch (IOException e) {
                    log.error("getTotalUsers cursor close {}", e);
                }

                return partUers;
            }
        });

    }



}
