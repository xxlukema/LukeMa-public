package com.learn.spring.rest;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;


public class MySpringRestClientTest {

    private static final Logger LOG = LogManager.getLogger();

    private static final String Host = "http://he3ilxvdmid902:10022";
    private static final String Endpoint = "/pcdebt-data-service/exchange-transactions-no-authentication";

    @Test
    public void testMain()
        throws Exception {
        LOG.info("Begin Test.");

        LOG.info("End Test.");

    }

    @Test
    public void testRestJesus()
        throws Exception {
        LOG.info("Begin Test.");

        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());

        String id = null;
        String appId = "Phoenix Single Family Transaction Accounting";
        try {
            UUID uuid = UUID.nameUUIDFromBytes(appId.getBytes("UTF-8"));
            id = uuid.toString();
        } catch (UnsupportedEncodingException e) {
        }

        headers.set("Request-Identifier", id);
        // headers.set("Request-Source-System", "A27009-Phoenix Single Family Transaction Accounting");
        headers.set("Request-Source-System", "pc debt");
        headers.set("Request-Timestamp", "2018-07-12T16:08:01.818-04:00");

        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);

        String url = Host + Endpoint;

        // @formatter:off
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                // .queryParam("start-date", "2018-04-01")
                // .queryParam("end-date", "2018-04-30")
                .queryParam("start-date", "2018-01-01")
                .queryParam("end-date", "2018-01-31")
                .queryParam("page", 1)
                .queryParam("chunk-size", "100");
        // @formatter:on

        String strUrl = builder.build().toUriString();
        LOG.info("strUrl = " + strUrl);

        RestTemplate restTemplate = new RestTemplate();

        try {

            ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, (HttpEntity<HttpHeaders>) requestEntity, String.class);
            String body = response.getBody();

            LOG.info("body = " + body);

        } catch (HttpClientErrorException e) {

            // LOG.error("REST Exception: " + e.getResponseBodyAsString(), e);

            LOG.error("REST Exception: " + e.getResponseBodyAsString());

            try {
                MBSError er = new ObjectMapper().readValue(e.getResponseBodyAsString(), MBSError.class);
                LOG.error("MBS Error root cause: " + er.getRootCause());
                LOG.error("MBS Error request details: " + er.getRequestUri());
                er.getErrors().forEach(x -> {
                    LOG.error("MBS REST Error " + x.getErrorCode() + " with message " + x.getErrorMessage());
                });
            } catch (IOException e1) {
                LOG.error("Ignore Parse Exception");
            }
        }

        LOG.info("End Test.");

    }

    @Test
    public void testRestLuke()
        throws Exception {
        LOG.info("Begin Test.");

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

        interceptors.add((HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
            request.getHeaders().set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            return execution.execute(request, body);
        });

        interceptors.add((HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
            request.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            return execution.execute(request, body);
        });

        interceptors.add((HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
            request.getHeaders().set("Request-Timestamp", "2018-05-11T14:38:00.000-04:00");
            return execution.execute(request, body);
        });

        interceptors.add((HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
            String id = null;
            String appId = "Phoenix Single Family Transaction Accounting";
            try {
                UUID uuid = UUID.nameUUIDFromBytes(appId.getBytes("UTF-8"));
                id = uuid.toString();
            } catch (UnsupportedEncodingException e) {
            }

            request.getHeaders().set("Request-Identifier", id);
            return execution.execute(request, body);
        });

        interceptors.add((HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
            request.getHeaders().set("Request-Source-System", "pc debt");
            return execution.execute(request, body);
        });

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(interceptors);

        String url = Host + Endpoint;

        // @formatter:off
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                // .queryParam("start-date", "2018-04-01")
                // .queryParam("end-date", "2018-04-30")
                .queryParam("start-date", "2018-01-01")
                .queryParam("end-date", "2018-01-31")
                .queryParam("page", 1)
                .queryParam("chunk-size", "100");
        // @formatter:on

        String strUrl = builder.build().toUriString();
        LOG.info("strUrl = " + strUrl);

        try {

            ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, (HttpEntity<HttpHeaders>) null, String.class);
            String body = response.getBody();

            LOG.info("body = " + body);

        } catch (HttpClientErrorException e) {

            // LOG.error("REST Exception: " + e.getResponseBodyAsString(), e);

            LOG.error("REST Exception: " + e.getResponseBodyAsString());

            try {
                MBSError er = new ObjectMapper().readValue(e.getResponseBodyAsString(), MBSError.class);
                LOG.error("MBS Error root cause: " + er.getRootCause());
                LOG.error("MBS Error request details: " + er.getRequestUri());
                er.getErrors().forEach(x -> {
                    LOG.error("MBS REST Error " + x.getErrorCode() + " with message " + x.getErrorMessage());
                });
            } catch (IOException e1) {
                LOG.error("Ignore Parse Exception");
            }
        }

        LOG.info("End Test.");

    }

}
