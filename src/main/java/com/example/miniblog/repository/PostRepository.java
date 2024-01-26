package com.example.miniblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.miniblog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
