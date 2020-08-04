package org.meteorite.com.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.meteorite.com.base.constant.SystemLogConstant;
import org.meteorite.com.domain.entity.LoginLog;
import org.meteorite.com.domain.repository.LoginRepository;
import org.meteorite.com.dto.EasyExcelHeaderDto;
import org.meteorite.com.service.TestExcelLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 火龙果
 * @Date 2020/8/4 10:56
 * @Version 1.0
 **/
@Service
@Slf4j
public class TestExcelLogServiceImpl implements TestExcelLogService {

    @Autowired
    LoginRepository loginRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public List<EasyExcelHeaderDto> getData() {
        List<LoginLog> loginLogs = new ArrayList<>();
        List<EasyExcelHeaderDto> easyExcelHeaderDtos = new ArrayList<>();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();



        String startTime = "2020-07-07 00:00:00";
        String endTime = "2020-07-09 23:59:59";
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(SystemLogConstant.QueryCondition.LOGIN_TIME).gte(startTime).lte(endTime);
        boolQueryBuilder.must(rangeQueryBuilder);

        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(boolQueryBuilder);
        nativeSearchQuery.addIndices(SystemLogConstant.QueryIndex.LOGIN_LOG);
//        nativeSearchQuery.setPageable(PageRequest.of(1,20));
        Iterable<LoginLog> search = loginRepository.search(boolQueryBuilder);


        search.forEach(e->{
            loginLogs.add(e);
            EasyExcelHeaderDto easyExcelHeaderDto = new EasyExcelHeaderDto();
            easyExcelHeaderDto.setPhone(e.getPhone());
            easyExcelHeaderDtos.add(easyExcelHeaderDto);
        });

        List<EasyExcelHeaderDto> collect = easyExcelHeaderDtos.stream().distinct().collect(Collectors.toList());
        return collect;
    }

    public String test(){

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        AggregationBuilders


        return "";
    }

}
