package com.learn.rest.controller;


import java.security.Principal;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.learn.pojo.Greeting;
import com.learn.pojo.MyPojo;
import com.learn.service.AyncEmailService;

import lombok.extern.log4j.Log4j2;


/**
 * 1. ResponseEntity<T> represents the entire HTTP response. Besides the body, its API allows you to set
 *    headers and a status code to the response.
 * 2. If you are OK with a HTTP 200 and a serialized version of your POJO, simply returning the POJO is fine.
 */
@RequestMapping("/spring/nosecurity")
@RestController
@Log4j2
public class PingNoSecurityController {

    private static final String TEMPLATE = "Hello, %s!";
    private static final String PROP1_LOG_MSG = "prop1 #{}#";
    private static final String PROP2_LOG_MSG = "prop2 #{}#";
    private static final String PROP3_PROP4_LOG_MSG = "prop3.prop4 #{}#";
    private static final String CALLED_NAME_LOG_MSG = "Called. name={}";

    private final AtomicLong counter = new AtomicLong();

    private static final String STATUS_OK_JSON = """
       {
         "status":"OK"
       }
       """;

    @Value("")
    private String prop1;

    @Value("")
    private String prop2;

    @Value("${prop3.prop4}")
    private String prop3Prop4;

    @Autowired
    AyncEmailService asyncEmailService;

    /**
     * curl -k -i -X GET 'https://localhost:8443/spring/nosecurity/ping'
     */
    @GetMapping("/ping")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public String pingGet() {

        log.info(() -> "Called. ping()");
        log.info(PROP1_LOG_MSG, () -> prop1);
        log.info(PROP2_LOG_MSG, () -> prop2);
        log.info(PROP3_PROP4_LOG_MSG, () -> prop3Prop4);

        return STATUS_OK_JSON;
    }

    /**
     * curl -k -i -X POST 'https://localhost:8443/spring/nosecurity/greeting'
     */
    @PostMapping("/ping")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public String pingPost() {

        log.info(() -> "Called. ping()");

        log.info(PROP1_LOG_MSG, () -> prop1);
        log.info(PROP2_LOG_MSG, () -> prop2);
        log.info(PROP3_PROP4_LOG_MSG, () -> prop3Prop4);

        return STATUS_OK_JSON;
    }

    /**
     * curl -k -i -X POST 'https://localhost:8443/spring/nosecurity/greeting'
     */
    @PostMapping("/ping2")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public String pingPost2(@RequestParam String str1, @RequestParam String str2) {

        log.info("Called. ping2() with params: {} {}", () -> str1, () -> str2);

        log.info(PROP1_LOG_MSG, () -> prop1);
        log.info(PROP2_LOG_MSG, () -> prop2);
        log.info(PROP3_PROP4_LOG_MSG, () -> prop3Prop4);

        return STATUS_OK_JSON;
    }

    /**
     * 1. ResponseEntity<T> represents the entire HTTP response. Besides the body, its API allows you to set
     *    headers and a status code to the response.
     * 2. If you are OK with a HTTP 200 and a serialized version of your POJO, simply returning the POJO is fine.
     *
     * curl -k -i -X GET 'https://localhost:8443/spring/nosecurity/greeting'
     */
    @GetMapping("/greeting")
    public ResponseEntity<Greeting> greeting(@RequestParam(defaultValue = "World") String name) {
        log.info(CALLED_NAME_LOG_MSG, () -> name);

        log.info(PROP1_LOG_MSG, () -> prop1);
        log.info(PROP2_LOG_MSG, () -> prop2);
        log.info(PROP3_PROP4_LOG_MSG, () -> prop3Prop4);

        return ResponseEntity.status(HttpStatus.OK).body(new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name)));
    }

    /**
     * 1. ResponseEntity<T> represents the entire HTTP response. Besides the body, its API allows you to set
     *    headers and a status code to the response.
     * 2. If you are OK with a HTTP 200 and a serialized version of your POJO, simply returning the POJO is fine.
     *
     * curl -k -i -X GET 'https://localhost:8443/spring/nosecurity/sendmail'
     * @throws TimeoutException 
     */
    @GetMapping("/sendmail")
    public String sendmail() throws TimeoutException {

        asyncEmailService.sendEmail("to@example.com", "Subject", "Email body");

        return STATUS_OK_JSON;
    }

    /**
     * 1. ResponseEntity<T> represents the entire HTTP response. Besides the body, its API allows you to set
     *    headers and a status code to the response.
     * 2. If you are OK with a HTTP 200 and a serialized version of your POJO, simply returning the POJO is fine.
     *
     * curl -k -i -X GET 'https://localhost:8443/spring/nosecurity/validate'
     */
    @GetMapping("/validate")
    public ResponseEntity<Greeting> validate(@RequestParam(defaultValue = "World") String name) {

        return greeting(name);
    }

    /**
     * curl -k -i -X GET 'https://localhost:8443/spring/nosecurity/user'
     */
    @GetMapping("/user")
    public Principal getUser(Principal principal) {
        return principal;
    }

    /**
     * 1. ResponseEntity<T> represents the entire HTTP response. Besides the body, its API allows you to set
     *    headers and a status code to the response.
     * 2. If you are OK with a HTTP 200 and a serialized version of your POJO, simply returning the POJO is fine.
     * curl -k -i -X GET 'https://localhost:8443/spring/nosecurity/greeting2'
     */
    @GetMapping("/greeting2")
    public Greeting greeting2(@RequestParam(defaultValue = "World") String name) {
        log.info(CALLED_NAME_LOG_MSG, () -> name);

        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
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
     * curl -k -i -X OPTIONS 'https://localhost:8443/spring/nosecurity/post/ping'
     * curl -k -i -H "Accept: application/json" -H "Content-Type: application/json"  \
     *      -X POST 'https://localhost:8443/spring/nosecurity/post/ping' -d '{"name": "Luke Ma", "age": "81"}'
     */
    @PostMapping(value = "/post/ping")
    public String postPing(@RequestBody MyPojo myPojo, @RequestAttribute("requestId") Long requestId) {

        log.info("Called. myPojo={}", () -> myPojo);
        log.info("requestId={}", () -> requestId);

        return myPojo + " POST OK!\n";
    }

    /**
     * curl -k -i -X OPTIONS 'https://localhost:8443/spring/nosecurity/post/object'
     * curl -k -i -H "Accept: application/json" -H "Content-Type: application/json"  \
     *      -X POST 'https://localhost:8443/spring/nosecurity/post/object' -d '{"name": "Luke Ma", "age": "81"}'
     */
    @PostMapping(value = "/post/object")
    public Greeting postPingObject(@RequestBody MyPojo myPojo) {

        log.info("Called. myPojo={}", () -> myPojo);

        return new Greeting(1, "Request POJO: " + myPojo);
    }
}
