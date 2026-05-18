package com.learn;


import org.junit.Test;


public class BapLoggerTest {
    //private static final Logger LOG = Logger.getLogger(BapLoggerTest.class);

    private BAPLogger logger = BAPLogger.getBAPLogger(BapLoggerTest.class.getName());

    @Test
    public void test()
        throws Exception {
        // LOG.info("Begin Test.");

        // LOG.info("Hello World!");

        // LOG.info("End Test.");

        logger.debug("Test BAPLogger.");

    }
}
