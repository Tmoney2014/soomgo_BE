package com.clone.soomgo.layer.bookmark.service;

import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.bookmark.dto.BookmarkResponseDto;
import com.clone.soomgo.layer.bookmark.model.Bookmark;
import com.clone.soomgo.layer.bookmark.repository.BookmarkRepository;
import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.post.repository.PostRepository;
import com.clone.soomgo.layer.user.model.User;
import com.clone.soomgo.layer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    

    public ResponseEntity<?> postbookmark(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저가 없습니다"));
        Post post = postRepository.findById(postId).get();
        Bookmark bookmark = new Bookmark(user, post);
        bookmarkRepository.save(bookmark);

        return new ResponseEntity<>("북마크 등록 성공", HttpStatus.valueOf(200));
    }

    public ResponseEntity<?> deletebookmark(UserDetailsImpl userDetails, Long postId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(userDetails.getUser());
        for (Bookmark bookmark : bookmarks) {
            if (bookmark.getPost().getId().equals(postId)) {
                bookmarkRepository.deleteById(bookmark.getId());
            }
        }
        return new ResponseEntity<>("북마크삭제 성공", HttpStatus.valueOf(200));
    }

    public ResponseEntity<?> getbookmark(UserDetailsImpl userDetails) {

        List<Bookmark> bookmarks = bookmarkRepository.findByUser(userDetails.getUser());

        List<BookmarkResponseDto> bookmarkResponseDtoList = new ArrayList<>();

        for(Bookmark bookmark : bookmarks) {
            bookmarkResponseDtoList.add(new BookmarkResponseDto(bookmark.getPost().getTitle(),bookmark.getUser().getUsername(),bookmark.getPost().getContent(),bookmark.getPost().getId()));
        }

        return new ResponseEntity<>(bookmarkResponseDtoList, HttpStatus.valueOf(200));

    }
}
