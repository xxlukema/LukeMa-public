package com.learn;


public class MyThreadReentrantLock
    extends Thread
{
    private CriticalCode criticalCode;

    public MyThreadReentrantLock(CriticalCode criticalCode)
    {
        this.criticalCode = criticalCode;
    }

    @Override
    public void run()
    {
        criticalCode.enterCriticalMethodReentrantLock(this);
    }
}
