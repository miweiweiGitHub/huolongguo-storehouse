package org.meteorite.com.service;

/**
 * @author EX_052100260
 * @title: CacheService
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-10 14:55
 */
public interface CacheService {
    String test(String key);

    void addCountInMins(String s, String s1);

    Boolean reduce(String pollKeyNum);
}
