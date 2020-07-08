package org.meteorite.com.service;

import org.springframework.data.domain.Page;

/**
 *
 * 特殊系统日志父类接口
 *
 * @author liwei
 * @since 2020-06-05
 *
 */
public interface SpecialSystemLogService<T> extends BaseSystemLogService<T> {



    /**
     * 分页查询日志,有鉴权脱敏需求
     * @param queryConditionBody 查询条件
     * @param fieldAuthorityResult 用户信息
     * @param <T>
     * @return
     */
    <T> Page<T> getDesensitizedPageList(String queryConditionBody, String fieldAuthorityResult);
}
