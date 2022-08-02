package com.clone.soomgo.testdata;

import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.post.model.SubjectEnum;
import com.clone.soomgo.layer.post.repository.PostRepository;
import com.clone.soomgo.layer.user.model.User;
import com.clone.soomgo.layer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class TestDataRunner implements ApplicationRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final PostRepository postRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User testUser1 = new User("testnickname","testid@naver.com", passwordEncoder.encode("test123"));

        userRepository.save(testUser1);

        createData(100,testUser1);

    }

    private void createData(int count, User testUser1) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 3;

        for(int i=0; i<count; i++){
            Random random = new Random();

            String title =random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            String content = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            int subjectNumber = random.nextInt(6)+1;
            SubjectEnum subject;


            switch (subjectNumber){
                case 1:
                    subject = SubjectEnum.FINDGOSU;
                    break;
                case 2:
                    subject = SubjectEnum.FREE;
                    break;
                case 3:
                    subject = SubjectEnum.HOWMUCH;
                    break;
                case 4:
                    subject = SubjectEnum.KNOWHOW;
                    break;
                case 5:
                    subject = SubjectEnum.TOGETHER;
                    break;
                default:
                    subject = SubjectEnum.QNA;
                    break;

            }

            User user = testUser1;

            String tags = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            Post post = new Post(title,content,subject,tags,user);

            postRepository.save(post);

        }



    }
}
