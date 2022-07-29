package com.clone.soomgo.layer.post;


import com.clone.soomgo.TimeStamped;
import com.clone.soomgo.layer.comment.Comment;
import com.clone.soomgo.layer.likes.model.Likes;
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


    @ElementCollection
    @CollectionTable(name ="TAG_LIST",joinColumns = @JoinColumn(name ="POST_ID"))
    private List<String> tagList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name ="IMAGEURL_LIST",joinColumns = @JoinColumn(name ="POST_ID"))
    private List<String> imgurlList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Likes> likeList;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> commentList;


    @Column(nullable = false)
    private int viewCount;





}
