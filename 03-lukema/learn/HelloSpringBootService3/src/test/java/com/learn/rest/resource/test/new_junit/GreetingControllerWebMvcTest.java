package com.learn.rest.resource.test.new_junit;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.learn.conf.test.BootMvcTestConfig;
import com.learn.rest.resource.junit.GreetingController;
import com.learn.rest.resource.junit.GreetingService;

import lombok.extern.log4j.Log4j2;


/**
 * 1. @WebMvcTest: Do full Spring application context is started but without the server - BUT, this will instantiate multiple controllers
 * 2. @WebMvcTest(GreetingController.class): Do full Spring application context is started but without the server - This will instantiate only one related controller
 * 3. Spring automatically injects the service dependency into the controller (because of the constructor signature - Use explicit/implicit constructor injection).
 * 4. For @WebMvcTest, baseUrl = "", not "https://localhost:8443/${server.servlet.context-path:/hello-jbpm-boot}".
 *
 * For **JUnit 5**, use: `@ExtendWith(SpringExtension.class)`
 * For **JUnit 4**, use: `@RunWith(SpringRunner.class)`
 * For **Mockito**, use: `@ExtendWith(MockitoExtension.class)` to use `@InjectMocks` and `@Mock`
 *
 * This line has not use: @TestPropertySource(locations = { "classpath:application.properties", "classpath:application-test.properties" })
 *
 * @ActiveProfiles("test") --- This line causes application-test.properties values to override application.properties values
 *
 * This line is required for @WebMvcTest(value = GreetingController.class): @Import(GreetingController.class)
 *
 * This line is optional: @AutoConfigureMockMvc
 *
 */
@Log4j2
// @ExtendWith(SpringExtension.class)
// @ExtendWith(MockitoExtension.class)  /** to use `@InjectMocks` and `@Mock` */
// @WebMvcTest(controllers = {PricingController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@WebMvcTest(value = GreetingController.class)
@Import({ GreetingController.class })
@ContextConfiguration(classes = BootMvcTestConfig.class)
@ActiveProfiles("test")
@WithMockUser(username = "user", password = "user")
class GreetingControllerWebMvcTest {

    /**
     * For @WebMvcTest, baseUrl = "", not "https://localhost:8443/${server.servlet.context-path:/hello-jbpm-boot}".
     */
    // final String baseUrl = "https://localhost:8443/";

    @Autowired
    private MockMvc mockMvc;

    // @Autowired
    // TestRestTemplate testRestTemplate;

    @MockitoBean
    private GreetingService greetingService;

    /**
     * 1. application.properties:
     * my.property.age=25
     *
     * 2. application-default.properties:
     * my.property.age=18
     *
     * 3. application-test.properties:
     * my.property.age = 40
     *
     */
    @Value("${my.property.age:50}")
    private String age;

    /**
     * application.properties:
     * my.property.name=Luke
     */
    @Value("${my.property.name:Tom}")
    private String name;

    /**
     * For @WebMvcTest, baseUrl = "", not "https://localhost:8443/${server.servlet.context-path:/hello-jbpm-boot}".
     */
    // final private String url = "/spring/junit/greeting";
    private final String url = "https://localhost:8443/spring/junit/greeting";

    @Test
    void testGreetingShouldReturnMessageFromService()
        throws Exception {

        log.info("name: {}, age: {}", () -> name, () -> age);

        assertEquals("40", age, "age");
        assertEquals("Luke", name, "name");

        when(greetingService.greet()).thenReturn("Hello, Mock");
        // when(greetingService.greet()).thenCallRealMethod();

        // @formatter:off
        this.mockMvc.perform((RequestBuilder) get(url))
                    // .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect( item -> {
                        String responseContent = item.getResponse().getContentAsString();
                        assertEquals(true, responseContent.contains("Hello, Mock"));
                    });
        // @formatter:on

        /*
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        log.info("response body: {}", () -> response.getBody());
        */
    }

}
