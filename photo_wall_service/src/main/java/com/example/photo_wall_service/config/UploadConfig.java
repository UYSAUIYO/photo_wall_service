package com.example.photo_wall_service.config;

import org.springframework.context.annotation.Configuration;

//图片上传相关配置
@Configuration
public class UploadConfig {
    //图片映射路径
    public static String SERVICE_HEAD = "http://localhost:8080";
    //图片存储路径
    //程序运行所在的目录
    public static String STORAGE_ADDRESS = System.getProperty ("user.dir") + "\\images\\";


    //压缩图片的长宽
    public static int WIDTH = 800;
    public static int HIGHT = 600;

}
