package com.learn;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;


public class CriticalCode
{
    protected static final Logger LOG  = Logger.getLogger(CriticalCode.class);

    protected static Lock         LOCK = new ReentrantLock();

    public void enterCriticalMethodReentrantLock(Thread thread)
    {
        LOG.info("Entered enterCriticalMethod()..." + threadInfo(thread));

        LOCK.lock();
        try
        {
            LOG.info("Got lock. Before sleep..." + threadInfo(thread));

            sleep(thread);

            LOG.info("After sleep." + threadInfo(thread));
        }
        finally
        {
            LOG.info("Releasing lock. After sleep..." + threadInfo(thread));
            LOCK.unlock();
        }

        LOG.info("Leave enterCriticalMethod()." + threadInfo(thread));
    }

    public void enterCriticalMethodSynchronized(Thread thread)
    {
        LOG.info("Entered enterCriticalMethod()..." + threadInfo(thread));

        synchronized (CriticalCode.class)
        {
            LOG.info("Got lock. Before sleep..." + threadInfo(thread));

            sleep(thread);

            LOG.info("After sleep." + threadInfo(thread));
            LOG.info("Releasing lock. After sleep..." + threadInfo(thread));
        }

        LOG.info("Leave enterCriticalMethod()." + threadInfo(thread));
    }

    private String threadInfo(Thread thread)
    {
        return " Thread name: " + thread.getName() + ". priority: " + thread.getPriority();
    }

    private void sleep(Thread thread)
    {
        for (int i = 0; i < 10; i++)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (Throwable t)
            {
                LOG.error("Sleep Exception:" + threadInfo(thread), t);
            }
        }
    }
}
