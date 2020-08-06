package org.meteorite.com.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SystemLogDto {

    private Long id;

    private Integer logType;

    private String logSource;

    private String fieldName;

    private String dictName;

    private String remark;


}
