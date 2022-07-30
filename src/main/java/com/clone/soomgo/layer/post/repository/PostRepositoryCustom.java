package com.clone.soomgo.layer.post.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PostRepositoryCustom {
    ResponseEntity<?> getPosts(String subject, Pageable pageable);
}
