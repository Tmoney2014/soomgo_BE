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
        subject = subject.toUpperCase();
        switch (subject) {
            case "QNA":
                return SubjectEnum.QNA;
            case "HOWMUCH":
                return SubjectEnum.HOWMUCH;
            case "FINDGOSU":
                return SubjectEnum.FINDGOSU;
            case "TOGETHER":
                return SubjectEnum.TOGETHER;
            case "KNOWHOW":
                return SubjectEnum.KNOWHOW;
            default:
                return SubjectEnum.FREE;
        }
    }
}
