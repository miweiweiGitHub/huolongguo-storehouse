package org.meteorite.com.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.base.em.SystemLogSourceEnum;
import org.meteorite.com.domain.entity.TestLog;
import org.meteorite.com.domain.repository.TestRepository;
import org.meteorite.com.dto.BaseLogQueryReq;
import org.meteorite.com.dto.TestSystemLogReq;
import org.meteorite.com.dto.log.SystemLogReq;
import org.meteorite.com.service.BaseSystemLogService;
import org.meteorite.com.service.factory.LogServiceImplFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/es/base/")
public class BaseController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    LogServiceImplFactory logServiceImplFactory;

    @PostMapping(value = "/test")
    public String test(@RequestBody TestSystemLogReq testSystemLogReq){

        log.info("BaseController test start *************** ");
        TestLog testLog = JSONObject.parseObject(testSystemLogReq.getMessage(), TestLog.class);
        TestLog save = testRepository.save(testLog);

        return JSONObject.toJSONString(save);
    }



    @PostMapping(value = "/testQuery")
    public String testQuery(@RequestBody SystemLogReq systemLogReq){

        log.info("BaseController test start *************** ");
        BaseLogQueryReq baseLogQueryReq = JSONObject.parseObject(systemLogReq.getQueryConditionBody(), BaseLogQueryReq.class);

        BaseSystemLogService baseLogServiceImpl = logServiceImplFactory.getBaseLogServiceImpl(SystemLogSourceEnum.LOGIN_LOG);
        Page pageList = baseLogServiceImpl.getPageList(systemLogReq.getQueryConditionBody());

        return JSONObject.toJSONString(pageList);
    }




}
