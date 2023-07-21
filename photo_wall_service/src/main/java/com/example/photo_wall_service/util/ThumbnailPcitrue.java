package com.example.photo_wall_service.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

import static com.example.photo_wall_service.config.UploadConfig.*;

/**
 * 图片压缩工具类
 */
public class ThumbnailPcitrue {
    /**
     * 压缩图片
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String Thumbnail(String url) throws IOException {
        // 获取当前时间戳
        long timestamp = System.currentTimeMillis();

        // 构造压缩后的图片路径
        String compressedImagePath = STORAGE_ADDRESS + "thumbnail" + timestamp + ".jpg";

        // 压缩并重命名图片
        Thumbnails.of(url)
                .size(WIDTH, HIGHT)
                .outputFormat("jpg")
                .toFile(compressedImagePath);

        // 返回压缩后的图片路径
        return "thumbnail" + timestamp + ".jpg";
    }
}
