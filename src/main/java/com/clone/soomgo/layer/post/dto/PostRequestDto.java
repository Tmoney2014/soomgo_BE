package com.clone.soomgo.layer.post.dto;


import com.clone.soomgo.layer.ImgUrl.dto.ImgUrlDto;
import com.clone.soomgo.layer.post.model.SubjectEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    private SubjectEnum subject;

    private String title;

    private List<TagDto> tagList;

    private String content;

    private List<ImgUrlDto> imgUrlList;



}
