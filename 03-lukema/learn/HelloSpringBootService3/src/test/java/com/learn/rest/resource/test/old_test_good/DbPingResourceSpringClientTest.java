package com.learn.rest.resource.test.old_test_good;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.learn.entity.PortfolioUser;
import com.learn.pojo.CurrentDatePojo;
import com.learn.util.UrlConstants;

import jakarta.ws.rs.core.MediaType;


class DbPingResourceSpringClientTest {

    private static final Logger log = LogManager.getLogger();

    private RestTemplate restTemplate = null;

    @SuppressWarnings("unused")
    private HttpEntity<String> entity = null;

    @BeforeEach
    public void beforeEach() {
        log.info("before(). For each test.");
        restTemplate = new RestTemplate();

        // Set the Accept header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
        entity = new HttpEntity<>("parameters", headers);
    }

    @Test
    void testPingDb() {
        PortfolioUser input = new PortfolioUser();
        input.setUsername("lukema");

        String uri = UrlConstants.BASE_URL + "/spring/db/ping";

        log.info("URI: {}", () -> uri);

        CurrentDatePojo currentDate = restTemplate.getForObject(uri, CurrentDatePojo.class);

        assertNotNull(currentDate);

        log.info(currentDate);
    }
}
