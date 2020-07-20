package org.meteorite.com.service.task;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.base.em.SystemLogSourceEnum;
import org.meteorite.com.base.util.CommonUtil;
import org.meteorite.com.domain.entity.TestLog;
import org.meteorite.com.domain.repository.TestRepository;
import org.meteorite.com.service.BaseSystemLogService;
import org.meteorite.com.service.factory.LogServiceImplFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Configuration
@EnableScheduling
@Slf4j
public class LocalScheduleTask {

    @Autowired
    LogServiceImplFactory logServiceImplFactory;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    TestRepository testRepository;

//    @Scheduled(cron = "0 45 * * * ? ")
    private void createMapping() {

        log.info("local task  createMapping start *************** ");
        for (SystemLogSourceEnum value : SystemLogSourceEnum.values()) {
            log.info("local task createMapping  SystemLogSourceEnum value:{} ", value);
            BaseSystemLogService baseLogServiceImpl = logServiceImplFactory.getBaseLogServiceImpl(value);
            if (baseLogServiceImpl == null) continue;
            Class aClass = baseLogServiceImpl.getGenericClass();

            log.info("local task createMapping  index mapping aClass:{} ", aClass);
            String indexType = elasticsearchTemplate.getPersistentEntityFor(aClass).getIndexType();
            String indexname = value.getIndexPrefix() + CommonUtil.getPlusMonthStr();

            Map mapping = elasticsearchTemplate.getMapping(aClass);

            //创建索引 设置 index max_result_window
            Map<String,Object> setting =new HashMap<>();
            //设置查询的最大条数
            setting.put("max_result_window", 100000000l);
            // 索引数据刷新间隔
            setting.put("refresh_interval", "5s");
            Map<String,Object> storeType =new HashMap<>();
            storeType.put("type","fs");
            //指定索引存储的格式
            setting.put("store", storeType);
            if (elasticsearchTemplate.indexExists(indexname)) {
                continue;
            }

            if (elasticsearchTemplate.createIndex(indexname,setting)) {
                boolean mappingFlag = elasticsearchTemplate.putMapping(indexname, indexType, mapping);
                log.info("local task createMapping  mapping is {} ", mappingFlag);
            }
        }
        log.info("local task createMapping end *************** ");
    }

    @Scheduled(cron = "0 0/1 * * * ? ")
    private void saveTestLog() {

        Random random = new Random();
        int i = random.nextInt(10);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", "18688736689");
        jsonObject.put("name", "火龙果test"+i);
        jsonObject.put("testTime", LocalDateTime.now());
        jsonObject.put("requestParam", "乘风破浪会有时，直挂云帆济沧桑");
        jsonObject.put("ipAddress", "127.0.0."+i);
        log.info("local save test log start *************** {}", JSONObject.parseObject(jsonObject.toJSONString(), TestLog.class));
        testRepository.save(JSONObject.parseObject(jsonObject.toJSONString(), TestLog.class));
        log.info("local save test log end *************** ");
    }

    @Scheduled(cron = "0 0/5 * * * ? ")
    private void checkMapping() {

        log.info("local task checkMapping start *************** ");
        for (SystemLogSourceEnum value : SystemLogSourceEnum.values()) {
            log.info("local task checkMapping  SystemLogSourceEnum value:{} ", value);
            BaseSystemLogService baseLogServiceImpl = logServiceImplFactory.getBaseLogServiceImpl(value);
            if (baseLogServiceImpl == null) continue;
            Class aClass = baseLogServiceImpl.getGenericClass();
            log.info("local task checkMapping  index mapping aClass:{} ", aClass);

            Map mapping = elasticsearchTemplate.getMapping(aClass);
            Map setting = elasticsearchTemplate.getSetting(aClass);

            log.info("local task checkMapping  SystemLogSourceEnum value:{},mapping:{}", value,mapping);
            log.info("local task checkMapping  SystemLogSourceEnum value:{},setting:{}", value,setting);
        }
        log.info("local task checkMapping end *************** ");
    }
}
