package com.learn.rest.resource.test.old_test_good;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import com.learn.entity.PortfolioUser;
import com.learn.util.UrlConstants;

import lombok.extern.log4j.Log4j2;


@Log4j2
class UserResourceSpringClientTest {

    private RestTemplate restTemplate = null;

    @BeforeEach
    public void before() {
        log.info("before(). For each test.");
        restTemplate = new RestTemplate();
    }

    @Test
    void testPostSpring() {
        PortfolioUser input = new PortfolioUser();
        input.setUsername("lukema");

        String uri = UrlConstants.BASE_URL + "/spring/user/add";

        log.info("URI: " + uri);

        PortfolioUser user = restTemplate.postForObject(uri, input, PortfolioUser.class);

        log.info(user);
    }

}
