package com.learn.core.trade;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class TodayOHLLast
{
   private static String date   = null;

   private static String open   = null;

   private static String high   = null;

   private static String low    = null;

   private static String last   = null;

   private static String volume = null;

   public static String getDate()
   {
      return date;
   }

   public static String getOpen()
   {
      return open;
   }

   public static String getHigh()
   {
      return high;
   }

   public static String getLow()
   {
      return low;
   }

   public static String getLast()
   {
      return last;
   }

   public static String getVolume()
   {
      return volume;
   }

   public static void setDate(String dateStr)
   {
      date = dateStr;
   }

   public static void retrieveData(String symbol)
   {
      retrieveOHLC(symbol);
      retrieveLast(symbol);
   }

   public static String getQuoteLine(String urlStr, String searchForStr)
   {
      String line = "";

      InputStream is = null;
      InputStreamReader isr = null;
      BufferedReader br = null;

      try
      {
         URL url = new URL(urlStr);
         URLConnection urlConn = url.openConnection();

         is = urlConn.getInputStream();
         isr = new InputStreamReader(is);
         br = new BufferedReader(isr);

         String newLine = null;
         while ((newLine = br.readLine()) != null)
         {
            line += newLine;
         }
      }
      catch (Throwable t)
      {
         t.printStackTrace();

         System.exit(1);
      }
      finally
      {
         try
         {
            is.close();
            isr.close();
            br.close();
         }
         catch (Throwable t)
         {
            t.printStackTrace();
         }
      }

      return line;
   }

   public static String getValue(String line, String beginStr, String endStr)
   {
      if (line == null)
      {
         return null;
      }

      int beginIndex = line.indexOf(beginStr) + beginStr.length();
      int endIndex = line.indexOf(endStr, beginIndex);
      return line.substring(beginIndex, endIndex);
   }

   public static void retrieveOHLC(String symbol)
   {
      String urlSpec = "http://finance.yahoo.com/q/ta?s=" + symbol;

      if (symbol.indexOf("^") == 0)
      {
         // Open:
         String beginStr = "Open:</td><td class=\"yfnc_tabledata1\">";
         String line = getQuoteLine(urlSpec, beginStr);

         String endStr = "</td>";
         open = getValue(line, beginStr, endStr);

         // Day's Range
         beginStr = "Day's Range:</td><td class=\"yfnc_tabledata1\">";
         String range = getValue(line, beginStr, endStr);

         if (range != null)
         {
            String delim = " - ";
            int index = range.indexOf(delim);

            high = range.substring(index + delim.length());
            low = range.substring(0, index);
         }

         // Volume
         volume = "15,000,000";

         beginStr = "Index Value:</td><td class=\"yfnc_tabledata1\"><big><b>";
         endStr = "</b>";
         last = getValue(line, beginStr, endStr);
      }
      else
      {
         // Open:
         String beginStr = "Open:</td><td class=\"yfnc_tabledata1\">";
         String line = getQuoteLine(urlSpec, beginStr);

         String endStr = "</td>";
         open = getValue(line, beginStr, endStr);

         // Day's Range
         beginStr = "Day's Range:</td><td class=\"yfnc_tabledata1\">";
         if (line.indexOf(beginStr) == -1)
         {
            // QQQ
            beginStr = "Day's Range:</td><td class=\"yfnc_tabledata1\" nowrap>";
         }
         String range = getValue(line, beginStr, endStr);

         if (range != null)
         {
            String delim = " - ";
            int index = range.indexOf(delim);

            high = range.substring(index + delim.length());
            low = range.substring(0, index);
         }

         // Volume
         beginStr = "Volume:</td><td class=\"yfnc_tabledata1\">";
         volume = getValue(line, beginStr, endStr);

         beginStr = "Last Trade:</td><td class=\"yfnc_tabledata1\"><big><b>";
         endStr = "</b>";
         last = getValue(line, beginStr, endStr);
      }
   }

   public static String retrieveLast(String symbol)
   {
      String last = null;

      if (symbol.indexOf("^") == -1)
      {
         String urlSpec = "http://finance.yahoo.com/q/ecn?s=" + symbol;
         String beginStr = "Last Trade:</td><td class=\"yfnc_tabledata1\"><b>";
         String endStr = "</b>";

         String line = getQuoteLine(urlSpec, beginStr);

         last = getValue(line, beginStr, endStr);
      }

      return last;
   }

   public static String retrieveLastDelayed(String symbol)
   {
      retrieveOHLC(symbol);

      return getLast();
   }
}
