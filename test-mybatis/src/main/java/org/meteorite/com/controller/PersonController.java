package org.meteorite.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.dto.PersonDto;
import org.meteorite.com.service.PersonService;
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
@RequestMapping("/sys/person")
public class PersonController {

    @Autowired
    PersonService personService;
    //新增
    @PostMapping(value = "/add")
    public String add(@RequestBody PersonDto personDto){
        log.info("add :{}",personDto);
        Integer save = personService.save(personDto);
        return String.valueOf(save);
    }




    @GetMapping(value = "/listAll")
    public List<PersonDto> listAll(){
        List<PersonDto> list = personService.listAll();
        return list;
    }




}
