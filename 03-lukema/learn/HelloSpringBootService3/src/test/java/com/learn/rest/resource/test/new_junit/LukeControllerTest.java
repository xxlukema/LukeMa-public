package com.learn.rest.resource.test.new_junit;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.learn.boot.config.MyProperties;
import com.learn.boot.main.HelloSpringBootMainApplication;
import com.learn.rest.controller.LukeController;
import com.learn.service.LukeNestedService;
import com.learn.service.LukeService;

import lombok.extern.log4j.Log4j2;


/**
 * 1. `LukeController` can `@Autowire` `LukeService`, but it cannot `@Autowire` `LukeNestedService`. Therefore,
 *    `LukeService` is neither added as `@Autowire` nor as `@MockitoBean`.
 * 2. `LukeService` needs `LukeNestedService`, that is why `LukeNestedService` is added as a `@MockitoBean`.
 */
@Log4j2
@WebMvcTest(value = LukeController.class)
@Import({
        LukeService.class,
        MyProperties.class
})
@ContextConfiguration(classes = { HelloSpringBootMainApplication.class })
class LukeControllerTest {

    /**
     * `@Autowired` is not needed if `mockMvc` is initialized in `setupMockMvc()`
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Needed if to set advisor and handler.
     */
    @Autowired
    protected LukeController lukeController;

    @MockitoBean
    private LukeNestedService lukeNestedService;

    // @MockitoBean
    // private AfpControllerAdvisor afpControllerAdvisor;

    /**
     * Needed if to set advisor and handler.
     */
    /*
    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(lukeController)
                .setControllerAdvice(afpControllerAdvisor)
                .setCustomArgumentResolvers(new AfpCodeCustomPageHandler())
                .build();
    }
    */

    @Test
    void testHello()
        throws Exception {
        var memberId = "123";

        when(lukeNestedService.getName()).thenReturn("Luke from MockMvc");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/luke/{memberId}", memberId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(request().asyncNotStarted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.memberId").value(memberId));

        verify(lukeNestedService, times(1)).getName();
    }
}
