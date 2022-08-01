package com.clone.soomgo.layer.likes.repository;

import com.clone.soomgo.layer.likes.model.Likes;
import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes,Long> {
    List<Likes> findByuserId(Long userId);

    Optional<Likes> findByUserAndPost(User user, Post post);
}
