package com.example.demo.repository;

import com.example.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Procedure(procedureName = "RESET_RRSCJSB_PERSON_DATA")
    void resetPersonData();

}
