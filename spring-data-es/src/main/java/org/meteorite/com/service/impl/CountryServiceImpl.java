package org.meteorite.com.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.domain.entity.Country;
import org.meteorite.com.domain.repository.CountryRepository;
import org.meteorite.com.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 火龙果
 * @Date 2020/7/31 11:39
 * @Version 1.0
 **/
@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Override
    public Country save(Country country) {
        return countryRepository.save(country);
    }
}
