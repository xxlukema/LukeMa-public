package com.learn.rest.resource.test.old_test_good;


import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.learn.entity.PortfolioUser;
import com.learn.util.UrlConstants;


public class UserResourceJerseyClientTest {

    private static final Logger LOG = LogManager.getLogger();

    private WebTarget webTarget = null;

    private static URI getBaseURI() {
        return buildURI(UrlConstants.BASE_URL);
    }

    private static URI buildURI(String url) {
        return UriBuilder.fromUri(url).build();
    }

    @Before
    public void before()
        throws Exception {
        LOG.info("before(). For each test.");

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(getBaseURI());

        WebTarget rest = target.path("spring");

        webTarget = rest.path("user").path("add");
    }

    @Test
    public void testPostJersey() {
        PortfolioUser input = new PortfolioUser();
        input.setUsername("lukema");

        LOG.info("URI: " + webTarget.getUri());

        Response response = webTarget.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(input, MediaType.APPLICATION_JSON));

        LOG.info("Form response Status: " + response.getStatus());
        // LOG.info("Form response Data: " + response.readEntity(PortfolioUser.class));
        LOG.info("Form response Data: " + response.getEntity());

        LOG.info("Form response Header: " + response.getMetadata());

        response.getMetadata().forEach((key, value) -> {
            LOG.info(key + ": " + value);
        });

        Assert.assertEquals("POST", 200, response.getStatus());
    }

}
