package com.learn.mongodb.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.mongodb.model.Person;
import com.learn.mongodb.service.PersonService;


@RestController
@RequestMapping("person")
public class PersonController {

    private final PersonService personService;

    /**
     * Implicit constructor injection
     */
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(path = "add")
    public ResponseEntity<Person> create(@RequestBody Person person) {

        person = personService.insertPerson(person);

        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    /**
     * curl -X GET http://localhost:8080/hello-mongodb-boot/person/all
     */
    @GetMapping(path = "all")
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Optional<Person>> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findById(id));
    }

    @GetMapping(path = "name")
    public ResponseEntity<List<Person>> findByName(@RequestParam(name = "name", required = true) String name) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findByName(name));
    }

    @GetMapping(path = "nameConstains")
    public ResponseEntity<List<Person>> findByNameContainingIgnoreCase(@RequestParam(name = "name", required = true) String name) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findByNameContainingIgnoreCase(name));
    }

    @PutMapping(path = "update/{id}")
    public void update(@PathVariable Long id, @RequestBody Person person) {
        personService.updateById(id, person);
    }

    @DeleteMapping(path = "delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        personService.deleteById(id);
    }
}
