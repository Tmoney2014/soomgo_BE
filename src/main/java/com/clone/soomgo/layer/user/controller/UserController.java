package com.clone.soomgo.layer.user.controller;

import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.user.dto.AuthResponseDto;
import com.clone.soomgo.layer.user.dto.SignupRequestDto;
import com.clone.soomgo.layer.user.model.User;
import com.clone.soomgo.layer.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/api/signup")
    public ResponseEntity<?>  registerUser(@RequestBody SignupRequestDto signupRequestDto){
        ResponseEntity<?>  responseEntity = userService.registerUser(signupRequestDto);

        return responseEntity;

    }

    @GetMapping("/api/auth")
    public ResponseEntity<?> getAuth(@AuthenticationPrincipal UserDetailsImpl userDetails){

        ResponseEntity<?> responseEntity = userService.getAuth(userDetails);

        return responseEntity;


    }

    @PutMapping("/api/role")
    public ResponseEntity<?> changeRole(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        return userService.changeRole(user);
    }




}