package com.clone.soomgo.IntegrationTest;

import com.clone.soomgo.config.security.UserDetailsImpl;
import com.clone.soomgo.config.security.jwt.JwtTokenUtils;
import com.clone.soomgo.layer.ImgUrl.dto.ImgUrlDto;
import com.clone.soomgo.layer.likes.repository.LikesRepository;
import com.clone.soomgo.layer.post.dto.TagDto;
import com.clone.soomgo.layer.post.model.SubjectEnum;
import com.clone.soomgo.layer.post.repository.PostRepository;
import com.clone.soomgo.layer.user.model.User;
import com.clone.soomgo.layer.user.repository.UserRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookmarkTest {


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

    @Autowired
    private LikesRepository likesRepository;


    private String token;

    @BeforeAll
    public void registerUser() {
        User user = new User("username", "email", passwordEncoder.encode("password"));
        userRepository.save(user);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        token = JwtTokenUtils.generateJwtToken(userDetails);
    }

    @BeforeEach
    public void setup() {

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "BEARER" + " " + token);
    }


    @Test
    @Order(1)
    @DisplayName("북마크 등록")
    void test1() {
        //given
        long postId = 1L;
        HttpEntity<?> request = new HttpEntity<>(postId, headers);

        //when
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/bookmark/" + postId,
                HttpMethod.POST,
                request,
                String.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String responseBody = response.getBody();
        assertEquals("북마크를 등록했습니다.", responseBody);
    }

    @Test
    @Order(2)
    @DisplayName("북마크 삭제")
    void test2() {
        //given
        long postId = 1L;
        HttpEntity<?> request = new HttpEntity<>(postId, headers);

        //when
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/bookmark/" + postId,
                HttpMethod.DELETE,
                request,
                String.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String responseBody = response.getBody();
        assertEquals("북마크가 삭제되었습니다.", responseBody);
    }
}


