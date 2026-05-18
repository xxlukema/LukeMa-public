package com.learn.toslf4j;


import java.util.logging.Logger;

import org.junit.Test;

import com.learn.jul.util.LoggingApi;


public class LoggingBridgeSysPropertyTest {

    private static final org.apache.log4j.Logger LOG1 = org.apache.log4j.Logger.getLogger(LoggingBridgeSysPropertyTest.class);

    /**
     * 1. This line must be executed before Logger LOG = Logger.getLogger();
     */
    static {
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
    }

    /**
     * This line must be executed after System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
     */
    private static final Logger LOG = Logger.getLogger(LoggingBridgeSysPropertyTest.class.getName());

    @Test
    public void testLogCustomized()
        throws Exception {

        // MyLogConfig.config();

        LoggingApi.doLog(LOG);

        LOG1.debug("Log4j debug.");

    }

}
