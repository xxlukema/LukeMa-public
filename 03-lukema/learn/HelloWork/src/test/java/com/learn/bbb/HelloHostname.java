package com.learn.bbb;


import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class HelloHostname {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        String hostname = InetAddress.getLocalHost().getHostName();
        String canonicalHostName = InetAddress.getLocalHost().getCanonicalHostName();

        LOG.info("hostname = " + hostname);
        LOG.info("canonicalHostName = " + canonicalHostName);

        LOG.info("End Test.");

    }
}
