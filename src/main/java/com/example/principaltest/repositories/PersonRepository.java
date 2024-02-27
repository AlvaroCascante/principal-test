package com.example.principaltest.repositories;


import com.example.principaltest.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByIdNumber(String idNumber);
}