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
 * 短信日志
 * </p>
 *
 * @author liwei
 * @since 2020-05-09
 */
@Data
@Document(indexName = "sms_log_index_#{T(com.evergrande.cloud.hdb.infra.util.SystemLogUtil).getYearMonthStr()}",type = "smslog")
public class SMSLog implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    private String id;

    //发送时间
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private LocalDateTime sendTime;
    //短信服务商
    @Field(type = FieldType.Keyword)
    private String smsServiceProvider;
    //使用商家
    @Field(type = FieldType.Keyword)
    private String useMerchant;
    //手机号
    @Field(type = FieldType.Keyword)
    private String phone;
    //短信内容
    @Field(type = FieldType.Text)
    private String smsContent;
    //状态
    @Field(type = FieldType.Keyword)
    private String state;


}
