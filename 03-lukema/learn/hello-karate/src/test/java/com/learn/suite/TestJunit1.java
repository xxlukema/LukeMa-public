package com.learn.suite;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;


public class TestJunit1 {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testPrintMessage() {
        LOG.info("TestJunit1");
    }
}
