package com.learn.rest.resource;


import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


/**
 * 1. ResponseEntity<T> represents the entire HTTP response. Besides the body, its API allows you to set
 *    headers and a status code to the response.
 * 2. If you are OK with a HTTP 200 and a serialized version of your POJO, simply returning the POJO is fine.
 */
@RequestMapping("/rest/security")
@RestController
@Log4j2
public class PingSecurityController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * curl -k -i -u "admin:admin" -X OPTIONS 'https://localhost:8443/rest/security/ping'
     * curl -k -i -u "admin:admin" -X GET 'https://localhost:8443/rest/security/ping'
     */
    @GetMapping("/ping")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public String pingGet() {

        log.info("Called. ping()");

        return "{\"status\":\"OK\"}\n";
    }

    @PostMapping("/ping")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public String pingPost() {

        log.info("Called. ping()");

        return "{\"status\":\"OK\"}\n";
    }

    /**
     * 1. ResponseEntity<T> represents the entire HTTP response. Besides the body, its API allows you to set
     *    headers and a status code to the response.
     * 2. If you are OK with a HTTP 200 and a serialized version of your POJO, simply returning the POJO is fine.
     *
     * curl -k -i --user admin:admin -X GET 'https://localhost:8443/rest/security/greeting'
     */
    @GetMapping("/greeting")
    public ResponseEntity<Greeting> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {

        log.info("Called. name=" + name);

        return ResponseEntity.status(HttpStatus.OK).body(new Greeting(counter.incrementAndGet(), String.format(template, name)));
    }

    /**
     * 1. ResponseEntity<T> represents the entire HTTP response. Besides the body, its API allows you to set
     *    headers and a status code to the response.
     * 2. If you are OK with a HTTP 200 and a serialized version of your POJO, simply returning the POJO is fine.
     * curl -k -i --user admin:admin -X GET 'https://localhost:8443/rest/security/greeting2'
     */
    @GetMapping("/greeting2")
    public Greeting greeting2(@RequestParam(value = "name", defaultValue = "World") String name) {

        log.info("Called. name=" + name);

        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    /**
     * Difference between @RequestParam and @RequestAttribute
     *
     * @RequestParam is used to bind parameter values from 'query string' e.g. in http://www.example.com?myParam=3,
     * myParam=3 can populate @RequestParam parameter.
     *
     * On the other hand, @RequestAttribute is to access objects which have been populated on the server-side but during the
     * same HTTP request, for example they can be populated in an interceptor or a filter.
     *
     * requestId is set by BootWebFilter.
     *
     * curl -k -i -u "admin:admin" -X OPTIONS 'https://localhost:8443/rest/security/post/ping'
     * curl -k -i -u "admin:admin" -H "Accept: application/json" -H "Content-Type: application/json"  \
     *      -X POST 'https://localhost:8443/rest/security/post/ping' -d '{"name": "Luke Ma", "age": "81"}'
     */
    @PostMapping(value = "/post/ping")
    public String postPing(@RequestBody MyPojo myPojo, @RequestAttribute("requestId") Long requestId) {

        log.info("Called. myPojo={}", () -> myPojo);
        log.info("requestId={}", () -> requestId);

        return myPojo + " POST OK!\n";
    }

    /**
     * curl -k -i -u "admin:admin" -X OPTIONS 'https://localhost:8443/rest/security/post/object'
     * curl -k -i -u "admin:admin" -H "Accept: application/json" -H "Content-Type: application/json"  \
     *      -X POST 'https://localhost:8443/rest/security/post/object' -d '{"name": "Luke Ma", "age": "81"}'
     */
    @PostMapping(value = "/post/object")
    // @PreAuthorize("permitAll()")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public Greeting postPingObject(@RequestBody MyPojo myPojo) {

        log.info("Called. myPojo={}", () -> myPojo);

        Greeting greeting = new Greeting(1, "Request POJO: " + myPojo);

        return greeting;
    }
}


@Data
class MyPojo {
    private String name;
    private int age;
}


@Data
@NoArgsConstructor
@AllArgsConstructor
class Greeting {
    private long id;
    private String content;
}
