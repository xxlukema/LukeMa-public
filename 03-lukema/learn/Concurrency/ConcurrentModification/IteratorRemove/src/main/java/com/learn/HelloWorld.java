package com.learn;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


public class HelloWorld
{
   protected static final Logger LOG  = Logger.getLogger(HelloWorld.class);

   private List<String>          LIST = new LinkedList<String>();

   @Test
   public void removeThroughIterator()
      throws Exception
   {
      LIST.add("One");
      LIST.add("Two");
      LIST.add("Three");
      LIST.add("Four");

      LOG.info("LIST size: " + LIST.size());

      for (Iterator<String> it = LIST.iterator(); it.hasNext();)
      {
         it.next();
         it.remove();
      }

      LOG.info("LIST size: " + LIST.size());

      Assert.assertEquals(0, LIST.size());
   }

   @Test(expected = ConcurrentModificationException.class)
   public void removeThroughLooping()
      throws Exception
   {
      LIST.add("One");
      LIST.add("Two");
      LIST.add("Three");
      LIST.add("Four");

      LOG.info("LIST size: " + LIST.size());

      for (String str : LIST)
      {
         LIST.remove(str);
      }

      LOG.info("LIST size: " + LIST.size());

      Assert.assertEquals(0, LIST.size());
   }

   @Test(expected = ConcurrentModificationException.class)
   public void removeThroughLoopingIndex()
      throws Exception
   {
      LIST.add("One");
      LIST.add("Two");
      LIST.add("Three");
      LIST.add("Four");

      LOG.info("LIST size: " + LIST.size());

      for (int i = 0; i < LIST.size(); i++)
      {
         LIST.remove(i);
      }

      LOG.info("LIST size: " + LIST.size());

      Assert.assertEquals(0, LIST.size());
   }
}
