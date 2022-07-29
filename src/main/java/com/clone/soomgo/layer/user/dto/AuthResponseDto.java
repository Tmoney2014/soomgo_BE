package com.clone.soomgo.layer.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDto {

    String username;
    String profileURL;
    String mobile;
    String email;
    boolean gosu;
}



