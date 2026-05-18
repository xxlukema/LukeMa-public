package com.learn.log4j2;


import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


public class Log4j2Test {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testLog()
        throws Exception {

        LOG.info("Begin Test.");

        LOG.debug("End Test debug.");

        Date date = null;
        LOG.info("date1: ", date);

        date = new Date();
        LOG.info("date2: ", date);

        LOG.info("End Test.");

    }
}
