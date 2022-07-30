package com.clone.soomgo.layer.post.repository;


import com.clone.soomgo.layer.post.dto.PostsResponseDto;
import com.clone.soomgo.layer.post.dto.testResponseDto;
import com.clone.soomgo.layer.post.model.Post;
import com.clone.soomgo.layer.post.model.QPost;
import com.clone.soomgo.layer.post.model.SubjectEnum;
import com.clone.soomgo.layer.tag.dto.TagDto;
import com.clone.soomgo.layer.tag.model.QTag;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.list;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private QPost post = QPost.post;

    private QTag tag = QTag.tag1;

    @Override
    public ResponseEntity<?> getPosts(String subject, Pageable pageable) {

        List<Post> postList = queryFactory
                .selectFrom(post)
                .leftJoin(post.tagList,tag).fetchJoin()
                .fetch();



        return new  ResponseEntity<>(postList.stream().map(p->new testResponseDto(p.getId(),p.getTagList())),HttpStatus.valueOf(200));
    }


    private BooleanExpression subjectContains(String subject){
        return subject.equals("ALL")? null : post.subject.eq(SubjectEnum.valueOf(subject));
    }
}
