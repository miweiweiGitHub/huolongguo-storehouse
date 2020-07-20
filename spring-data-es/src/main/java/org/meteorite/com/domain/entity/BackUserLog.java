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
 * 后台用户日志
 * </p>
 *
 * @author liwei
 * @since 2020-05-09
 */
@Data
@Document(indexName = "back_user_log_index_#{T(com.evergrande.cloud.hdb.infra.util.SystemLogUtil).getYearMonthStr()}",type = "backuserlog")
public class BackUserLog implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    private String id;

    //时间
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private LocalDateTime operatorTime;
    //账号
    @Field(type = FieldType.Keyword)
    private String account;
    //名字
    @Field(type = FieldType.Text)
    private String name;
    //用户操作类型
    @Field(type = FieldType.Keyword)
    private String operatorType;
    //后台类型
    @Field(type = FieldType.Keyword)
    private String backstageType;

    //IP地址
    @Field(type = FieldType.Ip)
    private String ipAddress;
    //状态
    @Field(type = FieldType.Keyword)
    private String state;
    //关键字
    @Field(type = FieldType.Text)
    private String keyWord;


}
