package com.clone.soomgo.layer.post.service;

import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.ImgUrl.dto.ImgUrlDto;
import com.clone.soomgo.layer.ImgUrl.model.ImgUrl;
import com.clone.soomgo.layer.ImgUrl.repository.ImgUrlRepository;
import com.clone.soomgo.layer.post.dto.PostRequestDto;
import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.post.model.SubjectEnum;
import com.clone.soomgo.layer.post.repository.PostRepository;
import com.clone.soomgo.layer.post.repository.PostRepositoryImpl;
import com.clone.soomgo.layer.tag.dto.TagDto;
import com.clone.soomgo.layer.tag.model.Tag;
import com.clone.soomgo.layer.tag.repository.TagRepository;
import com.clone.soomgo.layer.user.repository.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final TagRepository tagRepository;

    private final ImgUrlRepository imgUrlRepository;

    private final PostRepositoryImpl postRepositoryImpl;
    @Transactional
    public ResponseEntity<?> CreatePost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        Post post = new Post(postRequestDto,userDetails);
        postRepository.save(post);

        List<TagDto> tagDtoList = postRequestDto.getTagList();
        List<ImgUrlDto> imgUrlDtoList = postRequestDto.getImgurlList();

        for(TagDto tagDto:tagDtoList){
            Tag tag = new Tag(post,tagDto);
            tagRepository.save(tag);
        }

        for(ImgUrlDto imgUrlDto:imgUrlDtoList){
            ImgUrl imgUrl = new ImgUrl(post,imgUrlDto);
            imgUrlRepository.save(imgUrl);
        }


        return new ResponseEntity<>("성공적으로 글이 작성되었습니다.",HttpStatus.valueOf(200));


    }


    public ResponseEntity<?> getPosts(String subject, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);


        ResponseEntity<?> responseEntity =postRepositoryImpl.getPosts(subject,pageable);


        return responseEntity;

    }

}
