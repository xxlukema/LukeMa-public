package com.learn.rest.resource.test.new_junit;


import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.learn.rest.resource.junit.GreetingComponent;
import com.learn.rest.resource.junit.GreetingService;

import lombok.extern.log4j.Log4j2;


/**
 * 1. @SpringBootTest can be used for service level tests to load application.properties and application-test.properties files (ApplicationContext).
 * 2. @SpringBootTest(classes = ...) can also be specified using @ContextConfiguration(classes=...). If it finds @SpringBootApplication, it will start server.
 * 3. @SpringBootTest without any parameters (default to webEnvironment = SpringBootTest.WebEnvironment.MOCK), or webEnvironment = SpringBootTest.WebEnvironment.NONE will not start boot server.
 * 4. @SpringBootTest contains @ExtendWith(SpringExtension.class). Therefore, @ExtendWith(SpringExtension.class) for JUnit 5 can be omitted.
 * 5. @SpringBootTest(webEnvironment default is SpringBootTest.WebEnvironment.MOCK)
 * 6. @Import({ GreetingService.class, GreetingComponent.class })
 * 7. A mockBean.method() does doNothing() by default.
 */
@Log4j2
@SpringBootTest
@Import({ GreetingService.class })
@ActiveProfiles("test")
class GreetingServiceMockTest {

    /**
     * Already defined in src/main/java/com/learn/boot/config/RestTemplateConfig.java
     */
    /*
    @Configuration
    @SuppressWarnings("unused")
    static class GreetingServiceMockTestConfig {
        @Bean
        RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
    */

    @Autowired
    private GreetingService greetingService;

    @MockitoBean
    private GreetingComponent greetingComponent;

    @MockitoBean
    private RestTemplate restTemplate;

    // @Disabled
    @Test
    void testGreeting() {

        /**
         * A mockBean.method() does doNothing() by default. Need to use when().thenReturn() to specify the behavior.
         */
        greetingComponent.print();
        when(this.greetingComponent.print()).thenReturn("Return mock string.");
        String msg = greetingService.greet();
        log.info("GreetingServiceMockTest ::: greeting message: {}", () -> msg);
    }

    /**
     * Demo of RestTemplate Matchers:
     *
     * 1. Mockito.verify() does not work with @ExtendWith(SpringExtension.class) or @RunWith(SpringRunner.class).
     * 2. A mockBean.method() does doNothing() by default. Need to use when().thenReturn() to specify the behavior.
     *
     */
    // @Disabled
    @Test
    void testRestTemplateMatcher() {

        /**
         * A mockBean.method() does doNothing() by default. Need to use when().thenReturn() to specify the behavior.
         */

        ObjectNode parent = JsonNodeFactory.instance.objectNode();
        TextNode child = JsonNodeFactory.instance.textNode("Text Node");
        parent.set("DocumentId", child);

        log.debug("parent: {}", () -> parent.toString());
        log.debug("child: {}", () -> parent.get("DocumentId").asText());

        ResponseEntity<String> response = new ResponseEntity<>(parent.toString(), HttpStatus.OK);

        // @formatter:off
        when(this.restTemplate.exchange(Mockito.anyString(),
                                        Mockito.any(HttpMethod.class),
                                        Mockito.<HttpEntity<?>> any(),
                                        Mockito.<Class<String>> any(),
                                        Mockito.any(Object[].class)
                                       )
            ).thenReturn(response);

        /**
         * JsonNode response type:
         */
        /**
        when(this.restTemplate.exchange(Mockito.anyString(),
                                        Mockito.any(HttpMethod.class),
                                        Mockito.<HttpEntity<?>> any(),
                                        Mockito.<Class<JsonNode>> any(),
                                        Mockito.any(Object[].class)
                                       )
            ).thenReturn(response);
        */

        /**
         * Mockito.verify does not work!
         *
         * Or, use ArgumentMatchers:
         */
        /**
        when(this.restTemplate.exchange(ArgumentMatchers.anyString(),
                                        ArgumentMatchers.any(HttpMethod.class),
                                        ArgumentMatchers.<HttpEntity<?>> any(),
                                        ArgumentMatchers.<Class<JsonNode>> any(),
                                        ArgumentMatchers.any(Object[].class)
                                       )
            ).thenReturn(response);
        */
        // @formatter:off

        /**
         * A mockBean.method() does doNothing() by default. Need to use when().thenReturn() to specify the behavior.
         */
        greetingComponent.print();
        when(this.greetingComponent.print()).thenReturn("Return mock string.");
        String msg = greetingService.greet();
        log.info("GreetingServiceMockTest ::: greeting message: {}", () -> msg);
    }
}
