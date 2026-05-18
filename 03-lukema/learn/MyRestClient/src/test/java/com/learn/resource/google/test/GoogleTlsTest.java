package com.learn.resource.google.test;


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
import org.junit.Test;


/**
 * This is to prove that invocation of TLS call does not need to handle certificate issues 
 * programmatically. It is done automatically by JRE. 
 */
public class GoogleTlsTest {

    private static final Logger LOG = LogManager.getLogger();

    private WebTarget base;

    public static String URL_Google_END_POINT = "https://www.google.com:443";

    //private static final String Base_Path = "MyRest/rest/v1";

    private static URI getBaseURI() {
        return buildURI(URL_Google_END_POINT);
    }

    private static URI buildURI(String url) {
        return UriBuilder.fromUri(url).build();
    }

    @Before
    public void before()
        throws Exception {
        LOG.info("before(). For each test.");

        Client client = ClientBuilder.newBuilder().build();

        WebTarget target = client.target(getBaseURI());

        base = target;
    }

    @Test
    public void testPost() {

        WebTarget dest = base;

        Response response = dest.request().accept(MediaType.APPLICATION_JSON).get(Response.class);

        LOG.info(response.getStatus());
        LOG.info(response.getStatusInfo());
        LOG.info(response.getMediaType());
        LOG.info(response.toString());

        Assert.assertEquals("POST", 200, response.getStatusInfo().getStatusCode());

        String str = response.readEntity(String.class);

        LOG.info(str);

    }

}
