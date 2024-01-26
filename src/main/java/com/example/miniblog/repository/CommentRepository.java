package com.example.miniblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.miniblog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(nativeQuery = true, value = "delete from comment where post_id = ?1")
    public void deleteByPostId(Long postId);

}
