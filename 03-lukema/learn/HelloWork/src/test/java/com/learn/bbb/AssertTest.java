package com.learn.bbb;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class AssertTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void test()
        throws Exception {
        LOG.info("Begin Test.");

        // assert true;

        // assert false : "It is true";
        
        LOG.info(AssertTest.class.getName());
        LOG.info(AssertTest.class.getSimpleName());
        LOG.info(AssertTest.class.getCanonicalName());
        LOG.info(AssertTest.class.getTypeName());

        LOG.info("End Test.");

    }
}
