package org.meteorite.com.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author EX_052100260
 * @title: DictDto
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-9-15 9:52
 */
@Data
public class DictDto implements Serializable {
    private static final long serialVersionUID = 4565117268345709918L;

    private String dictType;
    private String dictCode;
    private String group;
    private String parentCode;
    private String name;
    private String alias;
    private String value;
    private Integer sort;
}
