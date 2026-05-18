package com.learn;


import java.io.*;
import java.util.*;
import java.text.*;


public class App 
{
   public static void main( String[] args )
   {
      System.out.println( "Hello World!" );

      System.out.println("+++++++++++++++++++++++++++++++++++++++");
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      format.setTimeZone(TimeZone.getTimeZone("UTC"));
      String dateSyst = format.format(new Date());

      System.out.println("Syst date: " + dateSyst);

      String myDate = "2008-03-21 17:06:06";

      Date utcDate = null;

      try
      {
         utcDate = format.parse(dateSyst);
         System.out.println("UTC date: " + utcDate);
      }
      catch (Exception e) 
      {
         e.printStackTrace();
      }

      System.out.println("myDate: " + myDate);
      System.out.println("utcDate: " + utcDate);

      myDate = format.format(utcDate);
      System.out.println("myDate: " + myDate);


   }
}
