package com.clone.soomgo.layer.bookmark.repository;

import com.clone.soomgo.layer.bookmark.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository <Bookmark , Long> {
    List<Bookmark> findByuserId(Long userId);
}
