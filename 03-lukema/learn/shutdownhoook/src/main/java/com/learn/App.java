package com.learn;

/**
 * Hello world!
 *
 */
public class App 
{
   public static void main( String[] args )
   {
      System.out.println("Hello World!" );
      Runtime.getRuntime().addShutdownHook(new ShutdownHook());
      try
      {
         Thread.sleep(2*1000);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      System.out.println("Exit");

      System.exit(1);
   }
}
