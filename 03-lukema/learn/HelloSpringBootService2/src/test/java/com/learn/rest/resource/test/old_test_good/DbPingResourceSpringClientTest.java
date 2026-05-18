package com.learn.rest.resource.test.old_test_good;


import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.learn.entity.PortfolioUser;
import com.learn.pojo.CurrentDatePojo;
import com.learn.util.UrlConstants;


public class DbPingResourceSpringClientTest {

    private static final Logger LOG = LogManager.getLogger();

    private RestTemplate restTemplate = null;

    @SuppressWarnings("unused")
    private HttpEntity<String> entity = null;

    @Before
    public void before()
        throws Exception {
        LOG.info("before(). For each test.");
        restTemplate = new RestTemplate();

        // Set the Accept header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
        entity = new HttpEntity<String>("parameters", headers);
    }

    @Test
    public void testPingDb() {
        PortfolioUser input = new PortfolioUser();
        input.setUsername("lukema");

        String uri = UrlConstants.BASE_URL + "/spring/db/ping";

        LOG.info("URI: " + uri);

        // Add the Jackson message converter
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        CurrentDatePojo currentDate = restTemplate.getForObject(uri, CurrentDatePojo.class);

        LOG.info(currentDate);

    }

}
