package com.learn.bbb;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;


public class CaseSwitchTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void test()
        throws Exception {
        LOG.info("Begin Test.");

        String value = "One";

        switch (value) {

            case "One":
            case "one":
                LOG.info("One");
                break;

            default:
                LOG.info("Default");
        }

        LOG.info("End Test.");

    }
}
