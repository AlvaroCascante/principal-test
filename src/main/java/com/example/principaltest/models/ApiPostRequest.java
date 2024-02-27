package com.example.principaltest.models;


/**
 * Used to manage all possible requests objects that could be received
 */

public record ApiPostRequest(
        PostPerson person,
        PostStudent student,
        PostUser user) {

    public ApiPostRequest(PostPerson person, PostStudent student, PostUser user) {
        this.person = person;
        this.student = student;
        this.user = user;
    }

    public ApiPostRequest(PostPerson person) {
        this(person, null, null);
    }

    public ApiPostRequest(PostStudent student) {
        this(null, student, null);
    }

    public ApiPostRequest(PostUser user) {
        this(null, null, user);
    }
}
