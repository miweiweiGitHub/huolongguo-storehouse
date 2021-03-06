package org.meteorite.com.domain.repository;


import org.meteorite.com.domain.entity.TestLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author liwei
 * @since 2020-05-19
 *
 */
@Repository
public interface TestRepository extends ElasticsearchRepository<TestLog,String> {


}
