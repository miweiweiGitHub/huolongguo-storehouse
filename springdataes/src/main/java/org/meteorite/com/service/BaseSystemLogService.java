package org.meteorite.com.service;



import org.meteorite.com.base.em.SystemLogSourceEnum;
import org.springframework.data.domain.Page;

/**
 *
 * 系统日志父类接口
 *
 * @author liwei
 * @since 2020-05-08
 *
 */
public interface BaseSystemLogService<T> {

    /**
     * 添加日志
     * @param logBody 日志内容json
     */
    void add(String logBody);

    /**
     * 获取日志枚举类型
     * @return 日志类型枚举
     */
    SystemLogSourceEnum getLogType();

    /**
     * 分页查询日志,无鉴权脱敏需求
     * @param queryConditionBody 查询条件
     * @param <T> 日志实体类型
     * @return 返回查询结果
     */
    <T> Page<T> getPageList(String queryConditionBody);

    /**
     * 清理历史日志实现
     *
     * 保留方法，暂未使用
     */
    void cleanHistoryLog();

    /**
     * 获取日志泛型class
     * @return class
     */
    Class<T> getGenericClass();

}
