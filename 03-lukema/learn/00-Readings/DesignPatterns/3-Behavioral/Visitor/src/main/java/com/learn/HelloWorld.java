package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
   {
      LOG.info("Hello World!");
      
      ToBeVisited toBeVisited = new LukeMa();
      
      Visitor friend = new MyFriend();
      friend.visit(toBeVisited);
      
      Visitor coworker = new MyCoworker();
      coworker.visit(toBeVisited);
   }
}
