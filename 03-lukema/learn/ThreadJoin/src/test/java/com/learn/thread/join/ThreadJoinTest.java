package com.learn.thread.join;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


public class ThreadJoinTest {
    private static final Logger LOG = LogManager.getLogger(ThreadJoinTest.class);

    @Test
    public void testJoin()
        throws Exception {
        LOG.info("Begin Test");

        Thread thread = new Thread(new MyRunnable2());
        thread.start();

        LOG.info("Waiting with join...");
        thread.join();

        LOG.info("End Test.");

    }

    @Test
    public void testNoJoin()
        throws Exception {
        LOG.info("Begin Test");

        Thread thread = new Thread(new MyRunnable2());
        thread.start();

        //LOG.info("Waiting with join...");
        //thread.join();

        LOG.info("End Test.");

    }
}
