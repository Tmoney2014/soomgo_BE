package com.clone.soomgo.layer.post.controller;


import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.post.dto.PostRequestDto;
import com.clone.soomgo.layer.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/api/posts")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto postDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        ResponseEntity<?> responseEntity = postService.CreatePost(postDto,userDetails);

        return responseEntity;

    }

    @GetMapping("/api/posts")
    public ResponseEntity<?> getPosts(@RequestParam String subject, @RequestParam int page, @RequestParam int size){

        ResponseEntity<?> responseEntity = postService.getPosts(subject,page,size);

        return responseEntity;

    }

    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        ResponseEntity<?> responseEntity = postService.getPost(postId,userDetails);

        return responseEntity;
    }

    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId,@RequestBody PostRequestDto postRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){

        ResponseEntity<?> responseEntity = postService.updatePost(postId,postRequestDto,userDetails);

        return responseEntity;

    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        ResponseEntity<?> responseEntity = postService.deletePost(postId,userDetails);

        return responseEntity;
    }

    @GetMapping("/api/posts/cursor")
    public ResponseEntity<?> getCursorPosts(@RequestParam(required = false)  Long lastId, @RequestParam int size, @RequestParam String subject){

        ResponseEntity<?> responseEntity = postService.getCursorPosts(lastId,size,subject);

        return responseEntity;

    }

    @GetMapping("/api/posts/search")
    public ResponseEntity<?> getSearchTagPosts(@RequestParam(required = false) Long lastId, @RequestParam int size, @RequestParam String keyword){

        ResponseEntity<?> responseEntity = postService.getSearchTagPosts(lastId,size,keyword);

        return responseEntity;

    }

    @GetMapping("/api/myposts")
    public  ResponseEntity<?> getMyPosts(@AuthenticationPrincipal UserDetailsImpl userDetails){

        ResponseEntity<?> responseEntity = postService.getSearchTagPosts(userDetails);

        return responseEntity;
    }







}
