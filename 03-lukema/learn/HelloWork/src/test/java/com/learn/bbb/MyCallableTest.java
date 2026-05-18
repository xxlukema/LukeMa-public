package com.learn.bbb;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;

import com.learn.MyCallable;


public class MyCallableTest {

    private static final Logger LOG = LogManager.getLogger();

    //@Ignore
    @Test
    public void testMyCallable() {

        LOG.info("Begin Test.");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        List<Future<String>> list = new ArrayList<Future<String>>();

        Callable<String> callable = new MyCallable(1000);
        for (int i = 0; i < 6; i++) {

            Future<String> future = executor.submit(callable);

            list.add(future);
        }

        for (Future<String> future : list) {
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                LOG.info(new Date() + "::" + future.get());
            } //catch (InterruptedException | ExecutionException e) {
            catch (Exception e) {
                LOG.error("Interrupted", e);
            }
        }

        executor.shutdown();

        LOG.info("End Test.");
    }

    @Test
    public void testFutureTask() {

        LOG.info("Begin Test.");

        MyCallable callable1 = new MyCallable(1000);
        MyCallable callable2 = new MyCallable(2000);

        FutureTask<String> futureTask1 = new FutureTask<String>(callable1);
        FutureTask<String> futureTask2 = new FutureTask<String>(callable2);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(futureTask1); // execute() returns void
        executor.submit(futureTask2); // submit() returns Future<?>

        while (true) {
            try {
                if (futureTask1.isDone() && futureTask2.isDone()) {
                    LOG.info("Done");

                    executor.shutdown();

                    break;
                }

                if (!futureTask1.isDone()) {
                    //wait indefinitely for future task to complete
                    LOG.info("FutureTask1 output=" + futureTask1.get());
                }

                LOG.info("Waiting for FutureTask2 to complete");
                String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
                if (s != null) {
                    LOG.info("FutureTask2 output=" + s);
                }
            } //catch (InterruptedException | ExecutionException | TimeoutException e) {
            catch (Exception e) {
                LOG.error("Interrupted: " + e.getClass().getName());
            }
        }

        LOG.info("End Test.");

    }

    @Test
    public void testFutureTaskStart() {

        LOG.info("Begin Test.");

        MyCallable callable = new MyCallable(1000);

        FutureTask<String> futureTask = new FutureTask<String>(callable);

        new Thread(futureTask).start();

        try {
            String result = futureTask.get();
            LOG.info("result = " + result);
        } // catch (InterruptedException | ExecutionException e) {
        catch (Exception e) {
            LOG.error("Exception", e);
        }

        LOG.info("End Test.");
    }
}
