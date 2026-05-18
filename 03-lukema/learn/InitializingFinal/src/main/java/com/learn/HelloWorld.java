package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG   = Logger.getLogger(HelloWorld.class);

   // Wrong. Can not re-asign it in constructor.
   private final String        name  = null;

   // Right. Init it in constructor.
   private final String        address;

   // Right. 
   private final String        alias = "Alias";

   public HelloWorld()
   {
      name = "Hello Again";

      address = "My Address";
   }

   public String getAlias()
   {
      return alias;
   }

   public String getName()
   {
      return name;
   }

   public String getAddress()
   {
      return address;
   }

   public static void main(String[] args)
   {
      LOG.info("Hello World!");
   }
}
