package org.meteorite.com.domian.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author EX_052100260
 * @title: Milk
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-9-8 8:45
 */
@Document(collection = "milk")
public class Milk implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    private Long id;

    //生产日期
    @Field("create_time")
    private String createTime;
    //过期时间
    @Field("expiration_time")
    private String expirationTime;
    //名字
    @Field("name")
    private String name;
    //品牌商
    @Field("brand")
    private String brand;
    //保存温度
    @Field("temperature")
    private int temperature;
    //含脂量
    @Field("fat")
    private int fat;



}
