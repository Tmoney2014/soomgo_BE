package com.clone.soomgo.layer.comment.repository;


import com.clone.soomgo.layer.comment.model.Comment;

import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByUserAndPost(User user, Post post);
}
