package org.meteorite.com.dto;

import lombok.Data;

/**
 * <p>
 * 日志查询基础请求
 * </p>
 *
 * @author liwei
 * @since 2020-05-11
 */
@Data
public class BaseLogQueryReq {

    private int logSourceType;

    private Integer page;
    private Integer size;

    private String startTime;
    private String endTime;

    private String phone;
    private String requestParam;
    private String requestType;
    private String keyWord;


    private String operatorNo;
    private String operatorType;




}
