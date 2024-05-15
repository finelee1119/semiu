package com.semiuniv.semiu.constant;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    STUDENT("ROLE_STUDENT"),
    PROFESSOR("ROLE_PROFESSOR");

    private String value;

    UserRole(String value) {
        this.value = value;
    }
}
