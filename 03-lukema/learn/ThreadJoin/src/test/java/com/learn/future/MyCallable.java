package com.learn.future;


import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MyCallable
    implements Callable<String> {

    private static final Logger LOG = LogManager.getLogger(MyCallable.class);

    @Override
    public String call()
        throws Exception {

        LOG.info("Thread started. Sleeping for 5 seconds...");

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            LOG.info("InterruptedException", e);
        }

        LOG.info("Thread completed.");

        return "Hello Future!";
    }

}
