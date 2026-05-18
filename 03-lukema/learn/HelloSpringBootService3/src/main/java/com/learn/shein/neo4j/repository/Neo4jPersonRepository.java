package com.learn.shein.neo4j.repository;


import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.learn.shein.neo4j.node.Person;


/**
 * Unnecessary `@Repository`
 */
public interface Neo4jPersonRepository
    extends Neo4jRepository<Person, String> {

    Person findByName(String name);

    List<Person> findByTeammatesName(String name);

    /*
    @org.springframework.data.neo4j.repository.query.Query("""
      match (p:Person)-[:TEAMMATE]->(:Person)
      return p
         """)
    */
    @org.springframework.data.neo4j.repository.query.Query("""
            match (p:Person)-[r:TEAMMATE]->(z:Person)
            return p, collect(r), collect(z)
               """)
    public List<Person> findAll();

}
