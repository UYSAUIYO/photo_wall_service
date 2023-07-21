package com.example.photo_wall_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photo_wall_service.domain.Comment;
import com.example.photo_wall_service.mapper.CommentMapper;
import com.example.photo_wall_service.result.GlobalResult;
import com.example.photo_wall_service.service.CommentService;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.photo_wall_service.util.SensitiveWordUtil.*;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {
    public static final String SENSITIVE = "static/SensitiveWordList.txt";
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public GlobalResult publishComment (String content, String email, String nickname, String photoId) throws IOException {
        String validPattern = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Matcher matcher = Pattern.compile (validPattern).matcher (email);
        if (!matcher.find ()) {
            return GlobalResult.errorMsg ("邮箱格式不正确");
        }
        //敏感词汇过滤
        String path = Objects.requireNonNull (Thread.currentThread ().getContextClassLoader ().getResource (SENSITIVE)).getPath ();
        FileInputStream fis = new FileInputStream (path);
        InputStreamReader isr = new InputStreamReader (fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader (isr);
        Set set = new HashSet ();
        String tempStr;
        while ((tempStr = br.readLine ()) != null) {
            set.add (tempStr);
        }
        initSensitiveWordMap (set);
        String newContent = replaceSensitiveWord (content, MinMatchTYpe);
        String newNickname = replaceSensitiveWord (nickname, MinMatchTYpe);

        UUID uuid = Generators.timeBasedGenerator ().generate ();//uuid

        Comment comment = new Comment ();
        comment.setContent (newContent);
        comment.setEmail (email);
        comment.setNickname (newNickname);
        comment.setPhotoId (photoId);
        comment.setUuid (String.valueOf (uuid));
        if (this.baseMapper.insert (comment) == 0) {
            return GlobalResult.errorMsg ("新增失败");
        }
        return GlobalResult.ok ("新增成功");
    }

    @Override
    public GlobalResult photoCommentList (String photoId, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<> ();
        lambdaQueryWrapper.eq (Comment::getPhotoId, photoId);
        Page<Comment> page = new Page<> (pageSize, pageNum);//分页查询
        page = this.baseMapper.selectPage (page, lambdaQueryWrapper);

        return GlobalResult.ok (page, "成功");
    }

    @Override
    public GlobalResult deleteComment (String uuid) {
        if (commentMapper.deleteByUuid (uuid)) {
            return GlobalResult.ok ("删除成功");
        }
        return GlobalResult.errorMsg ("删除失败");
    }

    @Override
    public GlobalResult deletePhotoComment (String photoId) {
        if (commentMapper.deleteByPhotoId (photoId)) {
            return GlobalResult.ok ("删除成功");
        }
        return GlobalResult.errorMsg ("删除失败");
    }

}
