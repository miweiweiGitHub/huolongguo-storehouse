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
 * 账号处理日志
 * </p>
 *
 * @author liwei
 * @since 2020-05-09
 */
@Data
@Document(indexName = "account_handler_log_index_#{T(com.evergrande.cloud.hdb.infra.util.SystemLogUtil).getYearMonthStr()}",type = "accounthandlerlog")
public class AccountHandlerLog implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    private String id;
    //操作时间
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private LocalDateTime operatorTime;
    //被处理账号
    @Field(type = FieldType.Keyword)
    private String processedAccount;
    //被处理账号名称
    @Field(type = FieldType.Text)
    private String processedAccountName;
    //所属公司
    @Field(type = FieldType.Text)
    private String affiliatedCompany;
    //项目数据权限
    @Field(type = FieldType.Keyword)
    private String projectDataPower;
    //角色
    @Field(type = FieldType.Keyword)
    private String role;

    //操作类型
    @Field(type = FieldType.Keyword)
    private String operatorType;
    //操作人账号
    @Field(type = FieldType.Keyword)
    private String operatorNo;
    //操作人名字
    @Field(type = FieldType.Text)
    private String operatorName;
    //操作状态
    @Field(type = FieldType.Keyword)
    private String operatorState;
    //IP地址
    @Field(type = FieldType.Ip)
    private String ipAddress;



}
