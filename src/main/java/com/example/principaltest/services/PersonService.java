package com.example.principaltest.services;

import com.example.principaltest.models.Person;
import com.example.principaltest.models.PostPerson;
import com.example.principaltest.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person findByIdNumberOrBuild(PostPerson person) {
        return this.findByIdNumber(person.idNumber())
                .orElse(Person.builder()
                        .idNumber(person.idNumber())
                        .name(person.name())
                        .lastname(person.lastname())
                        .birthday(person.birthday())
                        .email(person.email())
                        .gender(person.gender())
                        .build());
    }

    public Optional<Person> findByIdNumber(String idNumber) {
        return personRepository.findByIdNumber(idNumber);
    }
}
