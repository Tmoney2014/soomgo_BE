package com.clone.soomgo.layer.user.service;

import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.layer.user.dto.AuthResponseDto;
import com.clone.soomgo.layer.user.dto.UpdateAuthDto;
import com.clone.soomgo.layer.user.dto.RoleResponseDto;
import com.clone.soomgo.layer.user.dto.SignupRequestDto;
import com.clone.soomgo.layer.user.model.User;
import com.clone.soomgo.layer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public ResponseEntity<?> registerUser(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();

       if(userRepository.findByEmail(email).isPresent()){
           return new ResponseEntity<>("이미 존재하는 이메일입니다",HttpStatus.valueOf(400));
       }
       User user = new User(username,email, passwordEncoder.encode(password));

       userRepository.save(user);


       return new ResponseEntity<>("회원가입에 성공했습니다",HttpStatus.valueOf(200));

    }

    public ResponseEntity<?> getAuth(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        AuthResponseDto authResponseDto = new AuthResponseDto(user.getUsername(),user.getProfileurl(), user.getMobile(),user.getEmail(),user.isGosu());



        return new ResponseEntity<>(authResponseDto,HttpStatus.valueOf(200));

    }

    @Transactional
    public ResponseEntity<?> changeRole(User user) {

        user.updateGosu();

        RoleResponseDto roleResponseDto = new RoleResponseDto(user.getEmail(),user.isGosu());

        userRepository.save(user);

        return new ResponseEntity<>(roleResponseDto,HttpStatus.valueOf(200));
    }


   @Transactional
   public ResponseEntity<?> updateAuth(UserDetailsImpl userDetails, UpdateAuthDto updateAuthDto) {
        
       User user = userDetails.getUser();

       user.updateAuth(updateAuthDto);

       userRepository.save(user);

       UpdateAuthDto updateResponseAuthDto = new UpdateAuthDto(user.getUsername(), user.getProfileurl(), user.getMobile());

       return new ResponseEntity<>(updateResponseAuthDto,HttpStatus.valueOf(200));


    }
}
