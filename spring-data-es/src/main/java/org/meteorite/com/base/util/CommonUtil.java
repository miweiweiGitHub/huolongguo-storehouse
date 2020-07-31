package org.meteorite.com.base.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.meteorite.com.base.constant.SystemLogConstant;
import org.meteorite.com.dto.BaseLogQueryReq;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

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
        //yyyy_M_dd_HH_mm
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_M");
        String format = simpleDateFormat.format(instance.getTime());
        log.info("get getYearMonthStr :{}",format);
        return format;
    }

    /**
     * 获取下一个月
     * @return
     */
    public static String getPlusMonthStr(){
        Calendar instance = Calendar.getInstance();
        instance.add(instance.MONTH,1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_M");
        String format = simpleDateFormat.format(instance.getTime());
        log.info("get getPlusMonthStr :{}",format);
        return format;
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
            //模糊匹配 完整匹配查询字段
            MatchPhraseQueryBuilder requestParamQuery = QueryBuilders.matchPhraseQuery(SystemLogConstant.QueryCondition.REQUEST_PARAM, requestParam);
//            //模糊匹配
//            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(SystemLogConstant.QueryCondition.REQUEST_PARAM, requestParam);
//            //匹配完整的查询字段
//            matchQueryBuilder.operator(Operator.AND);
            boolQueryBuilder.must(requestParamQuery);
        }
        String requestType = baseLogQueryReq.getRequestType();
        if (StringUtils.isNotBlank(requestType)) {
            boolQueryBuilder.must(QueryBuilders.termQuery(SystemLogConstant.QueryCondition.REQUEST_TYPE, requestType));
        }
        String keyWord = baseLogQueryReq.getKeyWord();
        if (StringUtils.isNotBlank(keyWord)) {
            //模糊匹配 完整匹配查询字段
            MatchPhraseQueryBuilder keyWordQuery = QueryBuilders.matchPhraseQuery(SystemLogConstant.QueryCondition.KEY_WORD, keyWord);

            //            //模糊匹配
//            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(SystemLogConstant.QueryCondition.KEY_WORD, keyWord);
//            //匹配完整的查询字段
//            matchQueryBuilder.operator(Operator.AND);
            boolQueryBuilder.must(keyWordQuery);
        }

        return boolQueryBuilder;
    }


    public static void main(String[] args) {
        Random random = new Random();
        int i = random.nextInt(10);
        log.info("test:{}",i);
    }
}
