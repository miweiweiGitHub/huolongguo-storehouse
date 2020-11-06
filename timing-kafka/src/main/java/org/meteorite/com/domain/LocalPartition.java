package org.meteorite.com.domain;

import lombok.Data;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.io.Serializable;
import java.util.Map;

/**
 * @author EX_052100260
 * @title: LocalPartition
 * @projectName  hdb-msg-push
 * @description: TODO
 * @date 2020-10-28 10:43
 */
@Data
public class LocalPartition implements Partitioner, Serializable {

    private String topic;
    private Integer partition;
    private Long position;



    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
