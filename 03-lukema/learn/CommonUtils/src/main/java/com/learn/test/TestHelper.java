package com.learn.test;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.AssertionFailedError;


/**
 * This TestUtils is good for any projects. Not limited to SLB projects.
 */
public class TestHelper
{
   public static final String            USER_NAME           = System.getProperty("user.name", "unknown-user");

   public static final int               DEFAULT_MAX_STR_LEN = 50;

   private static final AtomicInteger    COUNTER             = new AtomicInteger();

   private static final long             MIN_PHONE           = 10000000000L;

   private static final SimpleDateFormat SDF                 = new SimpleDateFormat("yyyyMMddahh:mm-");

   private static final NumberFormat     NF                  = new DecimalFormat("000");

   private static final String           PREFIX              = USER_NAME.substring(0, 2);

   public static String getPREFIX()
   {
      return PREFIX;
   }

   public static String newString(String prefix)
   {
      return newString(prefix, DEFAULT_MAX_STR_LEN);
   }

   public static String newString(int maxLength)
   {
      return newString(null, maxLength);
   }

   public static String getCounter()
   {
      return NF.format(COUNTER.getAndIncrement());
   }

   public static String newString(String prefix, int maxLength)
   {
      Date now = Calendar.getInstance().getTime();
      String dateString = SDF.format(now);
      String str = dateString + getCounter();

      return formatString(str, prefix, maxLength);
   }

   private static String formatString(String str, String prefix, int maxLength)
   {
      if (prefix == null)
      {
         prefix = "";
      }
      else
      {
         prefix += "-";
      }

      String rtn = null;

      if (prefix.length() > maxLength)
      {
         rtn = prefix.substring(0, maxLength);
      }
      else
      {
         int maxStrLen = maxLength - prefix.length();
         if (str.length() > maxStrLen)
         {
            str = str.substring(str.length() - maxStrLen);
         }

         if (str.indexOf('-') == 0)
         {
            str = str.substring(1);
         }

         rtn = prefix + str;
      }

      return rtn;
   }

   public static String newPhoneNumber()
   {
      long num = randomLong() % MIN_PHONE;

      if (num * 10 < MIN_PHONE)
      {
         return newPhoneNumber();
      }

      String phoneNumber = Long.toString(num);

      return phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6);
   }

   private static Random newRandom()
   {
      return new Random(System.currentTimeMillis() + COUNTER.getAndIncrement());
   }

   public static int randomInt(int bound)
   {
      return Math.abs(newRandom().nextInt(bound));
   }

   public static int randomInt()
   {
      return Math.abs(newRandom().nextInt());
   }

   public static long randomLong()
   {
      return Math.abs(newRandom().nextLong());
   }

   public static double randomDouble()
   {
      return Math.abs(newRandom().nextDouble());
   }

   public static float randomFloat()
   {
      return Math.abs(newRandom().nextFloat());
   }

   public static void sleepRandom()
   {
      final int maxSleepMilliSec = 50;

      Random random = newRandom();
      int sleepMillisec = Math.abs(random.nextInt(maxSleepMilliSec));

      try
      {
         Thread.sleep(sleepMillisec + 1);
      }
      catch (InterruptedException e)
      {
      }
   }

   public static GregorianCalendar newFutureGregorianCalendar()
   {
      long futureTimeInMillis = System.currentTimeMillis() + randomInt();

      GregorianCalendar gregorianCalendar = new GregorianCalendar();
      gregorianCalendar.setTimeInMillis(futureTimeInMillis);

      return gregorianCalendar;
   }

   public static GregorianCalendar newFutureGregorianCalendarAfter(Calendar calendar)
   {
      long futureTimeInMillis = calendar.getTimeInMillis() + 10 + randomInt();

      GregorianCalendar newGregorianCalendar = new GregorianCalendar();
      newGregorianCalendar.setTimeInMillis(futureTimeInMillis);

      return newGregorianCalendar;
   }

   public static Date newFutureDate()
   {
      return newFutureGregorianCalendar().getTime();
   }

   public static Date newFutureDateAfter(Date date)
   {
      long futureTimeInMillis = date.getTime() + randomInt();

      GregorianCalendar gregorianCalendar = new GregorianCalendar();
      gregorianCalendar.setTimeInMillis(futureTimeInMillis);

      return gregorianCalendar.getTime();
   }

   public static <T> T randomArrayElement(T[] tArray)
   {
      if (tArray == null || tArray.length == 0)
      {
         return null;
      }

      return tArray[randomInt(tArray.length)];
   }

   public static <T> T randomCollectionElement(Collection<T> collection)
   {
      if (collection == null || collection.size() == 0)
      {
         return null;
      }

      int index = randomInt(collection.size());
      int i = 0;
      for (T t : collection)
      {
         if (i == index)
         {
            return t;
         }
         i++;
      }

      return null;
   }

   public static void println(String str)
   {
      System.out.println("###### " + str);
   }

   public static void preAssertEquals(Object firstParameter, Object secondParameter)
      throws Exception
   {
      if (firstParameter == null)
      {
         throw new AssertionFailedError("firstParameter is null.");
      }

      if (secondParameter == null)
      {
         throw new AssertionFailedError("secondParameter is null.");
      }

      if (firstParameter == secondParameter)
      {
         return;
      }
   }
}
