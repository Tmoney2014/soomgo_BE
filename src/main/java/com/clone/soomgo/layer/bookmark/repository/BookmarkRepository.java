package com.clone.soomgo.layer.bookmark.repository;

import com.clone.soomgo.layer.bookmark.model.Bookmark;
import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository <Bookmark , Long> {
    List<Bookmark> findByUser(User user);

    Optional<Bookmark> findByUserAndPost(User user,Post post);
}
