package com.learn.bbb;


import java.util.Arrays;

import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


@Named
public class StringSplit {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        String str = "Hello, World,again.";

        int len = Math.min(str.length(), 200);
        LOG.info(str.substring(0, len));

        LOG.info(str.substring(0, 5));

        LOG.info(Arrays.deepToString(str.split("[, ]")));

        LOG.info("End Test.");

    }
}
