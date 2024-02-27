package com.example.principaltest.models;

public class ApiResponseView {

    public static class Always {}
    public static class NoShow {}

    // Students
    public static class StudentList extends Always {}
    public static class StudentDetail extends StudentList {}

    // Users
    public static class UserList extends Always {}
    public static class UserDetail extends UserList {}

}
