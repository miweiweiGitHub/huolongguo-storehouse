package org.meteorite.com.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.base.configuration.OssConfig;
import org.meteorite.com.dto.FileDTO;
import org.meteorite.com.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    OssConfig ossConfig;

//    public OSSClient ossClient = ossConfig.getOssClient();

    @Override
    public FileDTO upLoad(File file) {
        // 判断文件
        if (file == null) {
            return null;
        }
        OSSClient ossClient = ossConfig.getOssClient();
        log.info("------OSS文件上传开始--------" + file.getName());
        String bucketName = ossConfig.getBucketname();
        String fileHost = ossConfig.getFilehost();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);

        try {
            // 判断容器是否存在,不存在就创建
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            // 设置文件路径和名称
            String fileUrl = fileHost + "/" + (dateStr + "/" + uuid) + "-" + file.getName();
            // 设置权限(公开读)
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));

            if (result != null) {
                log.info("upload result:{}", result);
                log.info("------OSS文件上传成功------" + fileUrl);
                return new FileDTO(
                        file.length(),//文件大小
                        fileUrl,//文件的绝对路径
                        ossConfig.getViewurl() + "/" + fileUrl,//文件的web访问地址
                        suffix,//文件后缀
                        "",//存储的bucket
                        bucketName,//原文件名
                        fileHost//存储的文件夹
                );

            }
        } catch (OSSException oe) {
            log.error(oe.getMessage());
        } catch (ClientException ce) {
            log.error(ce.getErrorMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    @Override
    public List<String> getObjectList() {
        List<String> listRe = new ArrayList<>();
        try {
            log.info("===========>查询文件名列表");
            CreateBucketRequest request = new CreateBucketRequest(ossConfig.getBucketname());
            request.setCannedACL(CannedAccessControlList.PublicRead);
            OSSClient ossClient = ossConfig.getOssClient();
            Bucket bucket = ossClient.createBucket(request);
//            boolean b = ossClient.doesBucketExist(ossConfig.getBucketname());
//            List<Bucket> buckets = ossClient.listBuckets();
//            buckets.forEach(e->{
//                log.info(" listBuckets Bucket:{}",e);
//                String name = e.getName();
//                ObjectListing ObjectListing = ossClient.listObjects(name);
//                List<String> commonPrefixes = ObjectListing.getCommonPrefixes();
//                log.info(" listBuckets commonPrefixes:{}",commonPrefixes);
//                List<OSSObjectSummary> objectSummaries = ObjectListing.getObjectSummaries();
//                objectSummaries.forEach(w->{
//                    OSSObject object = ossClient.getObject(name, w.getKey());
//                    log.info(" listBuckets ETag:{},key:{}",w.getETag(),w.getKey());
//
//                });
//            });

            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(ossConfig.getBucketname());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            listObjectsRequest.setPrefix(ossConfig.getFilehost() + "/" + format.format(new Date()) + "/");
            ObjectListing list = ossClient.listObjects(listObjectsRequest);
            for (OSSObjectSummary objectSummary : list.getObjectSummaries()) {
                System.out.println(objectSummary.getKey());
                listRe.add(objectSummary.getKey());
            }
            return listRe;
        } catch (Exception ex) {
            log.info("==========>查询列表失败", ex);
            return new ArrayList<>();
        }


    }
}
