package com.learn.aaa;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


// @Log4j2
public class AnotherNewTest {

    private static final Logger log = LogManager.getLogger();

    @Test
    public void testRun() {

        /**
         * Log4j2
         */
        log.debug("Start: {}", () -> "test string");

    }

}
