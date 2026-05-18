package com.learn.future;


import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


public class MyCallableTest {

    private static final Logger LOG = LogManager.getLogger(MyCallableTest.class);

    @Test
    public void testGet()
        throws Exception {
        LOG.info("Begin Test");

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Future<String> future = executor.submit(new MyCallable());

        LOG.info("Waiting with future.get()...");
        future.get();

        LOG.info("End Test.");

    }

}
