package com.example.principaltest.models;

public record PostUser(
        PostPerson person,
        String nickname,
        String password,
        UserStatus status,
        String username) {
}
