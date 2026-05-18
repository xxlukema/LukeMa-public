package com.learn.shein.neo4j;


import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.learn.boot.config.BootJpaConfig;
import com.learn.boot.config.BootSecurityConfig;
import com.learn.exception.AppException;
import com.learn.shein.neo4j.boot.config.Neo4jConfig;
import com.learn.shein.neo4j.node.Person;
import com.learn.shein.neo4j.service.Neo4jService;

import lombok.extern.log4j.Log4j2;


@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@Log4j2
@ContextConfiguration(classes = { Neo4jConfig.class, BootJpaConfig.class, BootSecurityConfig.class })
// @SpringBootTest(classes = HelloSpringBootMainApplication.class)
@SpringBootTest
public class Neo4jBootTest {

    @Autowired
    private Neo4jService neo4jService;

    /**
     * match (n) where n.name in [ 'Greg', 'Craig', 'Roy' ] return n
     * match (n:Person) return n
     */
    @Disabled
    @Order(1)
    @Test
    public void testCreate()
        throws AppException {

        log.debug(() -> "Begin Test.");

        // neo4jPersonRepository.deleteAll();

        Person greg = new Person("Greg");
        Person roy = new Person("Roy");
        Person craig = new Person("Craig");

        List<Person> team = Arrays.asList(greg, roy, craig);

        log.info(() -> "Before linking up with Neo4j...");

        team.stream().forEach(person -> log.info(() -> person.toString()));

        greg = neo4jService.save(greg);
        roy = neo4jService.save(roy);
        craig = neo4jService.save(craig);

        // greg = neo4jService.findByName(greg.getName());
        greg.worksWith(roy);
        greg.worksWith(craig);
        neo4jService.save(greg);

        // roy = neo4jService.findByName(roy.getName());
        roy.worksWith(craig);
        // We already know that roy works with greg
        neo4jService.save(roy);

        /**
         * !!! Important !!!
         * !!! Trick !!!
         * cyclic relationship will cause StackOverflow error: `person: [!!!com.learn.shein.neo4j.entity.Person@29fef6c0=>java.lang.StackOverflowError:null!!!]`
         */
        // craig.worksWith(roy);
        // neo4jService.save(craig);

        // We already know craig works with roy and greg

        log.info("Lookup each person by name...");

        team.stream().forEach(person -> log.info(() -> {
            try {
                return neo4jService.findByName(person.getName()).toString();
            } catch (AppException e) {
                log.error(() -> "Exception neo4jService.findByName", e);
            }
            return person;
        }));

        List<Person> teammates = neo4jService.findByTeammatesName(greg.getName());
        log.info(() -> "The following have Greg as a teammate...");
        teammates.stream().forEach(person -> log.info("person name: {}", () -> person.getName()));

        log.debug(() -> "End Test.");
    }

    /**
     * match (n) where n.name in [ 'Greg', 'Craig', 'Roy' ] return n
     * match (n:Person) return n
     * match (n)-[r:TEAMMATE]->(z:Person) return n, collect(r), collect(z)
     */
    @Order(2)
    @Test
    public void testFind()
        throws AppException {

        var team = this.neo4jService.findAll();

        log.debug("team.size(): {}", () -> team.size());

        team.forEach(person -> {
            log.debug("person: {}", () -> person);
        });

        // log.debug("team: {}", () -> team);
    }

}
