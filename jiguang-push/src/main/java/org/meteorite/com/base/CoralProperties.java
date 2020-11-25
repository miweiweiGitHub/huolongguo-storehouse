package org.meteorite.com.base;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author EX_052100260
 * @title: CoralProperties
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-25 11:21
 */
@Data
@Component
@ConfigurationProperties(prefix = "jiguang.coral")
public class CoralProperties {

    /**
     * 密钥
     */
    private String secret;

    /**
     *  key
     */
    private String key;

    /**
     * 极光推送设置过期时间
     */
    private String liveTime;

    /**
     * 环境
     */
    private boolean apnsProduction;


}
