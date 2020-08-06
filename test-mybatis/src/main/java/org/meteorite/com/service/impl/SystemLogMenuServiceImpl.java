package org.meteorite.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.meteorite.com.domain.entity.SystemLogMenu;
import org.meteorite.com.domain.mapper.SystemLogMenuMapper;
import org.meteorite.com.dto.SystemLogDto;
import org.meteorite.com.service.SystemLogMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SystemLogMenuServiceImpl implements SystemLogMenuService {


    @Autowired
    SystemLogMenuMapper systemLogMenuMapper;

    @Override
    public Integer save(SystemLogDto systemLogDto) {
        SystemLogMenu entity = transferEntity(systemLogDto);
        return systemLogMenuMapper.insert(entity);
    }

    @Override
    public Integer update(SystemLogDto systemLogDto) {
        SystemLogMenu entity = transferEntity(systemLogDto);
        entity.setId(systemLogDto.getId());
        return systemLogMenuMapper.updateById(entity);
    }

    @Override
    public Integer delete(Long id) {
        SystemLogMenu entity = new SystemLogMenu();
        entity.setIsDel(1);
        entity.setId(id);
        return systemLogMenuMapper.updateById(entity);
    }

    @Override
    public List<SystemLogDto> listAll() {
        QueryWrapper<SystemLogMenu>  wa = new QueryWrapper<>();
        List<SystemLogMenu> systemLogMenus = systemLogMenuMapper.selectList(wa);
        return transferEntityList(systemLogMenus);
    }

    private List<SystemLogDto> transferEntityList(List<SystemLogMenu> systemLogMenus) {
        if (CollectionUtils.isEmpty(systemLogMenus)) return null;
        List<SystemLogDto> systemLogDtos = new ArrayList<>();
        systemLogMenus.forEach(e->{
            systemLogDtos.add(transferDto(e));
        });
        return systemLogDtos;
    }

    private SystemLogDto transferDto(SystemLogMenu systemLogMenu) {

        SystemLogDto systemLogDto = new SystemLogDto();
        systemLogDto.setId(systemLogMenu.getId());
        systemLogDto.setLogType(systemLogMenu.getLogType());
        systemLogDto.setLogSource(systemLogMenu.getLogSource());
        systemLogDto.setFieldName(systemLogMenu.getFieldName());
        systemLogDto.setDictName(systemLogMenu.getDictName());
        systemLogDto.setRemark(systemLogMenu.getRemark());
        return systemLogDto;
    }

    private SystemLogMenu transferEntity(SystemLogDto systemLogDto) {

        if (Objects.isNull(systemLogDto)) return null;

        SystemLogMenu systemLogMenu = new SystemLogMenu();
        systemLogMenu.setLogType(systemLogDto.getLogType());
        systemLogMenu.setLogSource(systemLogDto.getLogSource());
        systemLogMenu.setFieldName(systemLogDto.getFieldName());
        systemLogMenu.setDictName(systemLogDto.getDictName());
        systemLogMenu.setRemark(systemLogDto.getRemark());

        return systemLogMenu;
    }
}
