package com.learn.resource.bsft.ps.test;


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


public class BsftQATest {

    private static final Logger LOG = LogManager.getLogger();

    private WebTarget base;

    private static final String username = "bdv-api-test-dev-ma";
    private static final String password = "1234";
    
    private static URI getBaseURI() {
        return buildURI(UrlConstants.URL_BSFT_QA_END_POINT);
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
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.universalBuilder().credentialsForBasic(username, password).build();
        client.register(feature);

        WebTarget target = client.target(getBaseURI());

        base = target.path("rest");
    }

    @Test
    public void testPost() {

        WebTarget customer = base.path("customer");

        Todo input = new Todo();

        input.setId(222L);
        input.setDescription("Desc");
        input.setSummary("Summary");

        String xml = NcmUtils.readFile("xml/customer/Create-Customer-good.xml");

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
