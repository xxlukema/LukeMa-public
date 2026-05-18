package com.learn;


import java.util.concurrent.Callable;

import org.apache.log4j.Logger;


public class WordLengthCallable
   implements Callable<WordLength>
{
   protected static final Logger LOG             = Logger.getLogger(WordLengthCallable.class);

   private static int            InstanceCounter = 0;

   private int                   id;

   private String                word;

   public WordLengthCallable(String word)
   {
      id = InstanceCounter++;

      this.word = word;
   }

   public WordLength call()
      throws Exception
   {
      LOG.info(id + ": started. Word: " + word);

      try
      {
         WordLength wordLength = new WordLength();
         wordLength.setWord(word);
         wordLength.setLength(word.length());

         return wordLength;
      }
      finally
      {
         LOG.info(id + ": completed.");
      }
   }

}
