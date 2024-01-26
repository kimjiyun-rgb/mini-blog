package com.example.miniblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.miniblog.model.Post;
import com.example.miniblog.repository.CommentRepository;
import com.example.miniblog.repository.PostRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/post-delete")
    @CrossOrigin
    public Map<String, Object> postDelete(@RequestBody @NonNull Post post) {
        Map<String, Object> map = new HashMap<>();

        // commentRepository.deleteByPostId(post.getId());

        try {
            postRepository.delete(post);
            map.put("code", 200);
            map.put("msg", "글이 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 201);
            map.put("msg", "처리 중 오류가 발생되었습니다.");
        }

        return map;
    }

    @PostMapping("/post-write")
    @CrossOrigin
    public Map<String, Object> postWrite(@RequestBody Post post) {
        System.out.println(post);
        log.error("post-write", post.toString());

        Map<String, Object> map = new HashMap<>();

        try {
            postRepository.save(post);
            map.put("code", 200);
            map.put("msg", "글 작성이 완료되었습니다.");
        } catch (Exception e) {
            map.put("code", 201);
            map.put("msg", "처리 중 오류가 발생되었습니다.");
        }

        return map;
    }

    @GetMapping("/posts")
    @CrossOrigin
    public Map<String, Object> posts(
            @RequestParam(defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<>();

        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Post> list = postRepository.findAll(pageable);

        map.put("posts", list.getContent());

        int startPage = (page - 1) / 5 * 5 + 1;

        int totalPage = list.getTotalPages();

        int endPage = startPage + 4;
        if (endPage > totalPage) {
            endPage = totalPage;
        }

        map.put("startPage", startPage);
        map.put("endPage", endPage);
        map.put("totalPage", totalPage);

        return map;
    }

    @GetMapping("/post/{postId}")
    @CrossOrigin
    public Post post(@PathVariable Long postId) {
        Optional<Post> opt = postRepository.findById(postId);
        Post post = opt.get();
        return post;
    }
}
