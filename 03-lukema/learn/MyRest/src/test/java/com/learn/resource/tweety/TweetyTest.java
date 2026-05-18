package com.learn.resource.tweety;


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
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.learn.element.Todo;
import com.learn.util.NcmUtils;
import com.learn.utl.UrlConstants;


public class TweetyTest {

    private static final Logger LOG = LogManager.getLogger();

    private WebTarget base;

    private static URI getBaseURI() {
        return buildURI(UrlConstants.URL_TRWEETY_END_POINT);
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
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.universalBuilder().credentialsForBasic("verima", "1234").build();
        client.register(feature);

        WebTarget target = client.target(getBaseURI());

        WebTarget rest = target.path("rest");

        base = rest.path("v1");
    }

    @Test
    public void testPost() {

        WebTarget customer = base.path("customer");

        Todo input = new Todo();

        input.setId(222L);
        input.setDescription("Desc");
        input.setSummary("Summary");

        String xml = NcmUtils.readFile("xml/Request-Peter.xml");

        Response response = customer.request().accept(MediaType.APPLICATION_XML).post(Entity.entity(xml, MediaType.APPLICATION_XML), Response.class);
        //Response response = customer.request().accept(MediaType.APPLICATION_XML).post(Entity.xml(xml), Response.class);

        LOG.info(response.getStatus());
        LOG.info(response.getStatusInfo());
        LOG.info(response.getMediaType());
        LOG.info(response.toString());

        Assert.assertEquals("POST", 200, response.getStatusInfo().getStatusCode());

        String str = response.readEntity(String.class);

        LOG.info(str);

    }

}
