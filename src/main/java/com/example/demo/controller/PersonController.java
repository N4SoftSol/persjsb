package com.example.demo.controller;

import com.example.demo.dto.JwtUserInfo;
import com.example.demo.exception.IndividualNotFoundException;
import com.example.demo.model.Person;
import com.example.demo.service.AuditService;
import com.example.demo.service.PersonService;
import com.example.demo.util.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data-app")
@Slf4j
public class PersonController {

    private final PersonService personService;
    private final AuditService auditService;

    @Autowired
    public PersonController(PersonService personService, AuditService auditService) {
        this.personService = personService;
        this.auditService = auditService;
    }

    @GetMapping("/individuals")
    @PreAuthorize("hasAuthority('SCOPE_read')")
    public List<Person> getIndividuals() {
        return personService.getIndividuals();
    }

    @GetMapping("/individuals/{id}")
    @PreAuthorize("hasAuthority('SCOPE_read')")
    public Person getIndividual(@PathVariable int id, Authentication authentication) {
//        auditService.log(
//                authentication.getName(),
//                "GET",
//                "PERSON",
//                "SUCCESS",
//                "ID=" + id);
        JwtUserInfo userInfo =
                JwtUtils.getUserInfo(
                        authentication);
        auditService.log(
                userInfo.username(),
                userInfo.displayName(),
                userInfo.email(),
                "GET",
                "PERSON",
                "SUCCESS",
                "ID=" + id);
        return
//                personService.getIndividual(id).orElseThrow(() -> new RuntimeException());

        personService.getIndividual(id)
                                .orElseThrow(() ->
                        new IndividualNotFoundException(
                                "Individual not found: " + id));
    }

    @PostMapping("/individuals")
    @PreAuthorize("hasAuthority('SCOPE_write')")
    public ResponseEntity<?> addIndividual(@Valid @RequestBody Person person, Authentication authentication) {
        JwtUserInfo userInfo =
                JwtUtils.getUserInfo(
                        authentication);
        personService.addPerson(person);
        auditService.log(
                userInfo.username(),
                userInfo.displayName(),
                userInfo.email(),
                "POST",
                "PERSON",
                "SUCCESS",
                "New person created");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/individuals/{id}")
    @PreAuthorize("hasAuthority('SCOPE_write')")
    public ResponseEntity<?> updateIndividual(
            @PathVariable int id,
            @Valid @RequestBody Person person, Authentication authentication) {

        JwtUserInfo userInfo =
                JwtUtils.getUserInfo(
                        authentication);

        personService.updatePerson(id, person);
        auditService.log(
                userInfo.username(),
                userInfo.displayName(),
                userInfo.email(),
                "PUT",
                "PERSON",
                "SUCCESS",
                "ID=" + id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/individuals/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<?> deleteIndividual(@PathVariable int id,  Authentication authentication) {

        JwtUserInfo userInfo =
                JwtUtils.getUserInfo(
                        authentication);

//        personService.getIndividual(id).orElseThrow(() -> new RuntimeException());
//        return ResponseEntity.noContent().build();
        personService.getIndividual(id)
                .orElseThrow(() ->
                        new IndividualNotFoundException(
                                "Individual not found: " + id));
        personService.deletePerson(id);
        auditService.log(
                userInfo.username(),
                userInfo.displayName(),
                userInfo.email(),
                "DELETE",
                "PERSON",
                "SUCCESS",
                "ID=" + id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/whoami")
    public Map<String, Object> whoami(
            Authentication authentication) {

        return Map.of(
                "name", authentication.getName(),
                "authorities",
                authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList());
    }

    @PostMapping("/reset_person_data")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Map<String, String>> reset_person_data(Authentication authentication) {

        JwtUserInfo userInfo =
                JwtUtils.getUserInfo(
                        authentication);

        log.info("POST /data-app/reset_person_data reached - ADMIN request received");
        personService.reset_person_data();

        Map<String, String> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "Procedure RESET_PERSON_DATA executed successfully.");
        auditService.log(
                userInfo.username(),
                userInfo.displayName(),
                userInfo.email(),
                "RESET",
                "PERSON",
                "SUCCESS",
                "RESET_PERSON_DATA");
        return ResponseEntity.ok(response);
    }
}
