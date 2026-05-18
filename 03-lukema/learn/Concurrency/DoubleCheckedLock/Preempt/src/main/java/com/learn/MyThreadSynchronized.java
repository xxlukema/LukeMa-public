package com.learn;


public class MyThreadSynchronized
    extends Thread
{
    private CriticalCode criticalCode;

    public MyThreadSynchronized(CriticalCode criticalCode)
    {
        this.criticalCode = criticalCode;
    }

    @Override
    public void run()
    {
        criticalCode.enterCriticalMethodSynchronized(this);
    }
}
