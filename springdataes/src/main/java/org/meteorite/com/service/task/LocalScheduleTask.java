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
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        @Scheduled(cron = "0 0/5 * * * ? ")
//    @Scheduled(cron = "0 0 0 15 * ?")
    private void cleanHistoryLog() {

        log.info("local task start *************** ");

        for (SystemLogSourceEnum value : SystemLogSourceEnum.values()) {
            log.info("local task create  SystemLogSourceEnum value:{} ", value);
            BaseSystemLogService baseLogServiceImpl = logServiceImplFactory.getBaseLogServiceImpl(value);
            if (baseLogServiceImpl == null) continue;
            Class aClass = baseLogServiceImpl.getGenericClass();
            log.info("local task create  index mapping aClass:{} ", aClass);
            String indexType = elasticsearchTemplate.getPersistentEntityFor(aClass).getIndexType();
            String indexname = value.getIndexPrefix() + CommonUtil.getNextMonthStr();
            String preIndexName = value.getIndexPrefix() + CommonUtil.getPreMonthStr();
            Map mapping = elasticsearchTemplate.getMapping(preIndexName, indexType);

            if (elasticsearchTemplate.indexExists(indexname)) {
                continue;
            }

            if (elasticsearchTemplate.createIndex(indexname)) {
                boolean mappingFlag = elasticsearchTemplate.putMapping(indexname, indexType, mapping);
                log.info("local task create  mapping is {} ", mappingFlag);
            }


        }
        log.info("local task end *************** ");
    }

    @Scheduled(cron = "0 0/1 * * * ? ")
    private void saveTestLog() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", "18688736689");
        jsonObject.put("name", "火龙果test");
        jsonObject.put("testTime", LocalDateTime.now());
        jsonObject.put("requestParam", "乘风破浪会有时，直挂云帆济沧海");
        jsonObject.put("ipAddress", "127.0.0.1");
        log.info("local save test log start *************** {}", JSONObject.parseObject(jsonObject.toJSONString(), TestLog.class));
        testRepository.save(JSONObject.parseObject(jsonObject.toJSONString(), TestLog.class));
        //test_log_index_2020
        log.info("local save test log end *************** ");
    }
}
