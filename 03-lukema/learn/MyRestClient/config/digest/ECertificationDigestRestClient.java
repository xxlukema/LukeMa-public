package com.freddiemac.pe.sourcing.ncm.api.ecert.service.util;


import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPDigestAuthFilter;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;


public class ECertificationDigestRestClient {

    private static final Logger LOG = LogManager.getLogger();

    public static final HttpURLConnectionFactory httpURLConnectionFactory = new ClientProxyConnectionFactory(ECertStringConstants.ProxyHost,
            ECertStringConstants.ProxyPort, ECertStringConstants.ProxyUsername, ECertStringConstants.ProxyPassword.toCharArray());

    private WebResource service;

    @Before
    public void before() {

        LOG.debug("Called.");

        SSLTool.disableCertificateValidation();

        URLConnectionClientHandler clientHandler = new URLConnectionClientHandler(httpURLConnectionFactory);
        Client client = new Client(clientHandler);

        client.setFollowRedirects(true);

        client.addFilter(new HTTPDigestAuthFilter(ECertStringConstants.TargetUsername2, ECertStringConstants.TargetPassword2));

        service = client.resource(ECertStringConstants.getBaseURI2());
    }

    @Test
    public void testGet() {

        ClientResponse response = service.path("rest").path("eCertificationResponse").accept(MediaType.TEXT_XML).get(ClientResponse.class);

        LOG.info(Thread.currentThread().getId() + ": " + response.getEntity(String.class));

        Assert.assertEquals("GET", response.getStatus(), 200);
    }

    @Test
    public void testPost() {

        String xmlString = "<a>hello xml file version-1.2</a>";

        Form form = new Form();
        form.add("CertificationRequestIndentifier", 99887766L);

        ClientResponse response = service.path("rest").path("eCertificationResponse").path("postECertificationResponse").type("application/xml")
                .post(ClientResponse.class, xmlString);

        LOG.info(Thread.currentThread().getId() + ": " + response.getEntity(String.class));

        Assert.assertEquals("POST", response.getStatus(), 200);
    }

}
