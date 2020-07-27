package org.meteorite.com.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.dto.FileDTO;
import org.meteorite.com.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/oss/object/")
public class OssObjectController {

    @Autowired
    FileService fileService;


    /**
     * object上传
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadFile")
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

    //    查看、罗列、删除、批量删除Object

    /**
     * 查看
     * @return
     */
    @GetMapping("/catObject")
    @ResponseBody
    public List<String> catObject() {
        List<String> objectList = fileService.getObjectList();
        return objectList;

    }

    /**
     * 罗列
     * @return
     */
    @GetMapping("/listObject")
    @ResponseBody
    public List<String> listObject() {
        List<String> objectList = fileService.getObjectList();
        return objectList;

    }

    /**
     * 删除
     * @return
     */
    @GetMapping("/removeObject")
    @ResponseBody
    public List<String> removeObject() {
        List<String> objectList = fileService.getObjectList();
        return objectList;

    }

    /**
     * 批量删除
     * @return
     */
    @GetMapping("/removeListObject")
    @ResponseBody
    public List<String> removeListObject() {
        List<String> objectList = fileService.getObjectList();
        return objectList;

    }



}
