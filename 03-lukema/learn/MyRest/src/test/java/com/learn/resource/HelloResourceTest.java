package com.learn.resource;


import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.learn.utl.UrlConstants;


public class HelloResourceTest {

    private static final Logger LOG = LogManager.getLogger();

    private WebTarget webTarget;

    private static URI getBaseURI() {
        return buildURI(UrlConstants.URL);
    }

    private static URI buildURI(String url) {
        return UriBuilder.fromUri(url).build();
    }

    @Before
    public void before()
        throws Exception {
        LOG.info("before(). For each test.");

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget rest = client.target(getBaseURI());

        webTarget = rest.path("rest");
    }

    @Test
    public void hello0A() {

        WebTarget hello = webTarget.path("hello/0/Luke Ma");

        doRequest(hello, MediaType.APPLICATION_XML, 406);
    }

    @Test
    public void hello0B() {

        WebTarget hello = webTarget.path("hello/0/Luke Ma");

        doRequest(hello, MediaType.TEXT_PLAIN, 200);
    }

    @Test
    public void hello1() {

        WebTarget hello = webTarget.path("hello/1/Luke Ma");

        doRequest(hello, MediaType.TEXT_XML, 200);
    }

    @Test
    public void hello2() {

        WebTarget hello = webTarget.path("hello/2/Luke Ma");

        doRequest(hello, MediaType.TEXT_HTML, 200);
    }

    @Test
    public void hello3() {

        WebTarget hello = webTarget.path("hello/3/Luke Ma");

        doRequest(hello, MediaType.TEXT_HTML, 200);
    }

    private void doRequest(WebTarget target, String mediaType, int httpReturnCode) {
        Response response = target.request(mediaType).get(Response.class);

        LOG.info(response.getStatus());
        LOG.info(response.getStatusInfo());
        LOG.info(response.getMediaType());
        LOG.info(response.toString());

        Assert.assertEquals("GET", httpReturnCode, response.getStatus());

        String str = response.readEntity(String.class);
        LOG.info(str);
    }
}
