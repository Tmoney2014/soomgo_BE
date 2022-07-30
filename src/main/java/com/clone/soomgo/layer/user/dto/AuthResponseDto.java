package com.clone.soomgo.layer.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {

    String username;
    String profileURL;
    String mobile;
    String email;
    boolean gosu;
}



