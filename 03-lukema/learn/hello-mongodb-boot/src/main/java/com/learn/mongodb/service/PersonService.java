package com.learn.mongodb.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learn.mongodb.model.Person;
import com.learn.mongodb.repository.PersonRepository;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;
    // private final PersonSeqGeneratorService personSeqGeneratorService;

    /**
     * Implicit constructor injection
     */
    // Auto generated constructor by lombok

    /**
     * !!! Trick !!!
     * `AbstractMongoEventListener<Person>` is used to set new document id automatically.
     * public class PersonModelListener extends AbstractMongoEventListener<Person>
     */
    public Person insertPerson(@NotNull Person person) {
        // Long seq = this.personSeqGeneratorService.generateSequence(Person.SEQ_NAME);
        // person.setId(seq);

        if (person == null) {
            return null;
        }

        return personRepository.insert(person);
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(@NotNull Long id) {
        if (id == null) {
            return null;
        }

        return personRepository.findById(id);
    }

    public Person updateById(@NotNull Long id, Person person) {
        if (id == null) {
            return null;
        }

        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person item = optionalPerson.get();
            item.setId(person.getId());
            item.setName(person.getName());
            return personRepository.save(item);
        } else {
            throw new RuntimeException(String.format("Person with id %d not found", id));
        }
    }

    public void deleteById(@NotNull Long id) {
        if (id == null) {
            return;
        }

        personRepository.deleteById(id);
    }

    public List<Person> findByName(String name) {
        return this.personRepository.findByName(name);
    }

    public List<Person> findByNameContainingIgnoreCase(String name) {
        return this.personRepository.findByNameContainingIgnoreCase(name);
    }

}
