package com.clone.soomgo.layer.likes;

import com.clone.soomgo.TimeStamped;
import com.clone.soomgo.layer.post.Post;
import com.clone.soomgo.layer.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Likes extends TimeStamped {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKES_ID", nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;




}
