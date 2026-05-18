package com.learn;


import org.apache.log4j.Logger;


/**
 * Hello world!
 *
 */
public class LogDriver {
    private static final Logger LOGGER = Logger.getLogger(LogDriver.class);

    public static void main(String[] args) {
        LOGGER.info("Begin test.");

        Thread t0 = new Thread(new LogThread());
        Thread t1 = new Thread(new LogThread());

        t0.start();
        t1.start();

        LOGGER.info("End test.");
    }
}
