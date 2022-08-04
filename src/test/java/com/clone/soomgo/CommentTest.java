package com.clone.soomgo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.config.security.jwt.JwtTokenUtils;
import com.clone.soomgo.layer.ImgUrl.dto.ImgUrlDto;
import com.clone.soomgo.layer.comment.dto.response.CommentResponseDto;
import com.clone.soomgo.layer.comment.model.Comment;
import com.clone.soomgo.layer.comment.repository.CommentRepository;
import com.clone.soomgo.layer.post.dto.PostResponseDto;
import com.clone.soomgo.layer.post.dto.TagDto;
import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.post.model.SubjectEnum;
import com.clone.soomgo.layer.user.dto.SignupRequestDto;
import com.clone.soomgo.layer.user.model.User;
import com.clone.soomgo.layer.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static com.clone.soomgo.layer.post.model.SubjectEnum.QNA;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    private HttpHeaders headers;

    private ObjectMapper mapper = new ObjectMapper();

    private CommentDto registerComment;

    private String token;

    private PostDto post = PostDto.builder()
            .id(1L)
            .subject(QNA)
            .title("제목")
            .content("내용")
            .build();

    @BeforeAll
    public void registerUser(){
        User user = new User("username","email", passwordEncoder.encode("password"));
        userRepository.save(user);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        token = JwtTokenUtils.generateJwtToken(userDetails);
    }


    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();

        // 토큰 주입
//        String token = JWT.create()
//                .withIssuer("sparta")
//                .withClaim(CLAIM_USER_NAME, "dfwf@naver.com") // username이 필요
//                // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
//                .withClaim(CLAIM_EXPIRED_DATE, new Date(System.currentTimeMillis() + JWT_TOKEN_VALID_MILLI_SEC))
//                .sign(generateAlgorithm());

        headers.add(HttpHeaders.AUTHORIZATION, "BEARER " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    }


    /*
    * 1. DTO 매핑
    * */

    @Test
    @Order(1)
    @DisplayName("comment 생성")
    void test1() {
        CommentDto commentDto = CommentDto.builder().content("test comment").build();

        HttpEntity<CommentDto> request = new HttpEntity<>(commentDto, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/comments/"+post.id,
                request,
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String responseBody = response.getBody();
        assertEquals("성공적으로 댓글이 작성되었습니다.", responseBody);
    }

    @Test
    @Order(2)
    @DisplayName("comment 조회")
    void test2() {
        long postId = 1L;

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<CommentResponseDto> response = restTemplate.exchange(
                "/api/comments/"+postId,
                    HttpMethod.GET,
                    request,
                    CommentResponseDto.class
        );

        CommentResponseDto responseDto = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());


    }

    @Test
    @Order(3)
    @DisplayName("comment 삭제")
    void test3() {

        CommentDto commentDto = CommentDto
                .builder()
                .content("test comment")
                .build();

       long commentId = 1L;

       HttpEntity<CommentDto> request = new HttpEntity<>(commentDto,headers);

       ResponseEntity<String> response = restTemplate.exchange(
                "/api/comments/"+commentId,
                HttpMethod.DELETE,
                request,
                String.class
       );

        assertEquals(HttpStatus.OK,response.getStatusCode());


    }

    @Test
    @Order(4)
    @DisplayName("존재 하지 않는 post에 댓글 작성")
    void test4() {
        long postId = 200L;

        CommentDto commentDto = CommentDto.builder().content("test comment").build();

        HttpEntity<CommentDto> request = new HttpEntity<>(commentDto, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/comments/"+postId,
                request,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Getter
    @Setter @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class CommentDto {
        private String content;
    }

    @Getter
    @Setter @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class PostDto {
        private Long id;
        private SubjectEnum subject;
        private String title;
        private List<TagDto> tagList;
        private String content;
        private List<ImgUrlDto> imgurlList;
    }
}
