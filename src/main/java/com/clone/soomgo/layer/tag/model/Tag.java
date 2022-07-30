package com.clone.soomgo.layer.tag.model;


import com.clone.soomgo.TimeStamped;
import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.tag.dto.TagDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Tag  extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    @JsonBackReference
    private Post post;

    @Column(nullable = false)
    private String tag;

    public Tag(Post post, TagDto tagDto){
        this.post =post;
        this.tag = tagDto.getTag();
    }

}
