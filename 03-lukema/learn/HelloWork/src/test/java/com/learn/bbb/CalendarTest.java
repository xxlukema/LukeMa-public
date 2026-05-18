package com.learn.bbb;


import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;


public class CalendarTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest() {

        CalendarTest ct = new CalendarTest();

        Thread t1 = ct.new MyThread();
        Thread t2 = ct.new MyChanger();

        t1.setDaemon(false);
        t2.setDaemon(false);

        t1.start();
        t2.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            LOG.error("InterruptedException", e);
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            LOG.error("InterruptedException", e);
        }
    }

    class MyThread
        extends Thread {

        @Override
        public void run() {

            for (int i = 0; i < 5; i++) {
                LOG.info(Thread.currentThread().threadId() + ": " + new Date());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    LOG.error("InterruptedException", e);
                }
            }
        }
    }

    class MyChanger
        extends Thread {

        @Override
        public void run() {

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                LOG.error("InterruptedException", e);
            }

            LOG.info(Thread.currentThread().threadId() + ": Setting time");

            Calendar c1 = Calendar.getInstance();
            c1.set(Calendar.YEAR, 1800);
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.MONTH, 8);
            Calendar c3 = Calendar.getInstance();
            c3.setTimeInMillis(System.currentTimeMillis() - 24 * 3600 * 1000);

            LOG.info(Thread.currentThread().threadId() + ": Time set: " + new Date());
            //LOG.info(Thread.currentThread().threadId() + ": Time set: " + new Date(Calendar.getInstance().getTimeInMillis()));
            //LOG.info(Thread.currentThread().threadId() + ": Time set: " + new Date(System.currentTimeMillis()));
            LOG.info(Thread.currentThread().threadId() + ": Time set c1: " + c1.getTime());
            LOG.info(Thread.currentThread().threadId() + ": Time set c2: " + c2.getTime());
            LOG.info(Thread.currentThread().threadId() + ": Time set c3: " + c3.getTime());
            LOG.info(Thread.currentThread().threadId() + ": Time set: " + Calendar.getInstance().getTime());

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                LOG.error("InterruptedException", e);
            }

            LOG.info(Thread.currentThread().threadId() + ": Time set c1: " + c1.getTime());
            LOG.info(Thread.currentThread().threadId() + ": Time set c2: " + c2.getTime());

            LOG.info(Thread.currentThread().threadId() + ": c1: " + c1);
            LOG.info(Thread.currentThread().threadId() + ": c2: " + c2);

            LOG.info(Thread.currentThread().threadId() + ": " + Calendar.getInstance());
            LOG.info(Thread.currentThread().threadId() + ": " + Calendar.getInstance());
        }
    }

}
