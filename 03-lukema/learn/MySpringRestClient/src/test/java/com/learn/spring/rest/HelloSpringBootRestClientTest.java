package com.learn.spring.rest;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


public class HelloSpringBootRestClientTest {

    private static final Logger log = LogManager.getLogger();

    private static final String Host = "http://localhost:8080";
    private static final String Endpoint = "/rest/post/object";

    @Ignore
    @Test
    public void testMain()
        throws Exception {
        log.info("Begin Test.");

        log.info("End Test.");

    }

    @Test
    public void testDoPost() {
        log.info("Begin Test.");

        String url = Host + Endpoint;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        String strUrl = builder.build().toUriString();
        log.debug("strUrl = {}", strUrl);

        MyPojo myPojo = new MyPojo("Luke Ma", 18);

        // String response = doPost(strUrl, myPojo, String.class);
        Greeting response = doPost(strUrl, myPojo, Greeting.class);
        
        log.debug("response = {}", response);

        log.info("End Test.");
    }

    public <R, T> T doPost(String requestPath, R payload, Class<T> clazz) {
        log.debug("Enter doGet");

        return doExchange(requestPath, HttpMethod.POST, payload, clazz);
    }

    public <R, T> T doExchange(String requestPath, HttpMethod method, R payload, Class<T> clazz) {
        log.debug("Enter doExchange");

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<R> requestEntity = new HttpEntity<>(payload, createHeaders("user", "user"));
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        log.debug("requestPath = {}", requestPath);

        ResponseEntity<T> response = null;
        try {
            response = restTemplate.exchange(requestPath, method, (HttpEntity<R>) requestEntity, clazz);
        } catch (RuntimeException e) {
            log.error("Unable to get response from pega. {}", e);
            throw e;
        }

        T responseBody = response.getBody();
        return responseBody;
    }

    HttpHeaders createHeaders(String username, String password) {
        String auth = username + ":" + password;
        byte[] bytes = auth.getBytes(StandardCharsets.UTF_8);

        String encodedAuth = Base64.getEncoder().encodeToString(bytes);

        return createHeaders(encodedAuth);
    }

    HttpHeaders createHeaders(String basicToken) {
        return new HttpHeaders() {
            private static final long serialVersionUID = 1L;
            {
                String authHeader = "Basic " + basicToken;
                set("Authorization", authHeader);
            }
        };
    }

}


class MyPojo {
    private String name;
    private int age;

    public MyPojo(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}


class Greeting {
    private long id;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("Greeting [id=%s, content=%s]", id, content);
    }

}
