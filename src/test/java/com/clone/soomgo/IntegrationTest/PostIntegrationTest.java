package com.clone.soomgo.IntegrationTest;


import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.config.security.jwt.JwtTokenUtils;
import com.clone.soomgo.layer.ImgUrl.dto.ImgUrlDto;
import com.clone.soomgo.layer.post.dto.PostResponseDto;
import com.clone.soomgo.layer.post.dto.TagDto;
import com.clone.soomgo.layer.post.model.SubjectEnum;
import com.clone.soomgo.layer.post.repository.PostRepository;
import com.clone.soomgo.layer.user.model.User;
import com.clone.soomgo.layer.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders headers;

    private ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private String token;

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
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","BEARER"+" "+token);
    }



    @Test
    @Order(1)
    @DisplayName("포스트 등록")
    void test1() throws JsonProcessingException{

        List<TagDto> tagDtoList = new ArrayList<>();

        List<ImgUrlDto> imgUrlDtoList = new ArrayList<>();

        TagDto tagDto = new TagDto("태그");

        ImgUrlDto imgUrlDto = new ImgUrlDto("URL");

        tagDtoList.add(tagDto);
        imgUrlDtoList.add(imgUrlDto);

        PostRequestDto postRequestDto = PostRequestDto.builder()
                .content("내용")
                .title("제목")
                .subject(SubjectEnum.QNA)
                .imgUrlList(imgUrlDtoList)
                .tagList(tagDtoList)
                .build();

        String requestBody = mapper.writeValueAsString(postRequestDto);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/posts",
                request,
                String.class
        );
        System.out.println(response);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("성공적으로 글이 작성되었습니다.",response.getBody());

    }

    @Test
    @Order(2)
    @DisplayName("포스트 전체 들고오기")
    void test2() throws JsonProcessingException{

        String subject = "ALL";
        int page =0;
        int size =2;
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/posts?subject="+subject+"&page="+page+"&size="+size,
                String.class

        );


        assertEquals(HttpStatus.OK,response.getStatusCode());

    }

    @Test
    @Order(3)
    @DisplayName("포스트 상세페이지 들고오기")
    void test3() throws  JsonProcessingException{
        long postId = 101L;

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<PostResponseDto> response = restTemplate.exchange(
                "/api/posts/"+postId,
                HttpMethod.GET,
                request,
                PostResponseDto.class
        );


        PostResponseDto postResponse = response.getBody();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("내용", Objects.requireNonNull(postResponse).getContent());
        assertEquals("제목",postResponse.getTitle());
        assertEquals(101L,postResponse.getPostId());
        assertEquals(SubjectEnum.QNA,postResponse.getSubject());

    }

    @Test
    @Order(4)
    @DisplayName("존재하지 않는 포스트 상세페이지")
    void test4() throws  JsonProcessingException{
        long postId = 200L;

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/posts/"+postId,
                HttpMethod.GET,
                request,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());

    }

    @Setter
    @Getter
    @Builder
    static class PostRequestDto{

        private SubjectEnum subject;

        private String title;

        private List<TagDto> tagList;

        private String content;

        private List<ImgUrlDto> imgUrlList;

    }





}
