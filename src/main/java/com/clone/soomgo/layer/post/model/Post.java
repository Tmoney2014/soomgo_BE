package com.clone.soomgo.layer.post.model;


import com.clone.soomgo.TimeStamped;
import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.comment.Comment;
import com.clone.soomgo.layer.likes.Likes;
import com.clone.soomgo.layer.post.dto.ImgUrlRequestDto;
import com.clone.soomgo.layer.post.dto.PostRequestDto;
import com.clone.soomgo.layer.post.dto.TagRequestDto;
import com.clone.soomgo.layer.user.model.User;
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

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    @Column(nullable = false)
    private int viewCount;


    public Post(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        for(TagRequestDto tagRequestDto: postRequestDto.getTagList()){
            this.tagList.add(tagRequestDto.getTag());
        }

        for(ImgUrlRequestDto imgUrlRequestDto: postRequestDto.getImgurlList()){
            this.imgurlList.add(imgUrlRequestDto.getImgurl());
        }
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.subject = postRequestDto.getSubject();
        this.user = userDetails.getUser();
    }
}
