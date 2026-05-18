package com.learn.suite;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;


public class TestJunit2 {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testPrintMessage() {
        LOG.info("TestJunit2");
    }
}
