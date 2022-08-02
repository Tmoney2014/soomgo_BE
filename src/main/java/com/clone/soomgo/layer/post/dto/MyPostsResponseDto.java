package com.clone.soomgo.layer.post.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class MyPostsResponseDto {
    List<PostsResponseDto> mypostList;
}
