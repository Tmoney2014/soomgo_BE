package com.clone.soomgo.layer.bookmark.controller;

import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.bookmark.service.BookmarkService;
import com.clone.soomgo.layer.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;

@RequiredArgsConstructor
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;


    @PostMapping("/api/bookmark/{postId}")
    public ResponseEntity<?> postbookmark(@AuthenticationPrincipal UserDetailsImpl userDetails , @PathVariable Long postId) {
        Long userId = userDetails.getUser().getId();
        return bookmarkService.postbookmark(userId,postId);
    }


    @DeleteMapping("/api/bookmark/{postId}")
    public ResponseEntity<?> deletetbookmark(@AuthenticationPrincipal UserDetailsImpl userDetails , @PathVariable Long postId)  {
        Long userId = userDetails.getUser().getId();
        return bookmarkService.deletebookmark(userId,postId);
    }

    @GetMapping("/api/mybookmark")
    public ResponseEntity<?> getbookmark(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUser().getId();
        return bookmarkService.getbookmark(userId);
    }

}

