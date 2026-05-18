package com.learn.bbb;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class HelloEnv {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        // getenv gets an environment variable
        String env = System.getenv("my_env");
        LOG.info("my_env = " + env);

        String home = System.getenv("HOME");
        LOG.info("HOME = " + home);
        
        //  System.getProperty() is for JVM arguments which are passed as -DpropName=value
        String prop = System.getProperty("my_prop");
        LOG.info("my_prop = " + prop);

        LOG.info("End Test.");

    }
}
