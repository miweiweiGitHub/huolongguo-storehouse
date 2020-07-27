package org.meteorite.com.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.base.configuration.OssConfig;
import org.meteorite.com.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BucketServiceImpl implements BucketService {

    @Autowired
    OssConfig ossConfig;



    @Override
    public Bucket creat(String name) {
        OSSClient ossClient = ossConfig.getOssClient();
        if (ossClient.doesBucketExist(name)) {
            BucketInfo bucketInfo = ossClient.getBucketInfo(name);
            return bucketInfo.getBucket();
        }

        CreateBucketRequest request = new CreateBucketRequest(name);
        request.setCannedACL(CannedAccessControlList.PublicRead);
        return ossClient.createBucket(request);
    }


    @Override
    public Bucket catBucket(String name) {
        OSSClient ossClient = ossConfig.getOssClient();
        BucketInfo bucketInfo = ossClient.getBucketInfo(name);
        if (null == bucketInfo) {
            return null;
        }
        return bucketInfo.getBucket();
    }

    @Override
    public List<Bucket> listBucket() {
        OSSClient ossClient = ossConfig.getOssClient();
        return ossClient.listBuckets();
    }

    @Override
    public void removeBucket(String name) {
        OSSClient ossClient = ossConfig.getOssClient();
        ossClient.deleteBucket(name);

    }

    @Override
    public void setBucketAcl(String name, CannedAccessControlList cannedAccessControlList) {
        OSSClient ossClient = ossConfig.getOssClient();
        ossClient.setBucketAcl(name,cannedAccessControlList);
    }
}
