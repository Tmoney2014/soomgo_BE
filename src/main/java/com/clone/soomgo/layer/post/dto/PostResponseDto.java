package com.clone.soomgo.layer.post.dto;

import com.clone.soomgo.layer.post.model.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long postId;

    private SubjectEnum subject;

    private String title;

    private String content;

    private String writer;

    private int likeCount;

    private int commentCount;

    private List<String> tagList;

    private List<String> imgUrlList;

    private int viewCount;

    private boolean bookmark;

    private boolean liked;

    private boolean owner;

    private Long createdAt;


}
