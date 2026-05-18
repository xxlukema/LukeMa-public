package com.learn.log4j2;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


public class ConfigureAndWatchTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testLog()
        throws Exception {

        LOG.info("Begin Test.");

        while (true) {
            LOG.debug("debug line.");

            try {
                Thread.sleep(5_000);
            } catch (Exception e) {
                LOG.info("End Test.");
            }
        }

    }
}
