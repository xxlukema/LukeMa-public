package com.learn.concurrency;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class ExecutorTest {

    private static final Logger LOG = LogManager.getLogger();

    private BlockingQueue<Runnable> workerQueue = new LinkedBlockingQueue<>(14);

    private int corePoolSize = 2;
    private int maximumPoolSize = 2;
    private long keepAliveTime = 300;

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workerQueue);

    @Test
    public void testScheduler()
        throws InterruptedException {
        LOG.info("Begin Test");

        int tasks = 20;

        final CountDownLatch countDownLatch = new CountDownLatch(tasks);

        for (int i = 0; i < tasks; i++) {

            Thread.sleep(900);

            MyRunnable myRunnable = new MyRunnable(i, countDownLatch);

            threadPoolExecutor.submit(myRunnable);

            LOG.info(i + " is submitted.");

        }

        LOG.info("End loop. Waiting on latch...");

        /**
         * Not wait(). It's await().
         */
        // countDownLatch.wait(); 
        countDownLatch.await();

        LOG.info("End Test");
    }

}
