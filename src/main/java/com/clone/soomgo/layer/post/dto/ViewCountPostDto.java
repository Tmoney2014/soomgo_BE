package com.clone.soomgo.layer.post.dto;

import com.clone.soomgo.layer.post.model.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class ViewCountPostDto {

    private Long postId;

    private SubjectEnum subject;

    private String title;

    private String content;

    private int likeCount;

    private int commentCount;

    private int viewCount;


}
