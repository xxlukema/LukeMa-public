package com.learn;


import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.junit.Test;


@Named
public class BapTest {
    private static final Logger LOG = Logger.getLogger(BapTest.class);

    @Inject
    SpringBean springBean;

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test.");

        // LOG.info("springBean.getMessage() = " + springBean.getMessage());

        Properties properties = System.getProperties();

        LOG.info("properties: " + properties);

        LOG.info("user.name: " + properties.getProperty("user.name"));

        double a = 3 + 2 / 5 + 2;

        LOG.info(a);

        LOG.info("End Test.");
    }
}
