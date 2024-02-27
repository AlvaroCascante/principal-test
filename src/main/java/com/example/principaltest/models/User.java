package com.example.principaltest.models;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int")
    @JsonView({
            ApiResponseView.UserList.class
    })
    private Long id;

    @Column(name = "created_at", nullable = false)
    @JsonView({
            ApiResponseView.UserDetail.class
    })
    private LocalDateTime createdAt;

    // TODO Implement last_login update
    @Column(name = "last_login", nullable = false)
    @JsonView({
            ApiResponseView.UserDetail.class
    })
    private LocalDateTime lastLogin;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_person", referencedColumnName = "id", nullable = false)
    @JsonView({
            ApiResponseView.UserList.class
    })
    private Person person;

    @Column(name = "nickname")
    @JsonView({
            ApiResponseView.UserList.class
    })
    private String nickname;

    @Column(name = "password")
    @JsonView({
            ApiResponseView.NoShow.class
    })
    private String password;

    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonView({
            ApiResponseView.UserDetail.class
    })
    private UserStatus status;

    @Column(name = "username", nullable = false)
    @JsonView({
            ApiResponseView.UserList.class,
            ApiResponseView.StudentDetail.class
    })
    private String username;

    @Column(name = "roles", nullable = false)
    @JsonView({
            ApiResponseView.UserDetail.class
    })
    private String roles;
}