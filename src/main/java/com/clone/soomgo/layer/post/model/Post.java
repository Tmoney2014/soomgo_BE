package com.clone.soomgo.layer.post.model;


import com.clone.soomgo.TimeStamped;
import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.ImgUrl.model.ImgUrl;
import com.clone.soomgo.layer.bookmark.model.Bookmark;
import com.clone.soomgo.layer.comment.model.Comment;

import com.clone.soomgo.layer.likes.model.Likes;
import com.clone.soomgo.layer.post.dto.PostRequestDto;
import com.clone.soomgo.layer.post.dto.TagDto;
import com.clone.soomgo.layer.post.service.PostService;
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


    @Column(nullable = false)
    private String tags;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @JsonManagedReference
    private List<ImgUrl> imgurlList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @JsonManagedReference
    private List<Likes> likeList;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @JsonManagedReference
    private List<Comment> commentList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @JsonManagedReference
    private List<Bookmark> bookmarkList;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    @ElementCollection
    @CollectionTable(name = "VIEWUSER",joinColumns = @JoinColumn(name = "POST_ID"))
    List<Long> viewUserList;


    public Post(PostRequestDto postRequestDto, UserDetailsImpl userDetails,String tags) {

        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.subject = postRequestDto.getSubject();
        this.user = userDetails.getUser();
        this.tags = tags;
    }

    public Post(String title,String content,SubjectEnum subject,String tags,User user){
        this.title = title;
        this.content = content;
        this.subject = subject;
        this.tags = tags;
        this.user =user;
    }



    public void addImgUrl(ImgUrl imgUrl){
        this.imgurlList.add(imgUrl);
    }

    public void addViewUser(Long userId){
        if(this.viewUserList.contains(userId)){
            return;
        }
        viewUserList.add(userId);
    }


    public void update(PostRequestDto postRequestDto) {
        List<TagDto> tagDtoList = postRequestDto.getTagList();
        String tags = PostService.getTags(tagDtoList);

        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.subject = postRequestDto.getSubject();
        this.tags = tags;

    }
}
