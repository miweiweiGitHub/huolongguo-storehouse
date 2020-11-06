package org.meteorite.com.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
public class ExcelUtils {


    private static Boolean validExxel(String path) {
        String xls = "^.+\\.(?i)(xls)$";    // 正则表达式判断是不是以 .xls 结尾的文件
        String xlsx = "^.+\\.(?i)(xlsx)$";    // 正则表达式判断是不是以 .xlsx 结尾的文件
        if (path == null || !(path.matches(xls) || path.matches(xlsx))) {
            log.error("此文件不是 Excel 文件！");
        }
        return true;
    }
    /*
     * @author 下载excel
     * */

    public static <T> XSSFWorkbook getWorkbook(Collection<T> dataSet, String[] params, String[] titles) {
        // 校验变量和预期输出excel列数是否相同
        if (params.length != titles.length) {
            log.error("变量参数有误！");
        }
        // 存储每一行的数据
        List<String[]> list = new ArrayList<>();
        for (Object obj : dataSet) {
            // 获取到每一行的属性值数组
            list.add(getValues(obj, params));
        }
        return getWorkbook(titles, list);
    }

    public static XSSFWorkbook getWorkbook(String[] titles, List<String[]> list) {
        // 定义表头
        String[] title = titles;
        // 创建excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表sheet
        XSSFSheet sheet = workbook.createSheet();
        // 创建第一行
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = null;
        // 插入第一行数据的表头
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        int idx = 1;
        for (String[] strings : list) {
            XSSFRow nrow = sheet.createRow(idx++);
            XSSFCell ncell = null;
            for (int i = 0; i < strings.length; i++) {
                ncell = nrow.createCell(i);
                ncell.setCellValue(strings[i]);
            }
        }
        // 设置自动列宽
        for (int i = 0; i < title.length; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 16 / 10);
        }
        return workbook;
    }

    // 根据需要输出的变量名数组获取属性值
    public static String[] getValues(Object object, String[] params) {
        String[] values = new String[params.length];
        try {
            for (int i = 0; i < params.length; i++) {
                Field field = object.getClass().getDeclaredField(params[i]);
                // 设置访问权限为true
                field.setAccessible(true);
                // 获取属性
                // 如果属性有涉及基本变量的做一个转换
                if (!ObjectUtils.isEmpty(field.get(object))) {
                    values[i] = field.get(object).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * 根据excel模板地址解析
     *
     * @param path
     * @return
     */
    public static Set<String> getDataByExcel(String path) {
        if (StringUtils.isBlank(path)) {
            return Collections.emptySet();
        }
        String sub = path.substring(path.lastIndexOf("."));
        File file = new File(path);
        FileInputStream input = null;
        Workbook wb = null;
        try {
            input = new FileInputStream(file);

            if(".xls".equals(sub)){
                 wb = new HSSFWorkbook(input);
            }else if(".xlsx".equals(sub)){
                 wb = new XSSFWorkbook(input);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        Set<String> data = new HashSet<>();
        Sheet sheet = wb.getSheetAt(0);
        //第一行是列名，所以不读
        int firstRowIndex = sheet.getFirstRowNum() + 1;
        int lastRowIndex = sheet.getLastRowNum();

        //遍历行
        for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
            Row row = sheet.getRow(rIndex);
            if (row != null) {
                Cell cell = row.getCell(0);
                cell.setCellType(CellType.STRING);
                String stringCellValue = cell.getStringCellValue();
                data.add(stringCellValue);
            }
        }
        return data;
    }

    public static void main(String[] args) {
        Set<String> dataByExcel = getDataByExcel("C:\\Users\\EX_052100260\\Documents\\WeChat Files\\wxid_to2prydnx9se22\\FileStorage\\File\\2020-11\\mobile (5).xlsx");
        dataByExcel.stream().forEach(e->{
            if (e.equals("13510830235")) {
                log.info("test :{}",e);
            }

        });
        log.info("dataByExcel:{}",dataByExcel.size());
    }
}
