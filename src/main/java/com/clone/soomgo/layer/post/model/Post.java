package com.clone.soomgo.layer.post.model;


import com.clone.soomgo.TimeStamped;
import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.ImgUrl.model.ImgUrl;
import com.clone.soomgo.layer.comment.model.Comment;

import com.clone.soomgo.layer.likes.model.Likes;
import com.clone.soomgo.layer.post.dto.PostRequestDto;
import com.clone.soomgo.layer.tag.model.Tag;
import com.clone.soomgo.layer.user.model.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Post extends TimeStamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SubjectEnum subject;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @JsonManagedReference
    private List<Tag> tagList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @JsonManagedReference
    private List<ImgUrl> imgurlList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @JsonManagedReference
    private List<Likes> likeList;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @JsonManagedReference
    private List<Comment> commentList;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    @Column(nullable = false)
    private int viewCount;


    public Post(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.subject = postRequestDto.getSubject();
        this.user = userDetails.getUser();
    }
}
