package com.example.demo.controller;

import com.example.demo.Entity.*;
import com.example.demo.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class PersonController {
    private final PersonRepository personRepo;

    public PersonController(PersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    // PERSON API
    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.ok(personRepo.findAll());
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable int id) {
        return personRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/persons")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
            return ResponseEntity.ok(personRepo.save(person));
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person updatedPerson) {
        return personRepo.findById(id)
                .map(person -> {
                    person.setIdperson(updatedPerson.getIdperson());
                    person.setUsername(updatedPerson.getUsername());
                    person.setPassword(updatedPerson.getPassword());
                    person.setName(updatedPerson.getName());
                    person.setBirthday(updatedPerson.getBirthday());
                    person.setPhonenumber(updatedPerson.getPhonenumber());
                    person.setRole(updatedPerson.isRole());
                    person.setGender(updatedPerson.getGender());
                    person.setEmail(updatedPerson.getEmail());
                    return ResponseEntity.ok(personRepo.save(person));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (personRepo.existsById(id)) {
            personRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
