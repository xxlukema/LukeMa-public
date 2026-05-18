/*
 * Copyright 2016 the original author or authors.
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.learn.boot.config.BootJpaConfig;
import com.learn.boot.config.BootSecurityConfig;
import com.learn.boot.config.TomcatPort8080Forwarding;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { BootJpaConfig.class, TomcatPort8080Forwarding.class, BootSecurityConfig.class })
@SpringBootTest
@AutoConfigureMockMvc
public class GreetingRestControllerTests {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void noParamGreetingShouldReturnDefaultMessage()
        throws Exception {

        LOG.info("Called.");

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

        LOG.info("Called.");

        // @formatter:off
        this.mockMvc.perform(get("/rest/greeting").param("name", "Spring Community"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
        // @formatter:on
    }

}
