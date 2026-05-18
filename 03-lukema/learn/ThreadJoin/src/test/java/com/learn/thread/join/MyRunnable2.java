package com.learn.thread.join;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MyRunnable2
    implements Runnable {

    private static final Logger LOG = LogManager.getLogger(MyRunnable2.class);

    @Override
    public void run() {
        LOG.info("Thread started. Sleeping for 5 seconds...");

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            LOG.info("InterruptedException", e);
        }

        LOG.info("Thread completed.");
    }

}
