package com.clone.soomgo.layer.post.dto;


import com.clone.soomgo.layer.post.model.SubjectEnum;
import lombok.Getter;

import java.util.List;

@Getter
public class PostRequestDto {

    private SubjectEnum subjectEnum;

    private String title;

    private List<String> tagList;

    private String content;

    private List<String> imgurlList;



}
