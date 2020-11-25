package org.meteorite.com;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 容器初始化完成后，清理缓存, 避免升级版本缓存问题
 */
@Component
@Slf4j
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {


  
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    try {
      log.info("application init start...");

      log.info("application init end...");
    } catch (Exception e) {
      log.error("init is invoked,occurs exception:",e);
      System.exit(-1);
    }
  }
}
