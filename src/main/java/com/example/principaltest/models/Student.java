package com.example.principaltest.models;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int")
    @JsonView({
            ApiResponseView.StudentList.class
    })
    private Long id;

    @Column(name = "created_at", nullable = false)
    @JsonView({
            ApiResponseView.StudentDetail.class
    })
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    @JsonView({
            ApiResponseView.StudentDetail.class
    })
    private User createdBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_person", referencedColumnName = "id", nullable = false)
    @JsonView({
            ApiResponseView.StudentList.class
    })
    private Person person;
}
