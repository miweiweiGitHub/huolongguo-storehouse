package org.meteorite.com.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.cache.LocalRedisHandler;
import org.meteorite.com.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author EX_052100260
 * @title: CacheServiceImpl
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-10 14:56
 */
@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private LocalRedisHandler localRedisHandler;

    @Override
    public String test(String key) {
        return localRedisHandler.get(key);
    }

    @Override
    public void addCountInMins(String key, String value) {
        localRedisHandler.add(key, value);
    }

    @Override
    public Boolean reduce(String pollKeyNum) {

        return null;
    }
}
