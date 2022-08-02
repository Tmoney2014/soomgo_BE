package com.clone.soomgo.layer.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {

    private String username;

    private String profileURL;

    private String mobile;

    private String email;

    private boolean gosu;


}



