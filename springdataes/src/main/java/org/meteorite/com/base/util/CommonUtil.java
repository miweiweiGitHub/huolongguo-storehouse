package org.meteorite.com.base.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.joda.time.format.DateTimeFormat;
import org.meteorite.com.base.constant.SystemLogConstant;
import org.meteorite.com.base.em.SystemLogSourceEnum;
import org.meteorite.com.domain.entity.TestLog;
import org.meteorite.com.dto.BaseLogQueryReq;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 *
 */
@Slf4j
public class CommonUtil {


    /**
     * 获取当前年月
     *
     * @return 年月
     */
    public static String getYearMonthStr() {

        Calendar instance = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_M");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_M_dd_HH_mm");
        String format = simpleDateFormat.format(instance.getTime());

        return format;
    }

    public static String getNextMonthStr(){
        Calendar instance = Calendar.getInstance();
        instance.add(instance.MINUTE,1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_M_dd_HH_mm");
        String format = simpleDateFormat.format(instance.getTime());
        return format;
    }

    public static String getPreMonthStr(){
        Calendar instance = Calendar.getInstance();
        instance.add(instance.MINUTE,-1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_M_dd_HH_mm");
        String format = simpleDateFormat.format(instance.getTime());
        return format;
    }


    public static TestLog getTestLog() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone","18688736689");
        jsonObject.put("name","火龙果test");
        jsonObject.put("ipAddress","127.0.0.1");
        jsonObject.put("requestParam","乘风破浪会有时，直挂云帆济沧海");
        DateTimeFormatter ss =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = LocalDateTime.now().format(ss);
        jsonObject.put("testTime",format);
        return JSONObject.parseObject(jsonObject.toJSONString(), TestLog.class);
    }

    public static Pageable getPageable(String sortName, int page, int size) {
        //*************************分页数据封装*******************************************/
        //默认按照 登录时间倒序排列 近三十天
        Sort sort = new Sort(Sort.Direction.DESC, sortName);
        //判断page的大小，ES分页默认是从0开始的
        page = page > 0 ? page - 1 : page;
        //判断size的大小，默认大小 10 （当前的框架已经做了处理）
//        size = size > 0 ? size : size;
        return PageRequest.of(page, size, sort);
    }

    public static BoolQueryBuilder handlerBaseLogQueryReqQuerySearch(String timeName, BaseLogQueryReq baseLogQueryReq) {

        //*************************构建基础ES查询条件*******************************************/
        String startTime = baseLogQueryReq.getStartTime();
        String endTime = baseLogQueryReq.getEndTime();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(timeName).gte(startTime).lte(endTime);
        boolQueryBuilder.must(rangeQueryBuilder);


        //*************************根据额外条件 完善ES查询条件*******************************************/
        String phone = baseLogQueryReq.getPhone();
        if (StringUtils.isNotBlank(phone)) {
            //精确查询
            boolQueryBuilder.must(QueryBuilders.termQuery(SystemLogConstant.QueryCondition.PHONE, phone));
        }
        String operatorNo = baseLogQueryReq.getOperatorNo();
        if (StringUtils.isNotBlank(operatorNo)) {
            //精确查询
            boolQueryBuilder.must(QueryBuilders.termQuery(SystemLogConstant.QueryCondition.OPERATOR_NO, operatorNo));
        }
        String operatorType = baseLogQueryReq.getOperatorType();
        if (StringUtils.isNotBlank(operatorType)) {
            boolQueryBuilder.must(QueryBuilders.termQuery(SystemLogConstant.QueryCondition.OPERATOR_TYPE, operatorType));
        }
        String requestParam = baseLogQueryReq.getRequestParam();
        if (StringUtils.isNotBlank(requestParam)) {
            //模糊匹配
            boolQueryBuilder.must(QueryBuilders.matchQuery(SystemLogConstant.QueryCondition.REQUEST_PARAM, requestParam));
        }
        String requestType = baseLogQueryReq.getRequestType();
        if (StringUtils.isNotBlank(requestType)) {
            boolQueryBuilder.must(QueryBuilders.termQuery(SystemLogConstant.QueryCondition.REQUEST_TYPE, requestType));
        }
        String keyWord = baseLogQueryReq.getKeyWord();
        if (StringUtils.isNotBlank(keyWord)) {
            //模糊匹配
            boolQueryBuilder.must(QueryBuilders.matchQuery(SystemLogConstant.QueryCondition.KEY_WORD, keyWord));
        }

        return boolQueryBuilder;
    }

    public static void main(String[] args) {
        String nextMonthStr = getNextMonthStr();
        DateTimeFormatter ss =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = LocalDateTime.now().format(ss);
        System.out.println(LocalDateTime.parse(format,ss));
    }
}
