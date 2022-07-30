package com.clone.soomgo.layer.post.dto;


import com.clone.soomgo.layer.tag.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class testResponseDto {

    private Long id;

    private List<Tag> tagList = new ArrayList<>();

}
