package com.example.photo_wall_service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photo_wall_service.domain.Comment;
import com.example.photo_wall_service.result.GlobalResult;

import java.io.IOException;

public interface CommentService extends IService<Comment> {
    GlobalResult publishComment(String content, String email, String nickname, String photoId) throws IOException;

    GlobalResult photoCommentList(String photoId, Integer pageSize, Integer pageNum);

    GlobalResult deleteComment(String uuid);

    GlobalResult deletePhotoComment(String photoId);

}
