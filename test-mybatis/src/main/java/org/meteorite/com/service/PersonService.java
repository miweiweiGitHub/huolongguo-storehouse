package org.meteorite.com.service;

import org.meteorite.com.dto.PersonDto;

import java.util.List;

public interface PersonService {


    Integer save(PersonDto personDto);

    List<PersonDto> listAll();

}
