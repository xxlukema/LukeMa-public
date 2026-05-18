package com.learn.mongodb.controller;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.learn.mongodb.main.HelloMongoBootApplication;
import com.learn.mongodb.model.Person;
import com.learn.mongodb.utils.SenarioContext;

import lombok.extern.log4j.Log4j2;


/**
 * This test will start boot server automatically. There is no need to start boot server separately.
 *
 * JUnit 4:
 * @RunWith(SpringJUnit4ClassRunner.class)
 *
 */
@Log4j2
// @SpringBootTest(classes = { HelloMongoBootApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SpringBootTest(classes = { HelloMongoBootApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("fixedport")
@TestMethodOrder(OrderAnnotation.class)
// @ContextConfiguration(classes = { MongoConfig.class })
class HelloMongoBootApplicationTests {

    String baseUrl = "http://localhost:8080/hello-mongodb-boot/person";

    @Test
    @Order(0)
    void testGet() {

        log.debug(() -> "Enter 000000000");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Person[]> response = restTemplate.getForEntity(baseUrl + "/all", Person[].class);

        log.debug("response: {}", () -> Arrays.asList(response.getBody()));
    }

    @Test
    @Order(1)
    void testInsert() {

        log.debug(() -> "Enter 1111111111");

        Person person = new Person(0L, "John Doe");

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Person> request = new HttpEntity<>(person);
        ResponseEntity<Person> response = restTemplate.exchange(baseUrl + "/add", HttpMethod.POST, request, Person.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Person body = response.getBody();

        log.debug("response.getBody(): {}", () -> body);

        Long id = body == null ? null : body.getId();
        SenarioContext.setContext("id", id);

        /**
         * Add another person to prove the new ID get updated.
         */
        Person person2 = new Person(0L, "John Doe");

        HttpEntity<Person> request2 = new HttpEntity<>(person2);
        ResponseEntity<Person> response2 = restTemplate.exchange(baseUrl + "/add", HttpMethod.POST, request2, Person.class);

        Assertions.assertEquals(HttpStatus.OK, response2.getStatusCode());

        Assertions.assertEquals(response.getBody().getId() + 1, response2.getBody().getId());

        log.debug("Exit 11111111 - id: {}", () -> id);
    }

    @Test
    @Order(2)
    void testUpdate() {

        Long id = SenarioContext.getContext("id");

        log.debug("Enter 22222222222 - id: {}", () -> id);

        Person person = new Person(0L, "Tom Bonner");

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Person> request = new HttpEntity<>(person);
        ResponseEntity<Person> response = restTemplate.exchange(baseUrl + "/update/" + id, HttpMethod.PUT, request, Person.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        log.debug("response.getBody(): {}", () -> response.getBody());
    }

    /**
     * String url = "http://test.com/solarSystem/planets/{planet}/moons/{moon}";
     *
     * 1. URI (URL) path parameters
     *    Map<String, String> urlParams = new HashMap<>();
     *    urlParams.put("planets", "Mars");
     *    urlParams.put("moons", "Phobos");
     *
     * 2. Query parameters
     *    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
     *          .queryParam("firstName", "Mark")
     *          .queryParam("lastName", "Watney");
     *
     * 3. URI uri = builder.buildAndExpand(urlParams).toUri();
     *
     *
     */
    @Test
    @Order(3)
    void testDelete() {

        log.debug("Enter 333333333 - before delete");

        // @formatter:off
        // String url = "http://test.com/solarSystem/planets/{planet}/moons/{moon}";
        String urlFind = baseUrl + "/nameConstains";

        // 1. URI (URL) path parameters
        Map<String, Object> urlParams = new HashMap<>();
        // urlParams.put("planets", "Mars");
        // urlParams.put("moons", "Phobos");

        // 2. Query parameters
        UriComponentsBuilder builderFind = UriComponentsBuilder.fromUriString(urlFind)
                // Add query parameter
                // .queryParam("firstName", "Mark")
                // .queryParam("lastName", "Watney");
                .queryParam("name", "Tom");

        URI uriFind = builderFind.buildAndExpand(urlParams).toUri();
        // @formatter:on

        RestTemplate restTemplate = new RestTemplate();

        Person person = new Person(0L, "Tom Bonner");
        HttpEntity<Person> request = new HttpEntity<>(person);
        ResponseEntity<Person[]> response = restTemplate.exchange(uriFind, HttpMethod.GET, request, Person[].class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Person[] persons = response.getBody();
        Assertions.assertNotNull(persons);
        Assertions.assertTrue(persons.length > 0);

        for (Person item : persons) {

            String urlDelete = baseUrl + "/delete/{id}";
            UriComponentsBuilder builderDelete = UriComponentsBuilder.fromUriString(urlDelete);
            urlParams = new HashMap<>();
            urlParams.put("id", item.getId());
            URI uriDelete = builderDelete.buildAndExpand(urlParams).toUri();
            restTemplate.exchange(uriDelete, HttpMethod.DELETE, null, Person[].class);
        }

        log.debug("Enter 333333333 - after delete");

        ResponseEntity<Person[]> response2 = restTemplate.exchange(uriFind, HttpMethod.GET, request, Person[].class);

        log.debug("Enter 333333333 - after delete - response: {}", () -> response2);

        Assertions.assertEquals(HttpStatus.OK, response2.getStatusCode());

        Person[] persons2 = response2.getBody();

        log.debug("Enter 333333333 - after delete - persons2 == null ? {}", () -> persons2 == null);

        Assertions.assertNotNull(persons2);

        log.debug("Enter 333333333 - after delete - persons2.length: {}", () -> persons2.length);

        Assertions.assertTrue(persons2.length == 0);

    }

}
