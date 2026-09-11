package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getIndividuals() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> getIndividual(int id) {
        return personRepository.findById(id);
    }

    @Override
    public void addPerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }

//    @Override
//    public void updatePerson(int id, Person person) {
//
//    }

    @Override
    public void updatePerson(int id, Person person) {

        Person existingPerson =
                personRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Person not found"));

        existingPerson.setFirstName(
                person.getFirstName());

        existingPerson.setLastName(
                person.getLastName());

        existingPerson.setBirthDate(
                person.getBirthDate());

        personRepository.save(existingPerson);
    }

    @Override
    @Transactional
    public void reset_person_data() {
        personRepository.resetPersonData();
    }

}
