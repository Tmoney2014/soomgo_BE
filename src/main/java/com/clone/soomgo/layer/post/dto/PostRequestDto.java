package com.clone.soomgo.layer.post.dto;


import com.clone.soomgo.layer.post.model.SubjectEnum;
import lombok.Getter;

import java.util.List;

@Getter
public class PostRequestDto {

    private SubjectEnum subject;

    private String title;

    private List<TagRequestDto> tagList;

    private String content;

    private List<ImgUrlRequestDto> imgurlList;



}
