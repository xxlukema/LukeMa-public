package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);


   public static void main(String [] args)
   {
      LOG.info("Hello World!");

      OptionType U = OptionType.UP_AND_OUT;
      OptionType D = OptionType.DOWN_AND_OUT;

      LOG.info("U: " + U);
      LOG.info("D: " + D);
      LOG.info("D: " + D.getDesc());
      LOG.info("D: " + D.getValue());

      LOG.info("U: " + isUpAndOut(U));
      LOG.info("D: " + isUpAndOut(D));
      
      OptionType optionType = OptionType.valueOf("DOWN_AND_OUT");
      LOG.info("optionType: " + optionType);

   }

   public static boolean isUpAndOut(OptionType ot)
   {
      if (ot == OptionType.UP_AND_OUT)
      {
         return true;
      }

      return false;
   }
}

