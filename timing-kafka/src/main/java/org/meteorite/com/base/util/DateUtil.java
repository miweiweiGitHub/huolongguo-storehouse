package org.meteorite.com.base.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author EX_052100260
 * @title: DateUtil
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-10-26 15:46
 */
@Slf4j
public class DateUtil {


    public static final String DEFALT_DATE_FORMAT = "yyyy-MM-dd HH:MM:SS";
    public static final String DATE_FORMATE_YYYYMMDDHHMMSS = "yyyyMMddHHMMSS";

    public static String getDate(String formate) {
        SimpleDateFormat formatter= new SimpleDateFormat(formate);
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static Long getLastDayStartTimeStamp(int i) {

        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.MINUTE, i);
        log.info("getLastDayStartTimeStamp  time:{}",ca.getTimeInMillis());
        return ca.getTimeInMillis();
    }

    public static void main(String[] args) {
        StringBuilder sbuilder = new StringBuilder();
        String append = sbuilder.append("test_").append("topic").append("0").toString();
       log.info(append);

        List<Long> offsets = new ArrayList();
        offsets.add(20L);
        offsets.add(8L);
        offsets.add(81L);
        offsets.add(86L);
        offsets.add(89L);
        log.info(Collections.max(offsets).toString());



    }
}
