package com.clone.soomgo.layer.post.controller;


import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.post.dto.PostRequestDto;
import com.clone.soomgo.layer.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/api/posts")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        ResponseEntity<?> responseEntity = postService.CreatePost(postRequestDto,userDetails);

        return responseEntity;

    }

    @GetMapping("/api/posts")
    public ResponseEntity<?> getPosts(@RequestParam String subject, @RequestParam int page, @RequestParam int size){

        ResponseEntity<?> responseEntity = postService.getPosts(subject,page,size);

        return responseEntity;

    }





}
