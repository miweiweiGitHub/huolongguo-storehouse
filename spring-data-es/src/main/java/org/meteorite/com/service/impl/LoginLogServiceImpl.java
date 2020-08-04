package org.meteorite.com.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.meteorite.com.base.constant.SystemLogConstant;
import org.meteorite.com.base.em.SystemLogSourceEnum;
import org.meteorite.com.base.util.CommonUtil;
import org.meteorite.com.base.util.DesensitizedUtils;
import org.meteorite.com.domain.entity.LoginLog;
import org.meteorite.com.domain.entity.TestLog;
import org.meteorite.com.domain.repository.LoginRepository;
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
public class LoginLogServiceImpl implements SpecialSystemLogService<LoginLog> {

    @Autowired
    LoginRepository loginRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void add(String logBody) {
        log.info("LoginLogServiceImpl add logBody:{}", logBody);
        LoginLog loginLog = JSONObject.parseObject(logBody, LoginLog.class);
        // ipAddress 属性 ES 存储类型为 IP，如果写入端传了 "" ,会导致类型匹配异常，这里处理一下
        if (StringUtils.isBlank(loginLog.getIpAddress())) {
            loginLog.setIpAddress(null);
        }
        LoginLog save = loginRepository.save(loginLog);
        log.info("LoginLogServiceImpl add result:{}", save);

    }

    @Override
    public SystemLogSourceEnum getLogType() {
        return SystemLogSourceEnum.LOGIN_LOG;
    }


    @Override
    public Page<LoginLog> getPageList(String queryConditionBody) {
        long start = System.currentTimeMillis();
        log.info("LoginLogServiceImpl loginGetPageList requestParam:{}", queryConditionBody);
        BaseLogQueryReq baseLogQueryReq = JSONObject.parseObject(queryConditionBody, BaseLogQueryReq.class);
        //用户登录查询条件
        // {"phone":"","startTime":"2020-04-28 00:00:00","endTime":"2020-05-28 00:00:00","page":1,"size":2}

        //*************************分页数据封装*******************************************/
        Pageable pageable = CommonUtil.getPageable(SystemLogConstant.QueryCondition.LOGIN_TIME, baseLogQueryReq.getPage(), baseLogQueryReq.getSize());

        BoolQueryBuilder boolQueryBuilder = CommonUtil.handlerBaseLogQueryReqQuerySearch(SystemLogConstant.QueryCondition.TEST_TIME, baseLogQueryReq);

        //考虑到 日志会根据时间分布索引 ，通过通配符查询目前能匹配的所有索引，消除时间跨年的问题
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(boolQueryBuilder);
        nativeSearchQuery.addIndices(SystemLogConstant.QueryIndex.LOGIN_LOG);
        nativeSearchQuery.setPageable(pageable);
        Page<LoginLog> search = loginRepository.search(nativeSearchQuery);
        long end = System.currentTimeMillis();

        log.info("LoginLogServiceImpl loginGetPageList costTime:[{}], search list:{}",end-start, JSONObject.toJSONString(search));
        return search;
    }

    @Override
    public void cleanHistoryLog() {
        log.info("LoginLogServiceImpl cleanHistoryLog ########");
    }

    @Override
    public Class<LoginLog> getGenericClass() {
        return LoginLog.class;
    }


    @Override
    public Page<LoginLog> getDesensitizedPageList(String queryConditionBody, String fieldAuthorityResult) {
        log.info("TestLogServiceImpl getDesensitizedPageList queryConditionBody:{},fieldAuthorityResult:{}",queryConditionBody,fieldAuthorityResult);
        Page<LoginLog> pageList = getPageList(queryConditionBody);

        //判断脱敏字段权限
        if (StringUtils.isNotBlank(fieldAuthorityResult)){
            if (fieldAuthorityResult.contains(SystemLogConstant.DesensitizedField.PHONE_MASK_CODE)) {
                //手机号查看脱敏
                log.info("LoginLogServiceImpl getDesensitizedPageList fieldAuthorityResult:{},pageList:{}", fieldAuthorityResult,JSONObject.toJSONString(pageList));
                return pageList;
            }
        }
        //无脱敏权限,需加密
        pageList.getContent().stream().forEach(
                e -> {
                    String dPhone = DesensitizedUtils.mobilePhone(e.getPhone());
                    e.setPhone(dPhone);
                }
        );
        log.info("LoginLogServiceImpl getDesensitizedPageList fieldAuthorityResult:{},pageList:{}", fieldAuthorityResult,JSONObject.toJSONString(pageList));
        return pageList;
    }


}
