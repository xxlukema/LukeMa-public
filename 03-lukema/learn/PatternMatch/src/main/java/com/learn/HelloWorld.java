package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Hello World!");

      /*Pattern pattern = Pattern.compile(".*(K.*):.*( .*u).*");
      Matcher matcher = pattern.matcher("The Key:The value");
      if (matcher.matches())
      {
         System.out.print("Key:   ");
         System.out.println(matcher.group(1));
         System.out.print("Value: ");
         System.out.println(matcher.group(2));
      }
      else
      {
         System.out.print("No match");
      }
      */

      String line = "3.";

        String lineNumber = StringUtils.findLineNumber(line);

      LOG.info("value = " + lineNumber);

   }
}
