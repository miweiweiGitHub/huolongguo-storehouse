package org.meteorite.com.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.base.configuration.OssConfig;
import org.meteorite.com.dto.FileDTO;
import org.meteorite.com.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    OssConfig ossConfig;

    @Override
    public FileDTO upLoad(File file) {
        // 判断文件
        if(file==null){
            return null;
        }

        log.info("------OSS文件上传开始--------"+file.getName());
        String endpoint=ossConfig.getEndpoint();
        log.info("获取到的Point为:{}",endpoint);
        String accessKeyId=ossConfig.getKeyid();
        String accessKeySecret=ossConfig.getKeysecret();
        String bucketName=ossConfig.getBucketname();
        String fileHost=ossConfig.getFilehost();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=format.format(LocalDateTime.now());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);

        OSSClient client=new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 判断容器是否存在,不存在就创建
            if (!client.doesBucketExist(bucketName)) {
                client.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                client.createBucket(createBucketRequest);
            }
            // 设置文件路径和名称
            String fileUrl = fileHost + "/" + (dateStr + "/" + uuid ) + "-" + file.getName();
            // 设置权限(公开读)
            client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            // 上传文件
            PutObjectResult result = client.putObject(new PutObjectRequest(bucketName, fileUrl, file));

            if (result != null) {
                log.info("upload result:{}",result);
                log.info("------OSS文件上传成功------" + fileUrl);
                return new FileDTO(
                        file.length(),//文件大小
                        fileUrl,//文件的绝对路径
                        ossConfig.getWebUrl() +"/"+ fileUrl,//文件的web访问地址
                        suffix,//文件后缀
                        "",//存储的bucket
                        bucketName,//原文件名
                        fileHost//存储的文件夹
                );

            }
        }catch (OSSException oe){
            log.error(oe.getMessage());
        }catch (ClientException ce){
            log.error(ce.getErrorMessage());
        }finally{
            if(client!=null){
                client.shutdown();
            }
        }
        return null;
    }
}
