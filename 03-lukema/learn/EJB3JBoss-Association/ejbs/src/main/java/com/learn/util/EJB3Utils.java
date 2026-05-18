package com.learn.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;


public class EJB3Utils
{
   public static final SimpleDateFormat SDF    = new SimpleDateFormat("-MMMdd:hha:mm.");

   public static final AtomicInteger    NUMBER = new AtomicInteger();

   public static String newString()
   {
      return SDF.format(Calendar.getInstance().getTime()) + NUMBER.getAndIncrement();
   }

}
