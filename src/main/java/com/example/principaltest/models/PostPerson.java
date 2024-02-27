package com.example.principaltest.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record PostPerson(
        String name,
        String lastname,
        String idNumber,
        String email,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate birthday,
        String gender) {
}
