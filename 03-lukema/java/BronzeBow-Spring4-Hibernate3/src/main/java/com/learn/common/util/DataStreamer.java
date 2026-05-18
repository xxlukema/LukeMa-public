package com.learn.common.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;


public class DataStreamer
{
   private static final HexBinaryAdapter HexBinaryAdapter = new HexBinaryAdapter();

   public static void main(String[] args)
      throws Exception
   {
      String hov = "hov";
      String hex = objectSerializeToHexString(hov);
      System.out.println("hex: " + hex);
      hov = hexStringDeserializeToObject(hex);
      System.out.println("hov: " + hov);

      Integer integer = 2134;
      hex = objectSerializeToHexString(integer);
      System.out.println("hex: " + hex);
      integer = hexStringDeserializeToObject(hex);
      System.out.println("integer: " + integer);
   }

   public static <T> String objectSerializeToHexString(T t)
      throws Exception
   {
      byte[] bytes = objectSerializeToBytes(t);
      return bytesToHexString(bytes);
   }

   @SuppressWarnings("unchecked")
   public static <T> T hexStringDeserializeToObject(String hexString)
      throws Exception
   {
      byte[] bytes = hexStringToBytes(hexString);

      return (T) bytesDeserializeToObject(bytes);
   }

   public static String bytesToHexString(byte[] bytes)
   {
      return HexBinaryAdapter.marshal(bytes);
   }

   public static byte[] hexStringToBytes(String hexString)
   {
      return HexBinaryAdapter.unmarshal(hexString);
   }

     private static <T> byte[] objectSerializeToBytes(T object)
        throws Exception
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        byte[] bytes = null;
        try
        {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            bytes = bos.toByteArray();
        }
        finally
        {
            bos.close();
            if (oos != null)
            {
                oos.close();
            }
        }
        return bytes;
    }

    @SuppressWarnings("unchecked")
    private static <T> T bytesDeserializeToObject(byte[] bytes)
        throws Exception
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        Object object = null;
        try
        {
            ois = new ObjectInputStream(bais);
            object = ois.readObject();
        }
        finally
        {
            bais.close();
            if (ois != null)
            {
                ois.close();
            }
        }

        return (T) object;
    }

   protected static byte[] zipStringToBytes(String input)
      throws IOException
   {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      BufferedOutputStream bufos = new BufferedOutputStream(new GZIPOutputStream(bos));
      bufos.write(input.getBytes());
      bufos.close();
      byte[] retval = bos.toByteArray();
      bos.close();
      return retval;
   }

   protected static String unzipStringFromBytes(byte[] bytes)
      throws IOException
   {
      ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
      BufferedInputStream bufis = new BufferedInputStream(new GZIPInputStream(bis));
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      byte[] buf = new byte[1024];
      int len;
      while ((len = bufis.read(buf)) > 0)
      {
         bos.write(buf, 0, len);
      }
      String retval = bos.toString();
      bis.close();
      bufis.close();
      bos.close();
      return retval;
   }
}
