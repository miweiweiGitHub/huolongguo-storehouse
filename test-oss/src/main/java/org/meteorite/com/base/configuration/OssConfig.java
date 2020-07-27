package org.meteorite.com.base.configuration;

import com.aliyun.oss.OSSClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class OssConfig {

    private  String endpoint;
    //填写刚刚生成的AccessKey
    private  String keyid;
    //填写刚刚生成的Accesssecret
    private  String keysecret;
    //bucket名称
    private  String bucketname;
    //bucket下文件夹的路径
    private  String filehost;
    //文件访问路径
    private  String viewurl;


    public OSSClient getOssClient() {
        return new OSSClient(this.endpoint, this.keyid, this.keysecret);
    }

}
