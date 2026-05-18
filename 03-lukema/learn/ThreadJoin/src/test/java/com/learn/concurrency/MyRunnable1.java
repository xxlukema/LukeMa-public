package com.learn.concurrency;


import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MyRunnable1
    implements Runnable {

    private static final Logger LOG = LogManager.getLogger(MyRunnable1.class);

    private final CountDownLatch countDownLatch;

    private int id = 0;

    public MyRunnable1(int id, CountDownLatch countDownLatch) {
        this.id = id;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        LOG.info(id + " started.");

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            LOG.error("InterruptedException", e);
        }

        countDownLatch.countDown();

        LOG.info(id + " completed.");
    }

}
