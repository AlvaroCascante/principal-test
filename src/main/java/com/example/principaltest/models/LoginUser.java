package com.example.principaltest.models;

public interface LoginUser {

    Long getId();

    String getUsername();

    String getPassword();

    UserStatus getStatus();

    PersonSummary getPerson();

    String getRoles();

    interface PersonSummary {

        String getName();

        String getLastname();
    }
}
