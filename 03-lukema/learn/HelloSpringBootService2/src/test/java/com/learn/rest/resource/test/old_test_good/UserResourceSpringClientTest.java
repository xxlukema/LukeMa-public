package com.learn.rest.resource.test.old_test_good;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import com.learn.entity.PortfolioUser;
import com.learn.util.UrlConstants;


public class UserResourceSpringClientTest {

    private static final Logger LOG = LogManager.getLogger();

    private RestTemplate restTemplate = null;

    @Before
    public void before()
        throws Exception {
        LOG.info("before(). For each test.");
        restTemplate = new RestTemplate();
    }

    @Test
    public void testPostSpring() {
        PortfolioUser input = new PortfolioUser();
        input.setUsername("lukema");

        String uri = UrlConstants.BASE_URL + "/spring/user/add";

        LOG.info("URI: " + uri);

        //HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON_TYPE);
        //HttpEntity<String> entity = new HttpEntity<String>("Hello World!", headers);

        PortfolioUser user = restTemplate.postForObject(uri, input, PortfolioUser.class);

        LOG.info(user);
        /*
        LOG.info("Form response Status: " + response.getStatus());
        LOG.info("Form response Data: " + response.readEntity(Date.class));
        
        LOG.info("Form response Header: " + response.getHeaders());
        
        response.getHeaders().forEach((key, value) -> {
            LOG.info(key + ": " + value);
        });
        
        Assert.assertEquals("POST", 200, response.getStatus());
        */
    }

}
