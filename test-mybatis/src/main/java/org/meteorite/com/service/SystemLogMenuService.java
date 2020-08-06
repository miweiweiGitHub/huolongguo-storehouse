package org.meteorite.com.service;

import org.meteorite.com.dto.SystemLogDto;

import java.util.List;

public interface SystemLogMenuService {
    Integer save(SystemLogDto systemLogDto);

    Integer update(SystemLogDto systemLogDto);

    Integer delete(Long id);

    List<SystemLogDto> listAll();

}
