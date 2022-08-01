package com.clone.soomgo.layer.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public class BookmarkResponseDto {
    private String tilte;
    private String username;
    private String content;
    private Long postId;
}



