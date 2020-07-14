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
 * BMP对接日志
 * </p>
 *
 * @author liwei
 * @since 2020-05-09
 */
@Data
@Document(indexName = "bmp_face_log_index_#{T(com.evergrande.cloud.hdb.infra.util.SystemLogUtil).getYearMonthStr()}",type = "bmpfacelog")
public class BmpFaceLog implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    private String id;

    //请求时间
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private LocalDateTime requestTime;
    //BMP流程号
    @Field(type = FieldType.Integer)
    private Integer bpmProcessNo;
    //需求类型
    @Field(type = FieldType.Keyword)
    private String requestType;
    //业务类型
    @Field(type = FieldType.Keyword)
    private String businessType;

    //姓名
    @Field(type = FieldType.Text)
    private String name;
    //登录账号
    @Field(type = FieldType.Keyword)
    private String loginNo;
    //手机号
    @Field(type = FieldType.Keyword)
    private String phone;

    //项目名称
    @Field(type = FieldType.Text)
    private String projectName;
    //项目所属公司
    @Field(type = FieldType.Text)
    private String projectCompany;
    //项目所属省份
    @Field(type = FieldType.Text)
    private String projectProvence;
    //项目所属城市
    @Field(type = FieldType.Text)
    private String projectCity;
    //项目原账号
    @Field(type = FieldType.Keyword)
    private String projectOriginalAccount;
    //项目现账号
    @Field(type = FieldType.Keyword)
    private String projectCurrentAccount;

    //项目原角色
    @Field(type = FieldType.Keyword)
    private String projectOriginalRole;
    //项目现角色
    @Field(type = FieldType.Keyword)
    private String projectCurrentRole;

    //处理结果
    @Field(type = FieldType.Keyword)
    private String handlerResult;


}
