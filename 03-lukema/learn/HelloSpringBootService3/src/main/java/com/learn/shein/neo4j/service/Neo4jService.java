package com.learn.shein.neo4j.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.learn.exception.AppException;
import com.learn.shein.neo4j.node.Person;
import com.learn.shein.neo4j.repository.Neo4jPersonRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class Neo4jService {

    /**
    * Implicit constructor injection
    */
    // Auto generated constructor by lombok
    // @Autowired
    private final Neo4jPersonRepository neo4jPersonRepository;

    @Transactional
    public Person save(Person person)
        throws AppException {
        try {
            return neo4jPersonRepository.save(person);
        } catch (Exception e) {
            throw new AppException("neo4jPersonRepository.save(person) Exception", e);
        }
    }

    @Transactional
    public Person findByName(String namen)
        throws AppException {
        try {
            return neo4jPersonRepository.findByName(namen);
        } catch (Exception e) {
            throw new AppException("neo4jPersonRepository.save(person) Exception", e);
        }
    }

    public List<Person> findAll()
        throws AppException {
        try {
            return neo4jPersonRepository.findAll();
        } catch (Exception e) {
            throw new AppException("neo4jPersonRepository.findByTeammatesName(name) Exception", e);
        }
    }

    public List<Person> findByTeammatesName(String name)
        throws AppException {
        try {
            return neo4jPersonRepository.findByTeammatesName(name);
        } catch (Exception e) {
            throw new AppException("neo4jPersonRepository.findByTeammatesName(name) Exception", e);
        }
    }

}
