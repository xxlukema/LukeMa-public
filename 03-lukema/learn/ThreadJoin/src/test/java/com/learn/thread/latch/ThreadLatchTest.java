package com.learn.thread.latch;


import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


public class ThreadLatchTest {
    private static final Logger LOG = LogManager.getLogger(ThreadLatchTest.class);

    @Test
    public void testLatch()
        throws Exception {
        LOG.info("Begin Test");

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread thread = new Thread(new MyRunnable3(countDownLatch));
        thread.start();

        LOG.info("Waiting with latch...");
        countDownLatch.await();

        LOG.info("End Test.");

    }

}
