package com.learn.spring;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
class LogTest {

    @Test
    void testLog() {
        log.debug(() -> "Hello world.");
    }

}
