package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface PersonService {

    void reset_person_data() ;

    List<Person> getIndividuals();

    Optional<Person> getIndividual(int id);

    void addPerson(Person person);

    void deletePerson(int id);

    void updatePerson(int id, @Valid Person person);


}
