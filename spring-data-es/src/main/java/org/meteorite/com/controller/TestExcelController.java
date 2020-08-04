package org.meteorite.com.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.base.em.SystemLogSourceEnum;
import org.meteorite.com.base.util.EasyExcelUtil;
import org.meteorite.com.domain.entity.LoginLog;
import org.meteorite.com.domain.entity.TestLog;
import org.meteorite.com.domain.repository.TestRepository;
import org.meteorite.com.dto.BaseLogQueryReq;
import org.meteorite.com.dto.EasyExcelHeaderDto;
import org.meteorite.com.dto.TestSystemLogReq;
import org.meteorite.com.dto.log.SystemLogReq;
import org.meteorite.com.service.BaseSystemLogService;
import org.meteorite.com.service.TestExcelLogService;
import org.meteorite.com.service.factory.LogServiceImplFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/es/export/")
public class TestExcelController {

    @Autowired
    TestExcelLogService testExcelLogService;

    @GetMapping(value = "/test")
    public String test(){
       List<EasyExcelHeaderDto> list = testExcelLogService.getData();
       log.info(" result:{}",list);
//        EasyExcelUtil.write("C:\\Users\\EDZ\\Desktop\\test.xlsx",List.class,list);
        EasyExcelUtil.write("C:\\Users\\EDZ\\Desktop\\test.xlsx", EasyExcelHeaderDto.class,list);

        return "success";
    }








}
