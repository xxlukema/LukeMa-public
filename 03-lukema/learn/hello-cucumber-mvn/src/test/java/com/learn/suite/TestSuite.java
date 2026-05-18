package com.learn.suite;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({ TestJunit2.class, TestJunit1.class })
public class TestSuite {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testPrintMessage() {
        LOG.info("TestSuite");
    }
}
