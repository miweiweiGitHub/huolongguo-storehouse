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
 * 第三方平台（入）日志
 * </p>
 *
 * @author liwei
 * @since 2020-05-09
 */
@Data
@Document(indexName = "third_in_log_index_#{T(com.evergrande.cloud.hdb.infra.util.SystemLogUtil).getYearMonthStr()}",type = "thirdpartinlog")
public class ThirdPartInLog implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    private String id;

    //请求时间
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private LocalDateTime requestTime;
    //请求类型
    @Field(type = FieldType.Keyword)
    private String requestType;

    //路径
    @Field(type = FieldType.Keyword)
    private String route;
    //请求参数
    @Field(type = FieldType.Text)
    private String requestParam;
    //请求结果
    @Field(type = FieldType.Text)
    private String requestResult;
    //关键字
    @Field(type = FieldType.Text)
    private String keyWord;


}
