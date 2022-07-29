package com.clone.soomgo.layer.post;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SubjectEnum {
    QNA,
    HOWMUCH,
    FINDGOSU,
    TOGETHER,
    FREE;



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
            default:
                return SubjectEnum.FREE;
        }
    }
}
