package com.clone.soomgo.layer.post.repository;

import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.post.model.SubjectEnum;
import com.clone.soomgo.layer.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from  Post  p where p.subject NOT IN ?1 ORDER BY p.id DESC ")
    Slice<Post> findAllByOrderByIdDesc(SubjectEnum subject,Pageable pageable); //subject가 KNOWHOW인 포스트를 제외한 모든 포스트를 보여주는 페이지네이션 처음 부분
    @Query("select p from  Post  p where p.subject NOT IN ?2 AND p.id<?1 ORDER BY p.id DESC ")
    Slice<Post> findByIdLessThanOrderByIdDesc(Long id,SubjectEnum subjectEnum, Pageable pageable); //subject가 KNOWHOW인 포스트를 제외한 모든 포스트를 보여주는 페이지네이션 후 부분

    Slice<Post> findAllBySubjectOrderByIdDesc(SubjectEnum subject, Pageable pageable); // 카테고리가 subject 포스트를 보여주는 페이지네이션 처음 부분

    Slice<Post> findByIdLessThanAndSubjectOrderByIdDesc(Long id,SubjectEnum subject, Pageable pageable); // 카테고리가 subject 포스트를 보여주는 페이지네이션 후 부분


    Slice<Post> findAllByTitleContainingOrContentContainingOrTagsContainingOrderByIdDesc(String title,String content,String tags,Pageable pageable); // 제목,내용,태그에 하나라도 keyword가 들어간 포스트를 보여주는 페이지네이션 첫 부분


    @Query("select p from Post p where p.id<?1 AND (p.title LIKE %?2% Or p.tags LIKE %?3% Or p.content LIKE %?4%) ORDER BY p.id DESC ")
    Slice<Post> findByIdLessThanAndTitleContainingOrContentContainingOrTagsContainingOrderByIdDesc(Long id,String title,String tags,String content, Pageable pageable);// 제목,내용,태그에 하나라도 keyword가 들어간 포스트를 보여주는 페이지네이션 후 부분


    List<Post>findByUser(User user);





}
