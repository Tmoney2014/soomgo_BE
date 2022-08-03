package com.clone.soomgo.layer.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {

    String token;

    String username;

    boolean gosu;
}
