package com.learn.toslf4j;


import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.learn.jul.util.JulConfigReader;
import com.learn.jul.util.LoggingApi;
import com.learn.jul.util.MyJulLogConfigReader;


public class LoggingBridgeRollingTest {

    private static final Logger LOG = Logger.getLogger(LoggingBridgeRollingTest.class.getName());

    private static final org.apache.log4j.Logger LOG1 = org.apache.log4j.Logger.getLogger(LoggingBridgeRollingTest.class);

    @BeforeClass
    public static void beforeClass() {
        JulConfigReader.readConfig();
    }

    @Ignore
    @Test
    public void testLogDefault()
        throws Exception {

        LoggingApi.doLog(LOG);

        LOG1.debug("Log4j debug.");

    }

    @Ignore
    @Test
    public void testLogCustomizedOverrideBefore()
        throws Exception {

        MyJulLogConfigReader.readConfig();

        LoggingApi.doLog(LOG);

        LOG1.debug("Log4j debug.");

    }

    @Test
    public void testLogCustomizedTakeBackToSLF4J()
        throws Exception {

        LoggingApi.doLog(LOG);

        LOG1.debug("Log4j debug.");

    }
}
