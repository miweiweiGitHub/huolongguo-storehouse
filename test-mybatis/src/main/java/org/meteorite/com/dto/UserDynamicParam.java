package org.meteorite.com.dto;

import lombok.Data;

/**
 * @author EX_052100260
 * @title: UserDynamicParam
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-9-15 9:51
 */
@Data
public class UserDynamicParam {
    private String uid;
    private String phone;
    private String name;
    private String level;

    public UserDynamicParam(String name, String level) {
        this.name = name == null ? "" : name;
        this.level = null == level ? "" : level;
    }

    public UserDynamicParam(){

    }
}
