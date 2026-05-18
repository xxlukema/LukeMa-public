package com.learn;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;


public class HelloWorld
{
   protected static final Logger LOG   = Logger.getLogger(HelloWorld.class);

   private static String[]       WORDS = { "Hello, World!", "Test", "A", "BB", "CCC" };

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Hello World!");

      ExecutorService executorService = Executors.newFixedThreadPool(3);

      List<Future<WordLength>> list = new LinkedList<Future<WordLength>>();
      for (String word : WORDS)
      {
         Callable<WordLength> callable = new WordLengthCallable(word);
         Future<WordLength> future = executorService.submit(callable);
         list.add(future);
      }

      for (Future<WordLength> future : list)
      {
         WordLength wordLength = future.get();
         LOG.info(wordLength.getWord() + " \t" + wordLength.getLength());
      }

      executorService.shutdown();
   }
}
