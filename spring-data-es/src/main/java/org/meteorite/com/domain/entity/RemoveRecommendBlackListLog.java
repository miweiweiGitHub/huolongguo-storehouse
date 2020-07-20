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
 * 解除推荐黑名单日志
 * </p>
 *
 * @author liwei
 * @since 2020-05-09
 */
@Data
@Document(indexName = "remove_recommend_log_index_#{T(com.evergrande.cloud.hdb.infra.util.SystemLogUtil).getYearMonthStr()}",type = "removerecommendblacklistlog")
public class RemoveRecommendBlackListLog implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    private String id;


    //操作时间
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private LocalDateTime operatorTime;
    //经纪人姓名
    @Field(type = FieldType.Text)
    private String agentName;
    //经纪人手机号
    @Field(type = FieldType.Keyword)
    private String phone;
    //移除接口路径
    @Field(type = FieldType.Keyword)
    private String removeInterfaceRoute;
    //操作人账号
    @Field(type = FieldType.Keyword)
    private String operatorNo;
    //操作人
    @Field(type = FieldType.Text)
    private String operator;
    //IP地址
    @Field(type = FieldType.Ip)
    private String ipAddress;


}
