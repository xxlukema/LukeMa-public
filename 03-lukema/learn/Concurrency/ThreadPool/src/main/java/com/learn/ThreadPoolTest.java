package com.learn;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;


public class ThreadPoolTest
{
   protected static final Logger LOG                = Logger.getLogger(ThreadPoolTest.class);

   private ThreadPoolExecutor    threadPoolExecutor = new ThreadPoolExecutor(2, 3, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Hello World!");

      ThreadPoolTest singleThreadAccess = new ThreadPoolTest();

      MyThread myThread = new MyThread();

      // singleThreadAccess.invokeAndWait(myThread);
      singleThreadAccess.invokeLater(myThread);

      // singleThreadAccess.invokeAndWait(myThread);
      singleThreadAccess.invokeLater(myThread);

      // singleThreadAccess.invokeAndWait(myThread);
      singleThreadAccess.invokeLater(myThread);

      // singleThreadAccess.invokeAndWait(myThread);
      singleThreadAccess.invokeLater(new MyOtherThread());

      singleThreadAccess.shutdown();

   }

   public void invokeLater(Runnable r)
   {
      LOG.info("invokeLater");

      threadPoolExecutor.execute(r);

      LOG.info("Leave invokeLater");
   }

   public void invokeAndWait(Runnable r)
      throws InterruptedException, ExecutionException
   {
      LOG.info("invokeAndWait");

      FutureTask<Object> task = new FutureTask<Object>(r, null);
      threadPoolExecutor.execute(task);
      task.get();

      LOG.info("Leave invokeAndWait");
   }

   public void shutdown()
   {
      threadPoolExecutor.shutdown();
   }
}
