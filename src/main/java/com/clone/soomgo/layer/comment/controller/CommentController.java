package com.clone.soomgo.layer.comment.controller;

import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.comment.dto.request.CommentRequestDto;
import com.clone.soomgo.layer.comment.service.CommentService;
import com.clone.soomgo.layer.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/comments/{postId}")
    public ResponseEntity<?> showPostComment(@PathVariable Long postId) {
        return commentService.showPostComment(postId);
    }

    @PostMapping("/api/comments/{postId}")
    public ResponseEntity<?> registerComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return commentService.registerComment(postId,commentRequestDto,user);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return commentService.deleteComment(commentId,user);
    }
}
