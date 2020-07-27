package org.meteorite.com.service;

import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;

import java.util.List;

public interface BucketService {
    Bucket creat(String name);

    Bucket catBucket(String name);

    List<Bucket> listBucket();

    void removeBucket(String name);

    void setBucketAcl(String name, CannedAccessControlList publicRead);
}
