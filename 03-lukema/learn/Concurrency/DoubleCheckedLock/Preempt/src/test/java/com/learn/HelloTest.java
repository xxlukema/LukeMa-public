package com.learn;


import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;


public class HelloTest
{
    protected static final Logger LOG = Logger.getLogger(HelloTest.class);

    @Test
    public void runTestReentrantLock()
        throws Exception
    {
        LOG.info("Hello World!");

        CriticalCode criticalCode = new CriticalCode();

        Thread thread1 = new MyThreadReentrantLock(criticalCode);
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread1.setName("Low Priority Thread");

        Thread thread2 = new MyThreadReentrantLock(criticalCode);
        thread2.setPriority(Thread.MAX_PRIORITY);
        thread2.setName("Hight Priority Thread");

        thread1.start();

        try
        {
            Thread.sleep(1000);
        }
        catch (Throwable t)
        {
            LOG.error("Sleep Exception:", t);
        }

        thread2.start();

        thread1.join();
        thread2.join();

        LOG.info("Completed!");
    }

    @Ignore
    @Test
    public void runTestSynchronized()
        throws Exception
    {
        LOG.info("Hello World!");

        CriticalCode criticalCode = new CriticalCode();

        Thread thread1 = new MyThreadSynchronized(criticalCode);
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread1.setName("Low Priority Thread");

        Thread thread2 = new MyThreadSynchronized(criticalCode);
        thread2.setPriority(Thread.MAX_PRIORITY);
        thread2.setName("Hight Priority Thread");

        thread1.start();

        try
        {
            Thread.sleep(1000);
        }
        catch (Throwable t)
        {
            LOG.error("Sleep Exception:", t);
        }

        thread2.start();

        thread1.join();
        thread2.join();

        LOG.info("Completed!");
    }
}
