package com.clone.soomgo.layer.likes.service;

import com.clone.soomgo.layer.likes.model.Likes;
import com.clone.soomgo.layer.likes.repository.LikesRepository;

import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.post.repository.PostRepository;
import com.clone.soomgo.layer.user.model.User;
import com.clone.soomgo.layer.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //좋아요 버튼 클릭시 좋아요 저장
    public ResponseEntity<?> postLikes(Long userId, Long postId) {
        //USERID 와 POSTID 아이디로 USER 와 POST 를 찾아서 저장
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("포스트가 없습니다"));
        Optional<Likes> Likefound = likesRepository.findByUserAndPost(user,post);
        //이미 있는 USER와 POST 가 들어오면 오류 메시지 전달
        if(Likefound.isPresent()){
            return new ResponseEntity<>("좋아요를 이미 하신 게시글입니다",HttpStatus.valueOf(400));
        }
        //새로운 Likes 생성후 USER 와 POST 넣고 저장
        Likes like = new Likes(user, post);
        likesRepository.save(like);

        return new ResponseEntity<>("좋아요 등록 성공", HttpStatus.valueOf(200));

    }

    //좋아요한 개시글의 좋아요 버튼 다시 누를시 좋아요 삭제
    public ResponseEntity<?> deleteLikes(Long userId, Long postId) {
        // USERID 로 좋아요 한 개시물들을 리스트에 담아서
        List<Likes> likes = likesRepository.findByuserId(userId);
        //for문으로 앞단에서 받아온 POSTID 와 같은 좋아요 삭제
        for (Likes like : likes) {
            if (like.getPost().getId().equals(postId)) {
                likesRepository.deleteById(like.getId());
            }
        }
        return new ResponseEntity<>("좋아요 삭제 성공", HttpStatus.valueOf(200));

    }

}
