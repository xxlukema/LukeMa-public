package com.learn.drools.test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import com.learn.drools.main.HelloDroolsBootApplication;
import com.learn.drools.model.Order;
import com.learn.drools.test.SpringDroolsApplicationTests.SpringDroolsApplicationTestsConfig;

import lombok.extern.log4j.Log4j2;


@Log4j2
@SpringBootTest(classes = HelloDroolsBootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SpringDroolsApplicationTestsConfig.class)
public class SpringDroolsApplicationTests {

    @Configuration
    public static class SpringDroolsApplicationTestsConfig {

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }

    /**
     * For @WebMvcTest, baseUrl = "", not "http://localhost:8080/${server.servlet.context-path:/hello-jbpm-boot}".
     */
    final String baseUrlTemplate = "http://localhost:%s/";

    @LocalServerPort
    private String port;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void contextLoads() {
        log.debug("Enter.");
    }

    @Test
    public void testDiscount() {

        String baseUrl = String.format(baseUrlTemplate, port);
        String url = baseUrl + "/order";

        log.info("URL: {}", () -> url);

        // Set the Accept header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        Order order = new Order();
        order.setCardType("ICICI");
        order.setPrice(16_000);
        order.setName("Luke");

        HttpEntity<Order> entity = new HttpEntity<>(order, headers);

        ResponseEntity<Order> response = restTemplate.exchange(url, HttpMethod.POST, entity, Order.class);

        log.info("response: {}", () -> response);

        Assertions.assertNotNull(response, "response");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Order updatedOrder = response.getBody();
        log.info("updatedOrder: {}", () -> updatedOrder);

        Assertions.assertNotNull(updatedOrder, "updatedOrder");
        Assertions.assertEquals(8, updatedOrder.getDiscount(), "discount");
    }

}
