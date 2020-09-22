package org.meteorite.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.meteorite.com.domain.entity.Person;
import org.meteorite.com.domain.mapper.PersonMapper;
import org.meteorite.com.dto.PersonDto;
import org.meteorite.com.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonMapper personMapper;


    @Override
    public Integer save(PersonDto personDto) {
        Person person = new Person();
        person.setAddress(personDto.getAddress());
        person.setName(personDto.getName());
        return personMapper.insert(person);
    }

    @Override
    public List<PersonDto> listAll() {
        QueryWrapper<Person>  wa = new QueryWrapper<>();
        List<Person> people = personMapper.selectList(wa);
        List<PersonDto> personDtos = new ArrayList<>();
        people.stream().forEach(e->{
            PersonDto personDto = new PersonDto();
            personDto.setAddress(e.getAddress());
            personDto.setName(e.getName());
            personDtos.add(personDto);
        });
        return personDtos;
    }
}
