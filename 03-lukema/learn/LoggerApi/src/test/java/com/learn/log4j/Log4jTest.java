package com.learn.log4j;


import org.apache.log4j.Logger;
import org.junit.Test;


public class Log4jTest {
    private static final Logger LOG = Logger.getLogger(Log4jTest.class);

    @Test
    public void testLog()
        throws Exception {

        LOG.info("Begin Test.");

        LOG.debug("End Test debug.");

        LOG.info("End Test.");

    }
}
