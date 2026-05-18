package com.learn;


import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


public class HelloWorld
{
   protected static final Logger LOG  = Logger.getLogger(HelloWorld.class);

   private List<Integer>         LIST = Collections.synchronizedList(new LinkedList<Integer>());

   @Test
   public void use()
      throws Exception
   {
      LIST.add(0);
      LIST.add(1);
      LIST.add(2);
      LIST.add(3);

      LOG.info("list size: " + LIST.size());

      synchronized (LIST)
      {
         for (Iterator<Integer> it = LIST.iterator(); it.hasNext();)
         {
            it.next();
            it.remove();
         }
      }

      LOG.info("list size: " + LIST.size());

      Assert.assertEquals(0, LIST.size());
   }

}
