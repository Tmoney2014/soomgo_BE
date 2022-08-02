package com.clone.soomgo.layer.post.dto;


import com.clone.soomgo.layer.post.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class ViewCountPostResponseDto {
    private List<ViewCountPostDto> postList;
}
