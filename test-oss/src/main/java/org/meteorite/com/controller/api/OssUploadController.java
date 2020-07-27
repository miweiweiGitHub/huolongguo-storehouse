package org.meteorite.com.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.dto.FileDTO;
import org.meteorite.com.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;


@Slf4j
@RestController
@RequestMapping("/oss/upload/")
public class OssUploadController {


//
//    n  对于大文件支持分片上传（Multi-Part Upload）

//
//    n  访问时支持If-Modified-Since和If-Match等HTTP参数

    @Autowired
    FileService fileService;

    /** 文件上传*/
    @PostMapping(value = "/file")
    public FileDTO uploadBlog(MultipartFile file) {
        log.info("文件上传");
        String filename = file.getOriginalFilename();
        System.out.println(filename);

        try {
            // 判断文件
            if (file!=null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS
                    return fileService.upLoad(newFile);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }






}
