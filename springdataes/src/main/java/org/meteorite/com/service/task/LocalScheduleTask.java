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

    @Scheduled(cron = "0 0/1 * * * ? ")
//    @Scheduled(cron = "0 0 0 15 * ?")
    private void cleanHistoryLog() {

        log.info("local task start *************** ");

        for (SystemLogSourceEnum value : SystemLogSourceEnum.values()) {
            log.info("local task create  SystemLogSourceEnum value:{} ",value);
            BaseSystemLogService baseLogServiceImpl = logServiceImplFactory.getBaseLogServiceImpl(value);
            if (baseLogServiceImpl == null) continue;
            Class aClass = baseLogServiceImpl.getGenericClass();
            log.info("local task create  index mapping aClass:{} ",aClass);
            String indexType = elasticsearchTemplate.getPersistentEntityFor(aClass).getIndexType();
            String indexname = value.getIndexPrefix() + CommonUtil.getNextMonthStr();
            String preIndexName = value.getIndexPrefix() + CommonUtil.getPreMonthStr();
            Map mapping = elasticsearchTemplate.getMapping(preIndexName,indexType);
            if (elasticsearchTemplate.createIndex(indexname)) {
                boolean mappingFlag = elasticsearchTemplate.putMapping(indexname, indexType, mapping);
                log.info("local task create  mapping is {} ", mappingFlag);
            }
        }
        log.info("local task end *************** ");
    }

    @Scheduled(cron = "0 0/2 * * * ? ")
    private void saveTestLog(){
        log.info("local save test log start *************** {}",CommonUtil.getTestLog());
        testRepository.save(CommonUtil.getTestLog());
        log.info("local save test log end *************** ");
    }
}
