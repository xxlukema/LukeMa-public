package com.learn;


import java.io.Serializable;


public class Printer
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   public static void prin(String arg)
   {
      System.out.println("############### arg: " + arg);
   }
}
