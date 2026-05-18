package com.learn.io;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;


public class InputStreamHelper
{
   private static final Logger LOG = Logger.getLogger(InputStreamHelper.class);

   public static String readInputStreamToStringBlock(InputStream is)
   {
      if (is == null)
      {
         LOG.debug("InputStream is null.");

         return null;
      }

      StringBuilder sb = new StringBuilder();

      InputStreamReader isr = null;
      BufferedReader br = null;

      try
      {
         isr = new InputStreamReader(is);
         br = new BufferedReader(isr);

         LOG.debug("Converting the content of the InputStream (buffered) to String...");

         for (String line = br.readLine(); line != null; line = br.readLine())
         {
            sb.append(line);
            sb.append('\n');
         }
      }
      catch (Throwable t)
      {
         sb.append(t.toString());
      }
      finally
      {
         IOResourceManager.close(isr);
         IOResourceManager.close(br);

         LOG.debug("Completed with the InputStream.");
      }

      return sb.toString();
   }

   public static void printInputStreamBuffered(InputStream is)
   {
      if (is == null)
      {
         LOG.debug("InputStream is null.");

         return;
      }

      InputStreamReader isr = null;
      BufferedReader br = null;

      try
      {
         isr = new InputStreamReader(is);
         br = new BufferedReader(isr);

         LOG.debug("Printing the content of the InputStream (buffered)...");

         for (String line = br.readLine(); line != null; line = br.readLine())
         {
            System.out.println(line);
         }
      }
      catch (Throwable t)
      {
         LOG.error("Exception reading InputStream.", t);
      }
      finally
      {
         IOResourceManager.close(isr, "Exception closing InputStreamReader.");
         IOResourceManager.close(br, "Exception closing BufferedReader.");

         LOG.debug("Completed with the InputStream.");
      }
   }

   public static void printInputStreamNotBuffered(InputStream is)
   {
      if (is == null)
      {
         LOG.debug("InputStream is null.");

         return;
      }

      LOG.debug("Printing the content of the InputStream (not buffered)...");

      try
      {
         for (int ch = is.read(); ch != -1; ch = is.read())
         {
            System.out.print((char) ch);
         }
      }
      catch (IOException e)
      {
         LOG.error("Exception reading InputStream.", e);
      }

      LOG.debug("Completed with the InputStream.");
   }
}
