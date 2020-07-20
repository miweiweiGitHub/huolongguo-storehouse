package org.meteorite.com.domain.entity;//package com.evergrande.cloud.hdb.domain.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.DateFormat;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//import java.io.Serializable;
//import java.time.LocalDateTime;
//
///**
// *
// * 在线房源操作日志
// * </p>
// *
// * @author liwei
// * @since 2020-05-09
// */
//@Data
//@Document(indexName = "online_house_log_index",type = "onlinehousesourceoperatorlog")
//@ApiModel(value = "OnlineHouseSourceOperatorLog对象", description = "在线房源操作日志")
//public class OnlineHouseSourceOperatorLog implements Serializable {
//
//    private static final long serialVersionUID = -1L;
//
//    @Id
//    @ApiModelProperty(value = "id")
//    private String id;
//
//    //操作时间
//    @ApiModelProperty(value = "操作时间")
//    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
//    private LocalDateTime operatorTime;
//    //项目名称
//    @ApiModelProperty(value = "项目名称")
//    @Field(type = FieldType.Text)
//    private String projectName;
//    //房间名称
//    @ApiModelProperty(value = "房间名称")
//    @Field(type = FieldType.Text)
//    private String roomName;
//    //操作类型
//    @ApiModelProperty(value = "操作类型")
//    @Field(type = FieldType.Keyword)
//    private String operatorType;
//    //操作人账号
//    @ApiModelProperty(value = "操作人账号")
//    @Field(type = FieldType.Keyword)
//    private String operatorNo;
//    //操作人
//    @ApiModelProperty(value = "操作人")
//    @Field(type = FieldType.Text)
//    private String operator;
//    //IP地址
//    @ApiModelProperty(value = "IP地址")
//    @Field(type = FieldType.Ip)
//    private String ipAddress;
//
//
//
//}
