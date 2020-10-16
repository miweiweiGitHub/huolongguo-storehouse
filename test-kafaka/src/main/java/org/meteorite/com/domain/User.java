package org.meteorite.com.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author EX_052100260
 * @title: User
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-10-10 9:50
 */
@Data
public class User {
    private Integer id;
    private String userName;
    private String description;
    //@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
