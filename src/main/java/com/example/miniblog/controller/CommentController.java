package com.example.miniblog.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.miniblog.model.Comment;
import com.example.miniblog.model.Post;
import com.example.miniblog.repository.CommentRepository;

@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/comment-write")
    @CrossOrigin
    public Map<String, Object> commentWrite(
            @RequestBody Comment comment,
            @RequestParam Long postId) {
        Post post = new Post();
        post.setId(postId);
        comment.setPost(post);

        Map<String, Object> map = new HashMap<>();

        try {
            commentRepository.save(comment);
            map.put("code", 200);
            map.put("msg", "댓글 작성이 완료되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 201);
            map.put("msg", "처리 중 오류가 발생되었습니다.");
        }

        return map;
    }
}
