package com.learn.test;


import org.apache.log4j.Logger;
import org.junit.Test;


public class Log4j1Test {

    private static final Logger LOG = Logger.getLogger(Log4j1Test.class);

    @Test
    public void testLog() {

        LOG.info("Begin Test");

        /**
         * Lambda expression does not work for log4j-1.2.17.jar 
         */
        // LOG.info(() -> "Test Provider.");

        LOG.info("End Test.");

    }

}
