package com.learn.bbb;


import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;


public class MyPropertyModuleTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void test()
        throws Exception {
        LOG.info("Begin Test.");

        Properties properties = new Properties();

        properties.load(MyPropertyModuleTest.class.getResourceAsStream("/module.properties"));

        LOG.info(properties.getProperty("prop1"));
        LOG.info(properties.getProperty("prop2"));

        LOG.info(System.getProperty("address_change_request_url"));
        LOG.info(System.getProperty("prop2"));

        LOG.info(System.getProperty("prop4"));

        LOG.info("End Test.");

    }

}
