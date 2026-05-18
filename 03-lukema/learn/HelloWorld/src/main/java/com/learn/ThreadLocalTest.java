package com.learn;


import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;


public class ThreadLocalTest {

    private static final Logger LOG = Logger.getLogger(ThreadLocalTest.class);

    protected ThreadLocal<AtomicInteger> id = new ThreadLocal<AtomicInteger>() {
        @Override
        protected AtomicInteger initialValue() {
            return new AtomicInteger(0);
        }
    };

    @Test(invocationCount = 6, threadPoolSize = 3)
    public void test() {

        LOG.info("Begin Test.");

        //LOG.debug(Thread.currentThread().getName() + "-" + Thread.currentThread().getId() + " " + id.get());

        if (Thread.currentThread().getId() == 12) {
            id.get().incrementAndGet();
        }

        LOG.debug(Thread.currentThread().getName() + "-" + Thread.currentThread().getId() + " " + id.get());

        LOG.info("End Test.");
    }

}
