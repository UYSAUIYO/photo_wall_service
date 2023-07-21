package com.example.photo_wall_service.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName photo_wall
 */
@Data
@TableName("photo_wall")
public class PhotoWall implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 世界 "0-主世界、1-地狱、2-末地"
     */
    private Integer world;

    /**
     * 坐标
     */
    private String coordinate;

    /**
     * 作者
     */
    private String author;

    /**
     * 详情
     */
    private String detail;

    /**
     * 图片地址
     */
    private Object photoUrl;

    /**
     * 作者头像
     */
    private String authorPicture;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 略缩图
     */
    private String thumbnail;

    private String uuid;

    private static final long serialVersionUID = 1L;


}