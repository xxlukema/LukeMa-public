package com.freddiemac.gradle.test;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.freddiemac.gradle.main.HelloBootGradleMainApplication;

import lombok.extern.log4j.Log4j2;


@SpringBootTest(classes = HelloBootGradleMainApplication.class)
@Log4j2
class HelloServerlessApplicationTests {

    @Test
    void contextLoads() {
        log.debug(() -> "Here");
    }

}
