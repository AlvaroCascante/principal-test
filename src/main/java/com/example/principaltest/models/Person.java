package com.example.principaltest.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int")
    @JsonView({
            ApiResponseView.NoShow.class
    })
    private Long id;

    @Column(name = "birthday")
    @JsonFormat(pattern="dd-MM-yyyy")
    @JsonView({
            ApiResponseView.UserDetail.class
    })
    private LocalDate birthday;

    @Column(name = "email", nullable = false)
    @JsonView({
            ApiResponseView.UserList.class,
            ApiResponseView.StudentDetail.class
    })
    private String email;

    @Column(name = "gender")
    @JsonView({
            ApiResponseView.UserList.class
    })
    private String gender;

    @Column(name = "id_number", updatable = false)
    @JsonView({
            ApiResponseView.UserDetail.class,
            ApiResponseView.StudentDetail.class
    })
    private String idNumber;

    @Column(name = "lastname", nullable = false)
    @JsonView({
            ApiResponseView.UserList.class,
            ApiResponseView.StudentDetail.class
    })
    private String lastname;

    @Column(name = "name", nullable = false)
    @JsonView({
            ApiResponseView.StudentList.class,
            ApiResponseView.UserList.class,
            ApiResponseView.StudentDetail.class
    })
    private String name;
}