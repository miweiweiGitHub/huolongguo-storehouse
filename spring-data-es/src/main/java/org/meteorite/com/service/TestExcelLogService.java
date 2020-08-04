package org.meteorite.com.service;

import org.meteorite.com.domain.entity.LoginLog;
import org.meteorite.com.dto.EasyExcelHeaderDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 * 测试导出 Excel 文档数据
 *
 * @author liwei
 * @since 2020-06-05
 *
 */
public interface TestExcelLogService {


    List<EasyExcelHeaderDto> getData();

}
