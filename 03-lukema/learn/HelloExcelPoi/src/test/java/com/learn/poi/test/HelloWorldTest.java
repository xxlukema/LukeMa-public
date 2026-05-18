package com.learn.poi.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


public class HelloWorldTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void method() {
        LOG.info("Begin Test.");

        LOG.info("End Test.");
    }

}
