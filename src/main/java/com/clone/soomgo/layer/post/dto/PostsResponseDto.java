package com.clone.soomgo.layer.post.dto;

import com.clone.soomgo.layer.ImgUrl.dto.ImgUrlDto;
import com.clone.soomgo.layer.ImgUrl.model.ImgUrl;
import com.clone.soomgo.layer.post.model.SubjectEnum;
import com.clone.soomgo.layer.tag.dto.TagDto;
import com.clone.soomgo.layer.tag.model.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostsResponseDto {

    private Long postId;

    private SubjectEnum subject;

    private String content;

    private int likeCount;

    private int commentCount;

    private List<TagDto> tagList;

    private List<ImgUrlDto> imgUrlList;

    private LocalDateTime createdAt;



}
