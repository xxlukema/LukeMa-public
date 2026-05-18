package com.learn.rest.resource.test.new_junit;


import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.learn.boot.main.HelloSpringBootMainApplication;
import com.learn.pojo.Greeting;

import lombok.extern.log4j.Log4j2;


@Log4j2
@SpringBootTest(classes = HelloSpringBootMainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingControllerIntegrationTest {

    /**
     * For @WebMvcTest, baseUrl = "", not "https://localhost:8443/${server.servlet.context-path:/hello-jbpm-boot}".
     */
    final String baseUrlTemplate = "http://localhost:%s/";

    @Value("${server.port}")
    private String port;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testGreeting() {

        String baseUrl = String.format(baseUrlTemplate, port);
        String url = baseUrl + "/spring/junit/greeting";

        log.info("URL: {}", () -> url);

        // Set the Accept header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<Greeting> response = restTemplate.exchange(url, HttpMethod.GET, entity, Greeting.class);

        log.info("response: {}", () -> response);

        Assertions.assertNotNull(response, "response");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Greeting greeting = response.getBody();
        Assertions.assertNotNull(greeting, "greeting");

        log.info("greeting: {}", () -> greeting);

    }

}
