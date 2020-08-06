package org.meteorite.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.dto.SystemLogDto;
import org.meteorite.com.service.SystemLogMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author EX_052100260
 * @title: SystemLogMenuController
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-8-6 14:50
 */
@Slf4j
@RestController
@RequestMapping("/sys/log")
public class SystemLogMenuController {

    @Autowired
    SystemLogMenuService systemLogMenuService;
    //新增
    @PostMapping(value = "/add")
    public String add(@RequestBody SystemLogDto systemLogDto){
        log.info("add :{}",systemLogDto);
        Integer save = systemLogMenuService.save(systemLogDto);
        return String.valueOf(save);
    }


    //修改
    @PutMapping(value = "/update")
    public String update(@RequestBody SystemLogDto systemLogDto){
        log.info("update:{}",systemLogDto);
        if (systemLogDto.getId() == null) {
            return "check id ! ";
        }
        Integer update = systemLogMenuService.update(systemLogDto);
        return String.valueOf(update);
    }

    //删除
    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        log.info("id:{}",id);
        Integer delete = systemLogMenuService.delete(id);
        return String.valueOf(delete);
    }

    //list 日志类型，字段名称
    @GetMapping(value = "/list")
    public String list(@RequestParam Integer type,@RequestParam String name){
        log.info("list type:{},name:{}",type,name);

        return "list";
    }

    //listAll
    @GetMapping(value = "/listAll")
    public List<SystemLogDto> listAll(){
        List<SystemLogDto> list = systemLogMenuService.listAll();
        return list;
    }




}
