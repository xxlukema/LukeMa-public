package com.learn.shein.neo4j.node;


import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;
import lombok.RequiredArgsConstructor;


// @NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Data
@Node
public class Person {

    @org.springframework.data.neo4j.core.schema.Id
    @GeneratedValue
    private String elementId;

    /**
     * `final` field will be used for `@RequiredArgsConstructor`
     */
    private final String name;

    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
     * to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */
    @Relationship(type = "TEAMMATE")
    public Set<Person> teammates;

    public void worksWith(Person person) {
        if (teammates == null) {
            teammates = new HashSet<>();
        }
        teammates.add(person);
    }

}
