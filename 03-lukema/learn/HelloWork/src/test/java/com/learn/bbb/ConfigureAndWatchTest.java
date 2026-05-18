package com.learn.bbb;


import org.apache.log4j.xml.DOMConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ConfigureAndWatchTest {
    private static final Logger LOG = LogManager.getLogger();

    public static void main(String[] args)
        throws InterruptedException {

        LOG.info("Begin Test.");

        /**
         * configureAndWatch() watches files. Not resources in the classpath.
         */
        DOMConfigurator.configureAndWatch("target/classes/log4j.xml", 5000);
        //DOMConfigurator.configureAndWatch("/log4j.xml", 5000);

        for (int i = 0; i < 1000000; i++) {
            Thread.sleep(2000);

            LOG.info("info");
            LOG.debug("debug");
        }

        LOG.info("End Test.");
    }
}
