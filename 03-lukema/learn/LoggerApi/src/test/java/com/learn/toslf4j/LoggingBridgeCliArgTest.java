package com.learn.toslf4j;


import java.util.logging.Logger;

import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.learn.jul.util.LoggingApi;
import com.learn.jul.util.MyJulLogConfigReader;


public class LoggingBridgeCliArgTest {

    private static final Logger LOG = Logger.getLogger(LoggingBridgeCliArgTest.class.getName());

    private static final org.apache.log4j.Logger LOG1 = org.apache.log4j.Logger.getLogger(LoggingBridgeCliArgTest.class);

    @Test
    public void testLogCustomized()
        throws Exception {

        MyJulLogConfigReader.readConfig();
        LoggingApi.doLog(LOG);

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        LoggingApi.doLog(LOG);

        MyJulLogConfigReader.readConfig();
        LoggingApi.doLog(LOG);

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        LoggingApi.doLog(LOG);

        MyJulLogConfigReader.readConfig();
        LoggingApi.doLog(LOG);

        LOG1.debug("Log4j debug.");

    }

}
