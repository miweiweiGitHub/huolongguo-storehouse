package org.meteorite.com.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 *
 * 登录日志
 * </p>
 *
 * @author liwei
 * @since 2020-05-09
 */
@Data
@Document(indexName = "login_log_index_#{T(org.meteorite.com.base.util.CommonUtil).getYearStr()}",type = "loginlog")
public class LoginLog implements Serializable  {

    private static final long serialVersionUID = -1L;

    @Id
    private String id;


    //登录时间
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private LocalDateTime loginTime;
    //手机号
    @Field(type = FieldType.Keyword)
    private String phone;
    //ip地址
    @Field(type = FieldType.Ip)
    private String ipAddress;


}
