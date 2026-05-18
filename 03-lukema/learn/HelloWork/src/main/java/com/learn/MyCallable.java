package com.learn;


import java.util.concurrent.Callable;


public class MyCallable
    implements Callable<String> {

    private long waitTime;

    private volatile int counter;

    public MyCallable(int timeInMillis) {
        this.waitTime = timeInMillis;
    }

    @Override
    public String call()
        throws Exception {

        counter++;

        Thread.sleep(waitTime);

        return Thread.currentThread().getName() + ": " + counter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
