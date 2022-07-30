package com.clone.soomgo.layer.likes.repository;

import com.clone.soomgo.layer.likes.model.Likes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes,Long> {
    List<Likes> findByuserId(Long userId);
}
