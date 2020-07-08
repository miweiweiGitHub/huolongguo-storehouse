package org.meteorite.com.dto.log;


import lombok.Data;

/**
 * 系统日志添加
 * 请求参数封装
 * </p>
 *
 * @author liwei
 * @since 2020-05-08
 */
@Data
public class SystemLogReq {


    private String logBody;

    private String queryConditionBody;

}
