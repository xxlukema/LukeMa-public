package com.learn.test;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class HelloTest {

    @Test
    public void testHello() {
        log.debug(() -> "Test start");

        log.debug(() -> "Test end");
    }
}
