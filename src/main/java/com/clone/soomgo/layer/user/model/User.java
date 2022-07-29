package com.clone.soomgo.layer.user.model;

import com.clone.soomgo.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class User  extends TimeStamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String mobile;

    @Column
    private boolean gosu;

    @Column
    private String profileurl;




    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gosu = false;
    }
}

