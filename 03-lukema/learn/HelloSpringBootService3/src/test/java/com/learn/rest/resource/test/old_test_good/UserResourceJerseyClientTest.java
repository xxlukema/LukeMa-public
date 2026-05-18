package com.learn.rest.resource.test.old_test_good;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.learn.entity.PortfolioUser;
import com.learn.util.UrlConstants;

import lombok.extern.log4j.Log4j2;


@Log4j2
class UserResourceJerseyClientTest {

    private WebTarget webTarget = null;

    private static URI getBaseURI() {
        return buildURI(UrlConstants.BASE_URL);
    }

    private static URI buildURI(String url) {
        return UriBuilder.fromUri(url).build();
    }

    @BeforeEach
    public void beforeEach() {
        log.info("before(). For each test.");

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(getBaseURI());

        WebTarget rest = target.path("spring");

        webTarget = rest.path("user").path("add");
    }

    @Test
    void testPostJersey() {
        PortfolioUser input = new PortfolioUser();
        input.setUsername("lukema");

        log.info("URI: {}", webTarget.getUri());

        Response response = webTarget.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(input, MediaType.APPLICATION_JSON));

        log.info("Form response Status: {}", response.getStatus());
        log.info("Form response Data: {}", response.getEntity());

        log.info("Form response Header: {}", response.getMetadata());

        response.getMetadata().forEach((key, value) -> {
            log.info(key + ": " + value);
        });

        assertEquals(200, response.getStatus());
    }

}
