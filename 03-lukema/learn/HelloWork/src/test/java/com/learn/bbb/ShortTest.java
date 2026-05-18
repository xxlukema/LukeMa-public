package com.learn.bbb;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class ShortTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testMain()
        throws Exception {
        LOG.info("Begin Test.");

        Short s = 1;

        LOG.info("s = " + s);

        short ss = s;

        LOG.info("ss = " + ss);

        LOG.info("End Test.");

    }
}
