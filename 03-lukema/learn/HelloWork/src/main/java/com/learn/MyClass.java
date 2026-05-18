package com.learn;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class MyClass {

    private static final Logger LOG = LogManager.getLogger();

    private static int myInt = 100;

    static {
        myInt = 234;
        LOG.info("Static block called.");
    }

    public static int getMyInt() {
        return myInt;
    }

}
