package com.learn.rest.resource.junit;


import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.pojo.Greeting;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RequestMapping("/spring/junit")
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final GreetingService service;

    /**
     * 1. application.properties:
     * my.property.age=25
     *
     * 2. application-default.properties:
     * my.property.age=18
     *
     * 3. application-test.properties:
     * my.property.age = 40
     *
     */
    @Value("${my.property.age:50}")
    private String age;

    /**
     * application.properties:
     * my.property.name=Luke
     */
    @Value("${my.property.name:Tom}")
    private String name;

    public GreetingController(GreetingService service) {
        this.service = service;
    }

    /**
     * curl -k -i -X GET https://localhost:8443/spring/junit/greeting
     * @return
     */
    @RequestMapping("/greeting")
    public ResponseEntity<Greeting> greeting() {

        Assert.hasText("25", age);
        Assert.hasText("Luke", name);

        log.info("GreetingController ::: name: {}, age: {}", () -> name, () -> age);

        String name = service.greet();

        return ResponseEntity.status(HttpStatus.OK).body(new Greeting(counter.incrementAndGet(), String.format(template, name)));
    }

}
