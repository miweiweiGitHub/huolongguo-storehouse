package org.meteorite.com.base.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author EX_052100260
 * @title: TaskThreadPoolConfig
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-10-26 11:44
 */
@Component
@ConfigurationProperties(prefix ="spring.task.pool")
public class TaskThreadPoolConfig {
    private int corePoolSize;
    private int maxPoolSize;
    private int keepAliveSeconds;
    private int queueCapacity;



    public int getCorePoolSize() {return corePoolSize; }
    public void setCorePoolSize(int corePoolSize) {this.corePoolSize = corePoolSize;}
    public int getMaxPoolSize() {return maxPoolSize;}
    public void setMaxPoolSize(int maxPoolSize) {this.maxPoolSize = maxPoolSize;}
    public int getKeepAliveSeconds() {return keepAliveSeconds;}
    public void setKeepAliveSeconds(int keepAliveSeconds) {this.keepAliveSeconds = keepAliveSeconds;}
    public int getQueueCapacity() {return queueCapacity; }
    public void setQueueCapacity(int queueCapacity) {this.queueCapacity = queueCapacity; }

}
