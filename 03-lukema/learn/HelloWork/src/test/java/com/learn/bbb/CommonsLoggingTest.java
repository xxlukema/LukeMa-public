package com.learn.bbb;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;


public class CommonsLoggingTest {

    private Log log = LogFactory.getLog(CommonsLoggingTest.class);

    @Test
    public void runTest()
        throws Exception {
        log.info("Begin Test.");

        log.info("End Test.");
    }

}
