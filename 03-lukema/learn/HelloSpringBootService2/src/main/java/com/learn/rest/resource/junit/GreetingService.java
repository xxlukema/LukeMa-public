package com.learn.rest.resource.junit;


import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class GreetingService {

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

    @Autowired
    private GreetingComponent greetingComponent;

    @Autowired
    private RestTemplate restTemplate;

    public String greet() {

        // Assert.isTrue("40".equals(age), "age");
        // Assert.isTrue("Luke".equals(name), "name");

        String str = greetingComponent.print();

        log.info("GreetingService ::: return from greetingComponent.print(): {}", () -> str);

        log.info("GreetingService ::: name: {}, age: {}", () -> name, () -> age);

        pingGoogle();

        return "Hello, World";
    }

    public String pingGoogle() {

        String url = "https://www.google.com/";

        log.info("URL: {}", () -> url);

        // Set the Accept header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        log.info("response: {}", () -> response);

        if (response == null) {
            log.info("Response is null. By default, @MockBean memeber function does nothing, unless specified with Mockito.when().thenReturns().");
            return null;
        } else {
            String body = response.getBody();
            log.info("\n reponse body: {}\n", () -> body);
            return body;
        }

    }
}
