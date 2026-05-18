package com.learn.resource.bsft.ps.test;


import java.net.URI;
import java.security.SecureRandom;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.learn.resource.bsft.ps.utils.TrustAllHostnameVerifier;
import com.learn.resource.bsft.ps.utils.TrustAllX509TrustManager;


public class PacketSmartTest {

    private static final Logger LOG = LogManager.getLogger();

    private WebTarget base;

    //public static String URL_PacketSmart_QA_END_POINT = "https://10.165.4.106:443";
    //public static String URL_PacketSmart_QA_END_POINT = "http://10.165.4.106:80";
    public static String URL_PacketSmart_QA_END_POINT = "https://10.233.12.89:8443";
    //public static String URL_PacketSmart_QA_END_POINT = "https://hs2-ps-v-ds01-qa-mgmt.bc.int:443";
    private static final String username = "_PIADMIN_#rialto";
    private static final String password = "Broad!23";

    private static final String Base_Path = "MyRest/rest/v1";
    //private static final String Base_Path = "rest/v1";

    private static URI getBaseURI() {
        return buildURI(URL_PacketSmart_QA_END_POINT);
    }

    private static URI buildURI(String url) {
        return UriBuilder.fromUri(url).build();
    }

    @Before
    public void before()
        throws Exception {
        LOG.info("before(). For each test.");

        //SSLTool.disableCertificateValidation();
        //ClientConfig config = new ClientConfig();
        //Client client = ClientBuilder.newClient(config);

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new TrustAllX509TrustManager() };

        // Ignore differences between given hostname and certificate hostname
        HostnameVerifier hv = new TrustAllHostnameVerifier();

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new SecureRandom());

        
        System.setProperty("javax.net.ssl.keyStore", "D:/JavaCAPS/appserver/domains/MyRest/config/luke-keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "lukema");
        
        Client client = ClientBuilder.newBuilder().hostnameVerifier(hv).sslContext(sc).build();

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.universalBuilder().credentialsForBasic(username, password).build();
        //HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
        client.register(feature);

        WebTarget target = client.target(getBaseURI());

        base = target.path(Base_Path);
    }

    @Test
    public void testPost() {

        WebTarget dest = base.path("distributors/postman");

        AdminUser adminUser = new AdminUser();
        adminUser.setAdminName("admin");
        adminUser.setEmailAddress("vnallusamy@broadsoft.com");
        adminUser.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        adminUser.setPassword("anystring");

        Distributor distributor = new Distributor();
        distributor.getAdmin().add(adminUser);

        PsRequest psRequest = new PsRequest();
        psRequest.setDistributor(distributor);

        Response response = dest.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(psRequest, MediaType.APPLICATION_JSON), Response.class);

        LOG.info(response.getStatus());
        LOG.info(response.getStatusInfo());
        LOG.info(response.getMediaType());
        LOG.info(response.toString());

        Assert.assertEquals("POST", 200, response.getStatusInfo().getStatusCode());

        String str = response.readEntity(String.class);

        LOG.info(str);

    }

}
