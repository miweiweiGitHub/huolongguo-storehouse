
package org.meteorite.com.base.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadExecutorConfig {
  @Value("${thread.pool.ms.corePoolSize:10}")
  private int corePoolSizeMs;
  @Value("${thread.pool.ms.maximumPoolSize:15}")
  private int maximumPoolSizeMs;
  @Value("${thread.pool.ms.workQueueSize:500}")
  private int workQueueSizeMs;
  @Value("${thread.pool.ms.threadName.prefix:MsgSend-}")
  private String prefixThreadNameMs;

  @Value("${thread.pool.ms.corePoolSize.l:1}")
  private int corePoolSizeMsL;
  @Value("${thread.pool.ms.maximumPoolSize.l:2}")
  private int maximumPoolSizeMsL;
  @Value("${thread.pool.ms.workQueueSize.l:10}")
  private int workQueueSizeMsL;
  @Value("${thread.pool.ms.threadName.prefix.l:MsgSend-L-}")
  private String prefixThreadNameMsL;

  @Value("${thread.pool.log.corePoolSize:2}")
  private int corePoolSizeLog;
  @Value("${thread.pool.log.maximumPoolSize:2}")
  private int maximumPoolSizeLog;
  @Value("${thread.pool.log.workQueueSize:10}")
  private int workQueueSizeLog;
  @Value("${thread.pool.log.threadName.prefix:Log-}")
  private String prefixThreadNameLog;

  @Value("${thread.pool.keepAliveTime:200}")
  private int keepAliveSeconds;
  @Value("${thread.pool.allowCoreThreadTimeOut:false}")
  private boolean allowCoreThreadTimeOut;

  /**
   * 提供任务执行线程池
   * 
   * @return Executor
   */
  @Bean(name = "asyncSendMsgExecutor")
  public Executor grantMsgExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(corePoolSizeMs);
    executor.setMaxPoolSize(maximumPoolSizeMs);
    executor.setQueueCapacity(workQueueSizeMs);
    executor.setKeepAliveSeconds(keepAliveSeconds);
    executor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
    executor.setThreadNamePrefix(prefixThreadNameMs);
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.initialize();
    return executor;
  }

  /**
   * 提供任务执行线程池(low)
   *
   * @return Executor
   */
  @Bean(name = "asyncSendMsgLExecutor")
  public Executor grantMsgExecutorL() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(corePoolSizeMsL);
    executor.setMaxPoolSize(maximumPoolSizeMsL);
    executor.setQueueCapacity(workQueueSizeMsL);
    executor.setKeepAliveSeconds(keepAliveSeconds);
    executor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
    executor.setThreadNamePrefix(prefixThreadNameMsL);
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.initialize();
    return executor;
  }


  /**
   * 提供任务执行线程池
   *
   * @return Executor
   */
  @Bean(name = "asyncGrantLogExecutor")
  public Executor grantLogtaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(corePoolSizeLog);
    executor.setMaxPoolSize(maximumPoolSizeLog);
    executor.setQueueCapacity(workQueueSizeLog);
    executor.setKeepAliveSeconds(keepAliveSeconds);
    executor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
    executor.setThreadNamePrefix(prefixThreadNameLog);
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.initialize();
    return executor;
  }
}

