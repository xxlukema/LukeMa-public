package com.learn.bbb;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class HelloWorldTest {
    private static final Logger LOG = LogManager.getLogger();

    //private static final String QuantityFormat = "######0.00";

    @Test
    public void testMain()
        throws Exception {
        LOG.info("Begin Test." + Math.abs(1 - 3));

        LOG.info("End Test.");

    }
}
