package com.learn.bbb;


import java.net.URL;
import java.net.URLClassLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class PrintClassPathTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testMain()
        throws Exception {

        LOG.info("Begin Test.");

        // ClassLoader cl = ClassLoader.getSystemClassLoader();
        ClassLoader cl = getClass().getClassLoader();

        URL[] urls = ((URLClassLoader) cl).getURLs();

        for (URL url : urls) {
            System.out.println(url.getFile());
        }

        LOG.info("End Test.");

    }
}
