package com.learn.concurrency;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class FutureSquareCalculatorTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testIsDone()
        throws InterruptedException, ExecutionException {

        Future<Integer> future = new FutureSquareCalculator().calculate(10);

        while (!future.isDone()) {
            LOG.info("Calculating...");
            Thread.sleep(300);
        }

        Integer result = future.get();

        LOG.info("result = " + result);
    }

    @Test
    public void testTwo()
        throws InterruptedException, ExecutionException {

        FutureSquareCalculator squareCalculator = new FutureSquareCalculator();

        Future<Integer> future1 = squareCalculator.calculate(10);
        Future<Integer> future2 = squareCalculator.calculate(100);

        while (!(future1.isDone() && future2.isDone())) {
            LOG.info(String.format("future1 is %s and future2 is %s", future1.isDone() ? "done" : "not done", future2.isDone() ? "done" : "not done"));
            Thread.sleep(300);
        }

        Integer result1 = future1.get();
        Integer result2 = future2.get();

        LOG.info(String.format("future1 is %s and future2 is %s", future1.isDone() ? "done" : "not done", future2.isDone() ? "done" : "not done"));
        
        LOG.info(result1 + " and " + result2);

        squareCalculator.shutdown();

    }
}
