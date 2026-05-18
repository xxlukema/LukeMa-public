package com.learn.boot.security.test;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.learn.boot.config.BootJpaConfig;
import com.learn.boot.config.BootSecurityConfig;
import com.learn.util.UrlConstants;


/**
 * https://www.baeldung.com/spring-security-method-security
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { BootJpaConfig.class, BootSecurityConfig.class, })
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = { "ADMIN" })
public class SecurityRestResourceSpringClientTest {

    private static final Logger log = LogManager.getLogger();

    private RestTemplate restTemplate = null;

    @SuppressWarnings("unused")
    private HttpEntity<String> entity = null;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void before()
        throws Exception {
        log.info("before(). For each test.");
        restTemplate = new RestTemplate();
        restTemplate.getInterceptors().clear();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin"));

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        // Set the Accept header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
        entity = new HttpEntity<String>("parameters", headers);
    }

    /**
     * TODO: RestTemplate needs boot server running at test.
     */
    @Test
    public void testSecurityPingUsingHeader() {

        // TODO: RestTemplate needs boot server running at test.
        String uri = UrlConstants.BASE_URL + "/spring/security/ping";
        // String uri = "http://localhost/spring/security/ping";

        log.info("URI: {}", () -> uri);

        // @formatter:off
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
                /*
                .queryParam("LastName", user.getLastName())
                .queryParam("Email", user.getEmail())
                .queryParam("PhoneNumber", user.getPhoneNbr());
                */
        // @formatter:on

        String url = builder.toUriString();

        log.debug("[GET] {}", url);

        HttpHeaders headers = createHeaders("admin", "admin");
        Object payload = null;
        HttpEntity<?> requestEntity = new HttpEntity<>(payload, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpMethod httpMethod = HttpMethod.GET;
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
            log.info(response.getStatusCode().name());

            String body = response.getBody();

            log.info(body);
        } catch (RuntimeException e) {
            log.error("[{}]: {} {}", httpMethod, url, e.getMessage(), e);
        }
    }

    /**
     * TODO: RestTemplate needs boot server running at test.
     */
    @Test
    public void testSecurityPingUsingAuthenticationInterceptor() {

        // TODO: RestTemplate needs boot server running at test.
        String uri = UrlConstants.BASE_URL + "/spring/security/ping";
        // String uri = "http://localhost/spring/security/ping";

        log.info("URI: {}", () -> uri);

        // @formatter:off
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        /*
                .queryParam("LastName", user.getLastName())
                .queryParam("Email", user.getEmail())
                .queryParam("PhoneNumber", user.getPhoneNbr());
         */
        // @formatter:on

        String url = builder.toUriString();

        log.debug("[GET] {}", url);

        String response = restTemplate.getForObject(uri, String.class);

        log.info(response);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void noParamGreetingShouldReturnDefaultMessage()
        throws Exception {

        log.info("Called.");

        // @formatter:off
        this.mockMvc.perform(get("/rest/greeting"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, World!"));

        // @formatter:on
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void paramGreetingShouldReturnTailoredMessage()
        throws Exception {

        log.info("Called.");

        // @formatter:off
        this.mockMvc.perform(get("/rest/greeting").param("name", "Spring Community"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
        // @formatter:on
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
