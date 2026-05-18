/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.learn.boot.hello.test;


import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.learn.boot.config.BootJpaConfig;
import com.learn.boot.config.BootSecurityConfig;

import lombok.extern.log4j.Log4j2;


@ContextConfiguration(classes = { BootJpaConfig.class, BootSecurityConfig.class })
@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
class GreetingMvcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("spring.datasource.password")
    private String value;

    @Test
    void value() {
        log.info("value = {}", value);
        assertNotNull(value);
    }

    @Test
    void homePage()
        throws Exception {

        log.info("Called.");

        // N.B. jsoup can be useful for asserting HTML content
        // @formatter:off
        mockMvc.perform(get("/"))
               .andExpect(content().string(containsString("Spring Boot Web Thymeleaf + Spring Security")));
        // @formatter:on
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void greeting()
        throws Exception {

        log.info("Called.");

        // @formatter:off
        mockMvc.perform(get("/greeting"))
               .andDo(print())
               .andExpect(content().string(containsString("Hello, World!")));
        // @formatter:on
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void greetingWithUser()
        throws Exception {

        log.info("Called.");

        // @formatter:off
        mockMvc.perform(get("/greeting").param("name", "Greg"))
               .andDo(print())
               .andExpect(content().string(containsString("Hello, Greg!")));
        // @formatter:on
    }

}
