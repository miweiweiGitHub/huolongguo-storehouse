package org.meteorite.com.service.factory;



import org.meteorite.com.base.em.SystemLogSourceEnum;
import org.meteorite.com.service.BaseSystemLogService;
import org.meteorite.com.service.SpecialSystemLogService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 系统日志服务实现工厂类
 *
 * @author liwei
 * @since 2020-05-09
 *
 */

@Component
public class LogServiceImplFactory implements ApplicationContextAware {

    private static Map<SystemLogSourceEnum, BaseSystemLogService> systemLogServiceMap;
    private static Map<SystemLogSourceEnum, SpecialSystemLogService> specialSystemLogServiceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, BaseSystemLogService> baseSystemLogServices = applicationContext.getBeansOfType(BaseSystemLogService.class);
        Map<String, SpecialSystemLogService> specialSystemLogServices = applicationContext.getBeansOfType(SpecialSystemLogService.class);
        systemLogServiceMap = new HashMap<>();
        specialSystemLogServiceMap = new HashMap<>();

        baseSystemLogServices.forEach((key,value)->systemLogServiceMap.put(value.getLogType(),value));
        specialSystemLogServices.forEach((key,value)->specialSystemLogServiceMap.put(value.getLogType(),value));
    }

    public  <T extends BaseSystemLogService> T getBaseLogServiceImpl(SystemLogSourceEnum type){

        return (T)systemLogServiceMap.get(type);
    }

    public  <T extends SpecialSystemLogService> T  getSpecialLogServiceImpl(SystemLogSourceEnum type){

        return (T)specialSystemLogServiceMap.get(type);
    }
}

