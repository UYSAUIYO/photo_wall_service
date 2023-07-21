package com.example.photo_wall_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import static com.example.photo_wall_service.config.UploadConfig.STORAGE_ADDRESS;

/**
 * 静态资源配置
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 静态资源映射
        registry.addResourceHandler("/img/**")     // 映射路径, 其中的img可以随便改
                .addResourceLocations("file:" + STORAGE_ADDRESS); // 服务器中存放图片的路径
    }

}