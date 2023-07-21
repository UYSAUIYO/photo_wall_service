package com.example.photo_wall_service.controller;

import com.example.photo_wall_service.domain.Comment;
import com.example.photo_wall_service.result.GlobalResult;
import com.example.photo_wall_service.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/commentDO")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 评论发布
     *
     * @param comment
     * @return
     */
    @PostMapping("/publishComment")
    public GlobalResult publishComment (@RequestBody Comment comment) throws IOException {
        String content = comment.getContent ();
        String email = comment.getEmail ();
        String nickname = comment.getNickname ();
        String photoId = comment.getPhotoId ();
        return commentService.publishComment (content, email, nickname, photoId);
    }


    /**
     * 根据id查评论
     *
     * @param photoId
     * @return
     */
    @GetMapping("/photoCommentList")
    public GlobalResult photoCommentList (@RequestParam String photoId, @RequestParam Integer pageSize, @RequestParam Integer pageNum) {
        return commentService.photoCommentList (photoId, pageSize, pageNum);
    }

    /**
     * 删除单条评论
     *
     * @param uuid
     * @return
     */
    @DeleteMapping("/deleteComment")
    public GlobalResult deleteComment (@RequestParam String uuid) {
        return commentService.deleteComment (uuid);
    }

    /**
     * 删除照片墙下全部评论
     *
     * @param photoId
     * @return
     */
    @DeleteMapping("/deletePhotoComment")
    public GlobalResult deletePhotoComment (@RequestParam String photoId) {
        return commentService.deletePhotoComment (photoId);
    }
}
