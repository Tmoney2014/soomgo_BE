package com.clone.soomgo.layer.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
//앞단에 북마크 표시하기위해 필요한 DTO
public class BookmarkResponseDto {
    private String title;
    private String username;
    private String content;
    private Long postId;
}



