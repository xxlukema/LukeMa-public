package com.learn.bbb;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;


public class ReplaceAllTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void test()
        throws Exception {
        LOG.info("Begin Test.");

        //LOG.info("Hello World!");

        String str = " This  is     a  broken        \n" + "line";

        str = str.replaceAll("[\\t\\n\\x0B\\f\\r]", "");
        LOG.info(str);

        str = str.replaceAll(" {2,}+", " ");
        str = str.trim();

        LOG.info(str);

        LOG.info("End Test.");

    }
}
