package com.clone.soomgo.layer.post.service;

import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.ImgUrl.dto.ImgUrlDto;
import com.clone.soomgo.layer.ImgUrl.model.ImgUrl;
import com.clone.soomgo.layer.bookmark.model.Bookmark;
import com.clone.soomgo.layer.bookmark.repository.BookmarkRepository;
import com.clone.soomgo.layer.likes.model.Likes;
import com.clone.soomgo.layer.likes.repository.LikesRepository;
import com.clone.soomgo.layer.post.dto.*;
import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.post.model.SubjectEnum;
import com.clone.soomgo.layer.post.repository.PostRepository;
import com.clone.soomgo.layer.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikesRepository likesRepository;

    private final BookmarkRepository bookmarkRepository;


    @Transactional
    public ResponseEntity<?> CreatePost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        List<TagDto> tagDtoList = postRequestDto.getTagList();
        List<ImgUrlDto> imgUrlDtoList = postRequestDto.getImgurlList();
        getTags(tagDtoList);
        String tags;

        tags = getTags(tagDtoList);

        Post post = new Post(postRequestDto,userDetails,tags);
        postRepository.save(post);

        for(ImgUrlDto imgUrlDto:imgUrlDtoList){
            ImgUrl imgUrl = new ImgUrl(post,imgUrlDto);
            post.addImgUrl(imgUrl);
        }

        return new ResponseEntity<>("성공적으로 글이 작성되었습니다.",HttpStatus.valueOf(200));


    }



    public ResponseEntity<?> getPosts(String subject, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<Post> postSlice;
        ResponseEntity<?> responseEntity;

        if(subject.equals("ALL")){
            postSlice = postRepository.findAllByOrderByIdDesc(SubjectEnum.valueOf("KNOWHOW"),pageable);

        }else{
            postSlice = postRepository.findAllBySubjectOrderByIdDesc(SubjectEnum.valueOf(subject),pageable);

        }
        responseEntity = new ResponseEntity<>(SlicePostToSlicePostsResponseDto(postSlice),HttpStatus.valueOf(200));
        return responseEntity;

    }



    public ResponseEntity<?> getPost(Long postId,UserDetailsImpl userDetails) {
       Post post = postRepository.findById(postId).orElseThrow(
               () -> new IllegalArgumentException("존재하지 않는 포스트입니다")
       );
       User user = userDetails.getUser();

       Optional<Likes> likes = likesRepository.findByUserAndPost(user,post);

       Optional<Bookmark> bookmark = bookmarkRepository.findByUserAndPost(user,post);

       boolean isliked = true;

       boolean isbookmark = true;

       if(!likes.isPresent()){
           isliked = false;
       }
       if(!bookmark.isPresent()){
           isbookmark = false;
       }

        post.addViewUser(userDetails.getUser().getId());


        PostResponseDto postResponseDto = postToPostResponseDto(post,isbookmark,isliked);


        return new ResponseEntity<>(postResponseDto,HttpStatus.valueOf(200));

    }

    @Transactional
    public ResponseEntity<?> updatePost(Long postId,PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 포스트입니다")
        );


        if(!post.getUser().getId().equals(user.getId())){
            return new ResponseEntity<>("수정할 권한이 없습니다",HttpStatus.valueOf(401));
        }

        post.getImgurlList().clear();

        post.update(postRequestDto);


        return new ResponseEntity<>("포스트가 성공적으로 수정 되었습니다",HttpStatus.valueOf(200));
    }

    public ResponseEntity<?> deletePost(Long postId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 포스트입니다")
        );

        if(!post.getUser().getId().equals(user.getId())){
            return new ResponseEntity<>("삭제할 권한이 없습니다.",HttpStatus.UNAUTHORIZED);
        }

        postRepository.delete(post);

        return new ResponseEntity<>("정상적으로 삭제 되었습니다",HttpStatus.valueOf(200));
    }

    public ResponseEntity<?> getCursorPosts(Long lastId, int size, String subject) {
        Pageable pageable = PageRequest.ofSize(size);
        Slice<Post> postSlice;
        ResponseEntity<?> responseEntity;


        if(subject.equals("ALL")){ // 카테고리 분류 X
            if(lastId ==null){
                postSlice = postRepository.findAllByOrderByIdDesc(SubjectEnum.valueOf("KNOWHOW"),pageable); //모든 포스트를 보여주는 페이지네이션 처음 부분
            }else{
                postSlice = postRepository.findByIdLessThanOrderByIdDesc(lastId,SubjectEnum.valueOf("KNOWHOW"),pageable); // 모든 포스트를 보여주는 페이지네이션 후 부분
            }

        }else{
            // 카테고리 분류 O
            if(lastId ==null){
                postSlice = postRepository.findAllBySubjectOrderByIdDesc(SubjectEnum.valueOf(subject),pageable); // 카테고리가 subject 포스트를 보여주는 페이지네이션 처음 부분
            }else{
                postSlice = postRepository.findByIdLessThanAndSubjectOrderByIdDesc(lastId,SubjectEnum.valueOf(subject),pageable);// 카테고리가 subject 포스트를 보여주는 페이지네이션 후 부분
            }

        }
        responseEntity = new ResponseEntity<>(SlicePostToSlicePostsResponseDto(postSlice),HttpStatus.valueOf(200));
        return  responseEntity;
    }

    public ResponseEntity<?> getSearchTagPosts(Long lastId, int size, String keyword) {
        Pageable pageable = PageRequest.ofSize(size);
        Slice<Post> postSlice;
        ResponseEntity<?> responseEntity;

        if(lastId ==null){
            postSlice = postRepository.findAllByTitleContainingOrContentContainingOrTagsContainingOrderByIdDesc(keyword,keyword,keyword,pageable);

        }else{
            postSlice = postRepository.findByIdLessThanAndTitleContainingOrContentContainingOrTagsContainingOrderByIdDesc(lastId,keyword,keyword,keyword,pageable);
        }

        responseEntity = new ResponseEntity<>(SlicePostToSlicePostsResponseDto(postSlice),HttpStatus.valueOf(200));
        return  responseEntity;
    }

    public ResponseEntity<?> getSearchTagPosts(UserDetailsImpl userDetails) {
        ResponseEntity<?> responseEntity;

        List<Post> postList = postRepository.findByUser(userDetails.getUser());

        MyPostsResponseDto myPostsResponseDto = MyPostsResponseDto.builder()
                .mypostList(postListToPostsResponseDtoList(postList))
                .build();


        responseEntity = new ResponseEntity<>(myPostsResponseDto,HttpStatus.valueOf(200));
        return  responseEntity;


    }

    public static String getTags(List<TagDto> tagDtoList) {
        List<String> tagContentList = new ArrayList<>();
        String tags = "";

        for(TagDto tagDto:tagDtoList){
            if(tagContentList.contains(tagDto.getTag())){
                throw new IllegalArgumentException("중복된 태그가 존재합니다");
            }
            tags = tags+","+tagDto.getTag();
            tagContentList.add(tagDto.getTag());
        }
        tags = tags.substring(1);

        return tags;
    }



    private List<PostsResponseDto> postListToPostsResponseDtoList(List<Post> postList){
        List<PostsResponseDto> postsResponseDtoList = postList.stream().map(p->
                PostsResponseDto.builder()
                        .postId(p.getId())
                        .subject(p.getSubject())
                        .title(p.getTitle())
                        .content(p.getContent())
                        .createdAt(p.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli())  //millisecond 변환
                        .tagList(Stream.of(p.getTags().split(",")).collect(Collectors.toList())) //
                        .imgUrlList(p.getImgurlList().stream().map(ImgUrl::getImgUrl).collect(Collectors.toList()))
                        .likeCount(p.getLikeList().size())
                        .commentCount(p.getCommentList().size())
                        .build()
                ).collect(Collectors.toList());

        return postsResponseDtoList;
    }


    private Slice<PostsResponseDto> SlicePostToSlicePostsResponseDto(Slice<Post> postSlice){
        Slice<PostsResponseDto> postsResponseDtoSlice = postSlice.map(p->
                PostsResponseDto.builder()
                        .postId(p.getId())
                        .subject(p.getSubject())
                        .title(p.getTitle())
                        .content(p.getContent())
                        .createdAt(p.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli())  //millisecond 변환
                        .tagList(Stream.of(p.getTags().split(",")).collect(Collectors.toList())) //
                        .imgUrlList(p.getImgurlList().stream().map(ImgUrl::getImgUrl).collect(Collectors.toList()))
                        .likeCount(p.getLikeList().size())
                        .commentCount(p.getCommentList().size())
                        .build()
        );
        return postsResponseDtoSlice;
    }
    private PostResponseDto postToPostResponseDto(Post post,Boolean bookmark,Boolean isLiked){

        PostResponseDto postResponseDto =  PostResponseDto.builder()
                .postId(post.getId())
                .bookmark(bookmark)
                .commentCount(post.getCommentList().size())
                .content(post.getContent())
                .liked(isLiked)
                .writer(post.getUser().getUsername())
                .createdAt(toLocalDateTimeMilliSecond(post.getCreatedAt()))
                .imgUrlList(post.getImgurlList().stream().map(ImgUrl::getImgUrl).collect(Collectors.toList()))
                .likeCount(post.getLikeList().size())
                .viewCount(post.getViewUserList().size())
                .subject(post.getSubject())
                .tagList(Stream.of(post.getTags().split(",")).collect(Collectors.toList()))
                .title(post.getTitle())
                .build();

        return postResponseDto;
    }


    public Long toLocalDateTimeMilliSecond(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
    }


}
