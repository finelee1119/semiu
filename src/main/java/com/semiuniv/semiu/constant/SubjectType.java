package com.semiuniv.semiu.constant;

public enum SubjectType {
    major("전공"),
    general("일반");

    private String value;

    SubjectType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
