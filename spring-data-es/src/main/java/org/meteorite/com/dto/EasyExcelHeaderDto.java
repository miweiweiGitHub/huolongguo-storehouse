package org.meteorite.com.dto;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
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
@ColumnWidth(20) // 定义列宽
public class EasyExcelHeaderDto {

    @ExcelProperty( value = "手机号",index = 0)
    private String phone;



}
