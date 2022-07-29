package com.clone.soomgo.layer.post;


import com.clone.soomgo.TimeStamped;
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


    @ElementCollection
    @CollectionTable(name ="tag_list",joinColumns = @JoinColumn(name ="post_id"))
    private List<String> tagList = new ArrayList<>();





}
