package com.learn.rest.resource.test.old_test_good;


import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Repeat;

import com.learn.util.UrlConstants;


public class DbPingResourceJerseyClientTest {

    private static final Logger LOG = LogManager.getLogger();

    private int ctr = 0;

    @Rule
    public RepeatRule repeatRule = new RepeatRule();

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

        webTarget = target.path("spring").path("db").path("ping");
    }

    /**
     * By default, all PostgreSQL deployments on Compose start with a connection limit that sets the maximum number 
     * of connections allowed to 100. If your deployment is on PostgreSQL 9.5 or later you can control the number of 
     * incoming connections allowed to the deployment, increasing the maximum if required
     * 
     * @Repeat(2000) OK
     */
    @Test
    @Repeat(20)
    public void testPingDb() {

        LOG.info("URI: " + webTarget.getUri() + " ctr=" + ++ctr);

        Response response = webTarget.request().accept(MediaType.APPLICATION_JSON).get();

        LOG.info("Form response Status: " + response.getStatus());
        // LOG.info("Form response CurrentDate: " + response.readEntity(CurrentDatePojo.class));
        LOG.info("Form response CurrentDate: " + response.getEntity());

        LOG.info("Form response Header: " + response.getMetadata());

        response.getMetadata().forEach((key, value) -> {
            LOG.info(key + ": " + value);
        });

        Assert.assertEquals("POST", 200, response.getStatus());
    }

}
