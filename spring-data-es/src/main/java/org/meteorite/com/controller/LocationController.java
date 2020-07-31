package org.meteorite.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.domain.entity.Country;
import org.meteorite.com.dto.TestSystemLogReq;
import org.meteorite.com.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/es/location/")
public class LocationController {


    @Autowired
    CountryService countryService;



    @PostMapping(value = "/country")
    public Country country(@RequestBody Country country){
        log.info("LocationController country:{}",country);
        Country save = countryService.save(country);
        return save;
    }

}
