package com.clone.soomgo.layer.comment.model;

import com.clone.soomgo.TimeStamped;
import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    @JsonBackReference
    private Post post;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean owner;

    public Comment(Post post, User user, String content) {
        this.post = post;
        this.user = user;
        this.content = content;
    }

    public void updateComment(User user , String content) {
        this.user = user;
        this.content = content;
    }
}

