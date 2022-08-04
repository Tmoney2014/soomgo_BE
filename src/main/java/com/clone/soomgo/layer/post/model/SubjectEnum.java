package com.clone.soomgo.layer.post.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SubjectEnum {
    QNA,
    HOWMUCH,
    FINDGOSU,
    TOGETHER,
    FREE,
    KNOWHOW;



    @JsonCreator
    public static SubjectEnum from(String subject) {
        return SubjectEnum.valueOf(subject.toUpperCase());
    }
}
