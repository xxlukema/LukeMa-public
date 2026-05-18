package com.learn.usaa;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class HelloTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        String fileName = "initName.txt";

        LOG.info(fileName);

        LOG.info("End Test.");

    }
}
