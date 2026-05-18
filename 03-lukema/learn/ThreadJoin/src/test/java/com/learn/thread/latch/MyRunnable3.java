package com.learn.thread.latch;


import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MyRunnable3
    implements Runnable {

    private static final Logger LOG = LogManager.getLogger(MyRunnable3.class);

    private final CountDownLatch countDownLatch;

    public MyRunnable3(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        LOG.info("Thread started. Sleeping for 5 seconds...");

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            LOG.info("InterruptedException", e);
        }

        countDownLatch.countDown();

        LOG.info("Thread completed.");
    }

}
