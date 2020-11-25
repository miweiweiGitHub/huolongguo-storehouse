package org.meteorite.com.base.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.*;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;
import java.util.Map;

/**
 * @author EX_052100260
 * @title: redis缓存配置: 重载redisCacheManager实现过期时间可配置
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-24 11:12
 */
@Configuration
@EnableTransactionManagement
@CacheConfig
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //初始化一个RedisCacheConfiguration
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        //返回一个自定义的CacheManager
        return new CustomizeTtlRedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }

}

/**
 * @Description: 重载redisCacheManager-从cacheName中提取过期时间进行配置
 **/
class CustomizeTtlRedisCacheManager extends RedisCacheManager {

    public CustomizeTtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    public CustomizeTtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
    }

    public CustomizeTtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, boolean allowInFlightCacheCreation, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation, initialCacheNames);
    }

    public CustomizeTtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
    }

    public CustomizeTtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        Duration ttl = getTtlByName(name);
        if (ttl != null) {
            //证明在cacheName上使用了过期时间，需要修改配置中的ttl
            cacheConfig = cacheConfig.entryTtl(ttl);
        }
        //修改缓存key和value值的序列化方式
        cacheConfig = cacheConfig.computePrefixWith(DEFAULT_CACHE_KEY_PREFIX)
                .serializeValuesWith(DEFAULT_PAIR);
        return super.createRedisCache(name, cacheConfig);
    }

    /**
     * 缓存参数的分隔符
     * 数组元素0=缓存的名称
     * 数组元素1=缓存过期时间TTL
     */
    private static final String DEFAULT_SEPARATOR = "#";

    /**
     * 通过name获取过期时间
     *
     * @param name
     * @return
     */
    private Duration getTtlByName(String name) {
        if (name == null) {
            return null;
        }
        //根据分隔符拆分字符串，并进行过期时间ttl的解析
        String[] cacheParams = name.split(DEFAULT_SEPARATOR);
        if (cacheParams.length > 1) {
            String ttl = cacheParams[1];
            if (!StringUtils.isEmpty(ttl)) {
                try {
                    return Duration.ofSeconds(Long.parseLong(ttl));
                } catch (Exception e) {
                    // TODO
                }
            }
        }
        return null;
    }

    /**
     * 默认的key前缀
     */
    private static final CacheKeyPrefix DEFAULT_CACHE_KEY_PREFIX = cacheName -> cacheName + ":";

    /**
     * 默认序列化方式为json
     */
    private static final RedisSerializationContext.SerializationPair<Object> DEFAULT_PAIR = RedisSerializationContext.SerializationPair
            .fromSerializer(new GenericJackson2JsonRedisSerializer());
}
