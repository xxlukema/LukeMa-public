package com.learn.bbb;


import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ThreadLocalTest {

    private static final Logger LOG = LogManager.getLogger();

    protected ThreadLocal<AtomicInteger> id = new ThreadLocal<AtomicInteger>() {
        @Override
        protected AtomicInteger initialValue() {
            return new AtomicInteger(0);
        }
    };

    // @Test(invocationCount = 6, threadPoolSize = 3)
    public void test() {

        LOG.info("Begin Test.");

        //LOG.debug(Thread.currentThread().getName() + "-" + Thread.currentThread().threadId() + " " + id.get());

        if (Thread.currentThread().threadId() == 12) {
            id.get().incrementAndGet();
        }

        LOG.debug(Thread.currentThread().getName() + "-" + Thread.currentThread().threadId() + " " + id.get());

        LOG.info("End Test.");
    }

}
