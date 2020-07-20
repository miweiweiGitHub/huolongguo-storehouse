package org.meteorite.com.dto;


import lombok.Data;

/**
 * 测试系统日志添加
 * 请求参数封装
 * </p>
 *
 * @author liwei
 * @since 2020-05-23
 */
@Data
public class TestSystemLogReq {


    private String topic;

    private String message;

}
