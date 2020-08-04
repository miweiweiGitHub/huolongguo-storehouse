package org.meteorite.com.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.InternalOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.meteorite.com.domain.entity.Country;
import org.meteorite.com.domain.repository.CountryRepository;
import org.meteorite.com.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

/**
 * @Author 火龙果
 * @Date 2020/7/31 11:39
 * @Version 1.0
 **/
@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Country save(Country country) {
        return countryRepository.save(country);
    }


    public Country test(Country country) {


        return null;
    }

}
