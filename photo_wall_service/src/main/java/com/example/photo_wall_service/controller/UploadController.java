package com.example.photo_wall_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.photo_wall_service.config.UploadConfig.SERVICE_HEAD;
import static com.example.photo_wall_service.config.UploadConfig.STORAGE_ADDRESS;

@RestController
@RequestMapping("/upload")
public class UploadController {
    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadPhoto")
    public String uploadPhotos(MultipartFile file) {

        // 获取上传的文件名
        String fileName = file.getOriginalFilename();

        // 获得文件后缀名例如.png
        String lastName = fileName.substring(fileName.lastIndexOf("."));

        // 生成新的文件名
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String newFileName = date + lastName;

        // 将文件路径与新的文件名拼接起来, 这是在静态资源中配置的路径
        File newPath = new File(STORAGE_ADDRESS, newFileName);

        // 判断文件夹是否存在若不存在则逐级创建
        if (!newPath.exists()) {
            newPath.mkdirs();
        }

        try {
            file.transferTo(newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = newFileName;

        return path;
    }

    /**
     * 头像上传
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadHead")
    public String uploadHead(MultipartFile file) {

        String fileName = file.getOriginalFilename();

        String lastName = fileName.substring(fileName.lastIndexOf("."));

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String newFileName = "head" + date + lastName;

        File newPath = new File(STORAGE_ADDRESS, newFileName);

        if (!newPath.exists()) {
            newPath.mkdirs();
        }

        try {
            file.transferTo(newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = SERVICE_HEAD + "/img/" + newFileName;

        return path;
    }
}
