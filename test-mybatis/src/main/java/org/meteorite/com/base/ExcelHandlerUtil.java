package org.meteorite.com.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author EX_052100260
 * @title: ExcelHandlerUtil
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-9-22 15:15
 */
@Slf4j
public class ExcelHandlerUtil {

    private static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String XLS_CONTENT_TYPE = "application/vnd.ms-excel";

    public static void main(String[] args) {
        Set<String> phones = getDataByExcel("https://hdb-hft-oss.evergrandebao.com/image/LINK/3866089770dff42214be623925524f15?Expires=1758424223&OSSAccessKeyId=LTAIgYXaMjqB4CgE&Signature=zzv49OV7l9WCYFC6ljfXS69oEqE%3D&fileName=mobile.xlsx");
        System.out.println(phones);
    }


    /**
     * 根据excel模板地址解析
     *
     * @param fileUrl
     * @return
     */
    public static Set<String> getDataByExcel(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) {
            return Collections.emptySet();
        }

        //处理oss路径fileUrl参数带空格问题
        StringBuilder sb = new StringBuilder();
        String[] qms = fileUrl.split("\\?");
        sb.append(qms[0]);//prfix url
        String[] ess;
        String[] ass;
        if(qms.length >= 2){
            sb.append("?");
            ass = qms[1].split("&");
            if(ass.length > 0) {
                for (String as : ass) {
                    ess = as.split("=");
                    sb.append(ess[0]).append("=").append(ess[1].replaceAll("\\s","%20")).append("&");
                }
            }
        }

        Workbook wb = null;
        URLConnection urlConnection;
        try {
            URL url = new URL(sb.toString());
            urlConnection = url.openConnection();
            String contentType = urlConnection.getContentType();
            InputStream input = urlConnection.getInputStream();

            if (XLS_CONTENT_TYPE.equals(contentType)) {
                //文件流对象
                wb = new HSSFWorkbook(input);
            } else if (XLSX_CONTENT_TYPE.equals(contentType)) {
                wb = new XSSFWorkbook(input);
            } else {
                log.error("文件类型[{}]异常", contentType);
            }
        } catch (IOException e) {
            log.error("文件解析异常", e);
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

}
