package com.learn.serverless.rest;


import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@RestController
@RequestMapping("/rest")
@Log4j2
public class MyResource {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * curl -i -k -L -X GET 'http://localhost:8080/rest/ping'
     * @return
     */
    @GetMapping("/ping")
    public ResponseEntity<?> pingGet() {

        log.info("Called. ping()");

        return ResponseEntity.status(HttpStatus.OK).body("{\"status\":\"OK\"}");
    }

    /**
     * 1. ResponseEntity<T> represents the entire HTTP response. Besides the body, its API allows you to set
     *    headers and a status code to the response.
     * 2. If you are OK with a HTTP 200 and a serialized version of your POJO, simply returning the POJO is fine.
     * 
     * curl --user admin:admin -i -k -X GET 'http://localhost:8080/rest/greeting'
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
     * 
     * curl --user admin:admin -i -k -X GET 'http://localhost:8080/rest/greeting2'
     */
    @GetMapping("/greeting2")
    public Greeting greeting2(@RequestParam(value = "name", defaultValue = "World") String name) {

        log.info("Called. name=" + name);

        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}


@Data
@NoArgsConstructor
@AllArgsConstructor
class Greeting {
    private long id;
    private String content;
}
