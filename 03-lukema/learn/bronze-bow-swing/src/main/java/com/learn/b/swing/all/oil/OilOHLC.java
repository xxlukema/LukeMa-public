package com.learn.b.swing.all.oil;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class OilOHLC
{
   public static String getQuoteLine(String urlStr, String searchForStr)
   {
      String line = null;

      try
      {
         URL url = new URI(urlStr).toURL();
         URLConnection urlConn = url.openConnection();

         ((HttpURLConnection) urlConn).setUseCaches(false);
         ((HttpURLConnection) urlConn).setRequestProperty("User-Agent",
               "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; TencentTraveler ; .NET CLR 1.1.4322)");
         ((HttpURLConnection) urlConn)
               .setRequestProperty(
                     "Accept",
                     "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*");
         ((HttpURLConnection) urlConn).setRequestProperty("Accept-Encoding", "gzip, deflate");

         ((HttpURLConnection) urlConn).connect();

         InputStream is = urlConn.getInputStream();
         InputStreamReader isr = new InputStreamReader(is);
         BufferedReader br = new BufferedReader(isr);

         while ((line = br.readLine()) != null)
         {
            if (line.indexOf(searchForStr) != -1)
            {
               break;
            }
         }

         is.close();
         isr.close();
         br.close();
      }
      catch (Throwable t)
      {
         t.printStackTrace();
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

   // Retrieve OHLC
   public static String retrieveData()
   {
      SimpleDateFormat sdf_MM = new SimpleDateFormat("MM", Locale.US);
      SimpleDateFormat sdf_yy = new SimpleDateFormat("yy", Locale.US);

      Date date = Calendar.getInstance().getTime();

      String strY = sdf_yy.format(date).substring(1);
      int intY = Integer.parseInt(strY);

      String strM = sdf_MM.format(date);
      int intM = 0;
      String intMStr = null;
      try
      {
         intM = Integer.parseInt(strM);
         intM += 2;

         if (intM >= 13)
         {
            intM = 0;
            intY++;
            if (intY > 9)
            {
               intY -= 9;
            }
         }

         intMStr = Integer.toHexString(intM);
      }
      catch (Throwable t)
      {
         t.printStackTrace();
      }

      strY = Integer.toHexString(intY);

      String urlSpec = "http://futures.tradingcharts.com/chart/CO/" + intMStr + strY
            + "/?saveprefs=t&xshowdata=t&xCharttype=b&xhide_specs=f&xhide_analysis=f&xhide_survey=t&xhide_news=f";
      // String urlSpec = "http://futures.tradingcharts.com/chart/CO/"+"C5"+"/?saveprefs=t&xshowdata=t&xCharttype=b&xhide_specs=f&xhide_analysis=f&xhide_survey=t&xhide_news=f";

      System.out.println(urlSpec);

      String beginStr = "new String('";
      String line = getQuoteLine(urlSpec, beginStr);

      String endStr = "')";
      return getValue(line, beginStr, endStr);
   }
}
