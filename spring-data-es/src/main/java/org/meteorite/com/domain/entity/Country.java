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
import java.util.List;


/**
 *
 * 国家
 * </p>
 *
 * @author liwei
 * @since 2020-05-19
 */
@Data
@Document(indexName = "country_index",type = "country")
public class Country implements Serializable  {

    private static final long serialVersionUID = -1L;

    @Id
    private String id;


    //登录时间
    @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private LocalDateTime testTime;

    //英文名
    @Field(type = FieldType.Keyword)
    private String enName;

    //名字
    @Field(type = FieldType.Keyword)
    private String name;

    //城市信息
    @Field(type = FieldType.Attachment)
    private List<City> citys;



}
