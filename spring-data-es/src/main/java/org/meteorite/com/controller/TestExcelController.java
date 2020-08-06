package org.meteorite.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.base.util.EasyExcelUtil;
import org.meteorite.com.dto.EasyExcelHeaderDto;
import org.meteorite.com.service.TestExcelLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        EasyExcelUtil.write("C:\\Users\\EX_052100260\\Desktop\\test.xlsx", EasyExcelHeaderDto.class,list);

        return "success";
    }








}
