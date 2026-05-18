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


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.learn.boot.config.BootJpaConfig;
import com.learn.boot.config.BootSecurityConfig;


@ContextConfiguration(classes = { BootJpaConfig.class, BootSecurityConfig.class })
@SpringBootTest
class ValueTests {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    // @Value("${my.property.name:}") // With value
    // @Value("${my.property.name1:}") // Not set. Default empty String
    @Value("${my.property.name1:}") // Not set. Default empty String
    private String name;

    @Test
    void testValue() {

        log.info("Called. {}", name);
        assertNotNull(name);
    }

}
