package org.meteorite.com.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.meteorite.com.base.em.SystemLogSourceEnum;
import org.meteorite.com.base.constant.SystemLogConstant;
import org.meteorite.com.base.util.CommonUtil;
import org.meteorite.com.base.util.DesensitizedUtils;
import org.meteorite.com.domain.entity.TestLog;
import org.meteorite.com.domain.repository.TestRepository;
import org.meteorite.com.dto.BaseLogQueryReq;
import org.meteorite.com.service.SpecialSystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

/**
 * @author liwei
 * @since 2020-05-08
 */
@Service
@Slf4j
public class TestLogServiceImpl implements SpecialSystemLogService<TestLog> {

    @Autowired
    TestRepository testRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void add(String logBody) {
        log.info("TestLogServiceImpl add logBody:{}", logBody);
        TestLog testLog = JSONObject.parseObject(logBody, TestLog.class);
        // ipAddress 属性 ES 存储类型为 IP，如果写入端传了 "" ,会导致类型匹配异常，这里处理一下
        if (StringUtils.isBlank(testLog.getIpAddress())) {
            testLog.setIpAddress(null);
        }
        TestLog save = testRepository.save(testLog);
        log.info("TestLogServiceImpl add result:{}", save);

    }

    @Override
    public SystemLogSourceEnum getLogType() {
        return SystemLogSourceEnum.TEST_LOG;
    }


    @Override
    public Page<TestLog> getPageList(String queryConditionBody) {
        long start = System.currentTimeMillis();
        log.info("TestLogServiceImpl loginGetPageList requestParam:{}", queryConditionBody);
        BaseLogQueryReq baseLogQueryReq = JSONObject.parseObject(queryConditionBody, BaseLogQueryReq.class);
        //用户登录查询条件
        // {"phone":"","startTime":"2020-04-28 00:00:00","endTime":"2020-05-28 00:00:00","page":1,"size":2}

        //*************************分页数据封装*******************************************/
        Pageable pageable = CommonUtil.getPageable(SystemLogConstant.QueryCondition.TEST_TIME, baseLogQueryReq.getPage(), baseLogQueryReq.getSize());

        BoolQueryBuilder boolQueryBuilder = CommonUtil.handlerBaseLogQueryReqQuerySearch(SystemLogConstant.QueryCondition.TEST_TIME, baseLogQueryReq);

        //考虑到 日志会根据时间分布索引 ，通过通配符查询目前能匹配的所有索引，消除时间跨年的问题
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(boolQueryBuilder);
        nativeSearchQuery.addIndices(SystemLogConstant.QueryIndex.TEST_LOG);
        nativeSearchQuery.setPageable(pageable);
        Page<TestLog> search = testRepository.search(nativeSearchQuery);
        long end = System.currentTimeMillis();

        log.info("TestLogServiceImpl loginGetPageList costTime:[{}], search list:{}",end-start, JSONObject.toJSONString(search));
        return search;
    }

    @Override
    public void cleanHistoryLog() {
        log.info("TestLogServiceImpl cleanHistoryLog ########");
    }

    @Override
    public Class<TestLog> getGenericClass() {
        return TestLog.class;
    }


    @Override
    public Page<TestLog> getDesensitizedPageList(String queryConditionBody, String fieldAuthorityResult) {
        log.info("TestLogServiceImpl getDesensitizedPageList queryConditionBody:{},fieldAuthorityResult:{}",queryConditionBody,fieldAuthorityResult);
        Page<TestLog> pageList = getPageList(queryConditionBody);

        //判断脱敏字段权限
        if (StringUtils.isNotBlank(fieldAuthorityResult)){

            if (fieldAuthorityResult.contains(SystemLogConstant.DesensitizedField.PHONE_MASK_CODE)
                    && fieldAuthorityResult.contains(SystemLogConstant.DesensitizedField.NAME_MASK_CODE)) {
                //手机号查看脱敏
                //名字查看脱敏
                log.info("TestLogServiceImpl getDesensitizedPageList fieldAuthorityResult:{},pageList:{}",fieldAuthorityResult,JSONObject.toJSONString(pageList));
                return pageList;
            } else if (fieldAuthorityResult.contains(SystemLogConstant.DesensitizedField.PHONE_MASK_CODE)) {
                //手机号查看脱敏，名字无脱敏
                pageList.getContent().stream().forEach(
                        e -> {
                            String dName = DesensitizedUtils.chineseName(e.getName());
                            e.setName(dName);
                        }
                );
                log.info("TestLogServiceImpl getDesensitizedPageList fieldAuthorityResult:{},pageList:{}",fieldAuthorityResult,JSONObject.toJSONString(pageList));
                return pageList;
            } else if (fieldAuthorityResult.contains(SystemLogConstant.DesensitizedField.NAME_MASK_CODE)) {
                //名字查看脱敏，手机号无脱敏
                pageList.getContent().stream().forEach(
                        e -> {
                            String dPhone = DesensitizedUtils.mobilePhone(e.getPhone());
                            e.setPhone(dPhone);
                        }
                );
                log.info("TestLogServiceImpl getDesensitizedPageList fieldAuthorityResult:{},pageList:{}",fieldAuthorityResult,JSONObject.toJSONString(pageList));

                return pageList;
            }
        }

        //无脱敏权限
        pageList.getContent().stream().forEach(
                e -> {

                    String dName = DesensitizedUtils.chineseName(e.getName());
                    String dPhone = DesensitizedUtils.mobilePhone(e.getPhone());
                    e.setName(dName);
                    e.setPhone(dPhone);
                }
        );
        log.info("TestLogServiceImpl getDesensitizedPageList fieldAuthorityResult:{},pageList:{}",fieldAuthorityResult,JSONObject.toJSONString(pageList));
        return pageList;
    }


}
