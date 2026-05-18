package com.learn.bbb;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class PatternMatchTest {
    private static final Logger LOG = LogManager.getLogger();

    private static final String TN_PATTERN = "[0-9]{10,10}";
    
    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        String toValidate = "2123456789a";

        boolean matches = toValidate.matches(TN_PATTERN);

        LOG.info(matches);

        LOG.info("End Test.");

    }
}
