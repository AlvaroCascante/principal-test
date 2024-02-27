package com.example.principaltest.services;

import com.example.principaltest.exceptions.RecordNotFoundException;
import com.example.principaltest.models.*;
import com.example.principaltest.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;

    private final PersonService personService;

    private final UserService userService;

    public StudentService(PersonService personService, StudentRepository studentRepository, UserService userService) {
        this.personService = personService;
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }


    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(PostStudent student, String user) {
        Student entity = buildEntity(student, getPersonEntity(student.person()), getUserEntity(user));

        return studentRepository.save(entity);
    }

    private Student getEntity(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User not found " + id));
    }

    private Student buildEntity(PostStudent student, Person person, User createdBy) {
        return Student.builder()
                .createdAt(LocalDateTime.now())
                .createdBy(createdBy)
                .person(person)
                .build();
    }

    private Person getPersonEntity(PostPerson person) {
        return personService.findByIdNumberOrBuild(person);
    }

    private User getUserEntity(String username) {
        return userService.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found " + username));
    }
}
