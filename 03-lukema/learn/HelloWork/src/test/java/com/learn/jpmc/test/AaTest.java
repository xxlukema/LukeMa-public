package com.learn.jpmc.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class AaTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testMain()
        throws Exception {
        
        LOG.info("Begin Test.");

        LOG.info("End Test.");

    }
}
