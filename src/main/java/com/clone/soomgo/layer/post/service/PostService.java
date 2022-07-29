package com.clone.soomgo.layer.post.service;

import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.post.dto.ImgUrlRequestDto;
import com.clone.soomgo.layer.post.dto.PostRequestDto;
import com.clone.soomgo.layer.post.dto.TagRequestDto;
import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.post.repository.PostRepository;
import com.clone.soomgo.layer.post.repository.PostRepositoryImpl;
import com.clone.soomgo.layer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostRepositoryImpl postRepositoryImpl;
    public ResponseEntity<?> CreatePost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        Post post = new Post(postRequestDto,userDetails);

        postRepository.save(post);

        return new ResponseEntity<>("성공적으로 글이 작성되었습니다.",HttpStatus.valueOf(200));


    }


    public ResponseEntity<?> getPosts(String subject, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);


        return new ResponseEntity<>("성공적으로 글이 작성되었습니다.",HttpStatus.valueOf(200));

    }
}
