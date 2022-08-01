package com.clone.soomgo.layer.ImgUrl.model;

import com.clone.soomgo.TimeStamped;
import com.clone.soomgo.layer.ImgUrl.dto.ImgUrlDto;
import com.clone.soomgo.layer.post.model.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class ImgUrl extends TimeStamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    @JsonBackReference
    private Post post;

    @Column(nullable = false)
    private String imgUrl;


    public ImgUrl(Post post, ImgUrlDto imgUrlDto){
        this.post = post;
        this.imgUrl = imgUrlDto.getImgurl();
    }
}
