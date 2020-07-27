package org.meteorite.com.controller.api;

import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.meteorite.com.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/oss/bucket/")
public class OssBucketController {

    @Autowired
    BucketService bucketService;

    /**
     * 创建
     */
    @PostMapping(value = "/create")
    public Bucket createBucket(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        return bucketService.creat(name);
    }

    /**
     * 查看
     */
    @PostMapping(value = "/cat")
    public Bucket catBucket(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        return bucketService.catBucket(name);
    }

    /**
     * 罗列
     */
    @PostMapping(value = "/list")
    public List<Bucket> listBucket() {
        return bucketService.listBucket();
    }

    /**
     * 删除
     */
    @PostMapping(value = "/remove")
    public void removeBucket(String name) {
        bucketService.removeBucket(name);
    }


    /**
     * 修改Bucket的访问权限
     */
    @GetMapping("/changeAuthority")
    @ResponseBody
    public void changeAuthority(String name,int type) {

        switch (type) {
            case 1:
                bucketService.setBucketAcl(name,CannedAccessControlList.PublicRead);
                break;
            case 2:
                bucketService.setBucketAcl(name,CannedAccessControlList.PublicReadWrite);
                break;
        }

    }


}
