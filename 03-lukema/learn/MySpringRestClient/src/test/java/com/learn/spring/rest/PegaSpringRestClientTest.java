package com.learn.spring.rest;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
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


public class PegaSpringRestClientTest {

    private static final Logger log = LogManager.getLogger();

    private static final String Host = "https://ecorrcrm.dev.it.census.gov";
    private static final String Endpoint = "/prweb/api/v1/data/D_SurveyQuestionList?Survey=mqa&CaseID=2117E9149002826C975848480F74AD08";
    //private static final String Endpoint = "/prweb/api/v1/data/D_CasesByContact";

    @Ignore
    @Test
    public void testMain()
        throws Exception {
        log.info("Begin Test.");

        log.info("End Test.");

    }

    /**
     * Self Signed Cert. Need to import cert to JRE.
     */
    @Test
    public void testRestPegaPega()
        throws Exception {
        log.info("Begin Test.");

        // HttpHeaders headers = new HttpHeaders();
        // headers.set("Authorization", "Basic d2VidGVhbTpydWxlcw==");

        // HttpHeaders headers = this.createHeaders("webteam", "rules");
        HttpHeaders headers = this.createHeaders("d2VidGVhbTpydWxlcw==");

        // headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());

        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);

        String url = Host + Endpoint;

        // @formatter:off
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                // .queryParam("start-date", "2018-04-01")
                // .queryParam("end-date", "2018-04-30")
                // .queryParam("start-date", "2018-01-01")
                // .queryParam("end-date", "2018-01-31")
                // .queryParam("page", 1)
                // .queryParam("chunk-size", "100")
                ;
        // @formatter:on

        String strUrl = builder.build().toUriString();
        log.info("strUrl = {}", () -> strUrl);

        /**
         * Bypass Self-Signed Cert: This part does not work.
         */
        /*
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        SSLContextBuilder builder2 = new SSLContextBuilder();
        builder2.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder2.build());
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        requestFactory.setHttpClient(httpclient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        */

        RestTemplate restTemplate = new RestTemplate();

        try {

            ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, (HttpEntity<HttpHeaders>) requestEntity, String.class);
            String body = response.getBody();

            log.info("body = " + body);

        } catch (HttpClientErrorException e) {

            // LOG.error("REST Exception: " + e.getResponseBodyAsString(), e);

            log.error("REST Exception: " + e.getResponseBodyAsString());

            try {
                MBSError er = new ObjectMapper().readValue(e.getResponseBodyAsString(), MBSError.class);
                log.error("MBS Error root cause: " + er.getRootCause());
                log.error("MBS Error request details: " + er.getRequestUri());
                er.getErrors().forEach(x -> {
                    log.error("MBS REST Error " + x.getErrorCode() + " with message " + x.getErrorMessage());
                });
            } catch (IOException e1) {
                log.error("Ignore Parse Exception");
            }
        }

        log.info("End Test.");

    }

    @Ignore
    @Test
    public void testRestLuke()
        throws Exception {
        log.info("Begin Test.");

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
        log.info("strUrl = " + strUrl);

        try {

            ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, (HttpEntity<HttpHeaders>) null, String.class);
            String body = response.getBody();

            log.info("body = " + body);

        } catch (HttpClientErrorException e) {

            // LOG.error("REST Exception: " + e.getResponseBodyAsString(), e);

            log.error("REST Exception: " + e.getResponseBodyAsString());

            try {
                MBSError er = new ObjectMapper().readValue(e.getResponseBodyAsString(), MBSError.class);
                log.error("MBS Error root cause: " + er.getRootCause());
                log.error("MBS Error request details: " + er.getRequestUri());
                er.getErrors().forEach(x -> {
                    log.error("MBS REST Error " + x.getErrorCode() + " with message " + x.getErrorMessage());
                });
            } catch (IOException e1) {
                log.error("Ignore Parse Exception");
            }
        }

        log.info("End Test.");

    }

    HttpHeaders createHeaders(String username, String password) {
        String auth = username + ":" + password;
        byte[] bytes = auth.getBytes(StandardCharsets.UTF_8);

        String encodedAuth = Base64.getEncoder().encodeToString(bytes);

        return createHeaders(encodedAuth);
    }

    HttpHeaders createHeaders(String basicToken) {
        return new HttpHeaders() {
            private static final long serialVersionUID = 1L;
            {
                String authHeader = "Basic " + basicToken;
                set("Authorization", authHeader);
            }
        };
    }

}
