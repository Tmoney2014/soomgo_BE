package com.clone.soomgo.layer.likes.controller;

import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
public class LikesController {

    private final LikesService likesService;


    @PostMapping("/api/likes/{postid}")
    public ResponseEntity<?> postLikes(@AuthenticationPrincipal UserDetailsImpl userDetails , Long postId) {
        Long userId = userDetails.getUser().getId();
        return likesService.postLikes(userId,postId);
    }


    @DeleteMapping("/api/likes/{postid}")
    public ResponseEntity<?> deletetLikes(@AuthenticationPrincipal UserDetailsImpl userDetails , Long postId)  {
        Long userId = userDetails.getUser().getId();
        return likesService.deleteLikes(userId,postId);
    }
}
