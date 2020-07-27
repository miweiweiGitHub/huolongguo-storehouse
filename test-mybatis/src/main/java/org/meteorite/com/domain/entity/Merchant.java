package org.meteorite.com.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_merchant")
public class Merchant {

    @TableId(type = IdType.AUTO)
    @TableField("uuid")
    private String uuid;

    @TableField("merchant_name")
    private String merchantName;

    @TableField("merchant_key")
    private String merchantKey;

    @TableField("merchant_secret")
    private String merchantSecret;

    @TableField("icon")
    private String icon;
    @TableField("status")
    private int status;
    @TableField("del")
    private int del;

    @TableField("contact_name")
    private String contactName;
    @TableField("contact_phone")
    private String contactPhone;
    @TableField("expire_time")
    private LocalDateTime expireTime;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("update_time")
    private LocalDateTime updateTime;



}
