package com.example.photo_wall_service.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName comment
 */

@Data
@TableName("comment")
public class Comment implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 评论内容
     */
    private String content;

    /**
     *
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    private String uuid;

    /**
     *
     */
    private Date createTime;

    private String photoId;

    private static final long serialVersionUID = 1L;


}