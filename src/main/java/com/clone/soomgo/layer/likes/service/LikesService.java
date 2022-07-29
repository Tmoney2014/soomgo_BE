package com.clone.soomgo.layer.likes.service;

import com.clone.soomgo.layer.likes.model.Likes;
import com.clone.soomgo.layer.likes.repository.LikesRepository;
import com.clone.soomgo.layer.post.Post;
import com.clone.soomgo.layer.user.model.User;
import com.clone.soomgo.layer.user.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> postLikes(Long userId, Long postId) {

        User user = userRepository.findByuserId(userId);
        Post post = postRepository.findBypostId(postId);
        Likes like = new Likes(user, post);
        likesRepository.save(like);

        return new ResponseEntity<>("좋아요 등록 성공", HttpStatus.valueOf(200));

    }

    public ResponseEntity<?> deleteLikes(Long userId, Long postId) {
        List<Likes> likes = likesRepository.findByuserId(userId);
        for (Likes like : likes) {
            if (like.getPost().getId() == postId) {
                likesRepository.deleteById(like.getId());
            }
        }
        return new ResponseEntity<>("좋아요 삭제 성공", HttpStatus.valueOf(200));

    }

}
