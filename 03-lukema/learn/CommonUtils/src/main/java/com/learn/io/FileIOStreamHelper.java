package com.learn.io;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.learn.classpath.ClassPathInputStream;


public class FileIOStreamHelper
{
   public static String readClassPathFile2String(String fileName)
      throws IOException
   {
      InputStream is = ClassPathInputStream.newInputStream(fileName);

      String str = readInputStream2String(is);

      IOResourceManager.close(is, "Exception closing InputStream.");

      return str;
   }

   public static String readInputStream2String(InputStream is)
      throws IOException
   {
      String ret = null;

      InputStreamReader isr = null;
      BufferedReader br = null;

      try
      {
         isr = new InputStreamReader(is);
         br = new BufferedReader(isr);

         StringBuilder sb = new StringBuilder();
         String line = null;
         while ((line = br.readLine()) != null)
         {
            sb.append(line);
         }

         ret = sb.toString();
      }
      finally
      {
         IOResourceManager.close(br, "Exception closing BufferedReader.");
         IOResourceManager.close(isr, "Exception closing InputStreamReader.");
         IOResourceManager.close(is, "Exception closing InputStream.");
      }

      return ret;
   }

   public static String readFile2String(String fileName)
      throws IOException
   {
      FileInputStream fis = null;

      try
      {
         fis = new FileInputStream(fileName);
         return readInputStream2String(fis);
      }
      finally
      {
         IOResourceManager.close(fis, "Exception closing FileInputStream.");
      }
   }

   public static void writeString2File(String str, String fileName)
      throws IOException
   {
      FileOutputStream fos = null;

      try
      {
         fos = new FileOutputStream(fileName);
         fos.write(str.getBytes());
      }
      finally
      {
         IOResourceManager.close(fos, "Exception closing FileOutputStream.");
      }
   }

   public static void writeString2OutputStream(String str, OutputStream os)
      throws IOException
   {
      os.write(str.getBytes());
   }
}
