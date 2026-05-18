package com.learn.io;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;


public class IOResourceManager
{
   private static final Logger LOG = Logger.getLogger(IOResourceManager.class);

   public static void close(Object obj)
   {
      close(obj, "Exception closing i/o resource.");
   }

   public static void close(Object obj, String errorMsg)
   {
      if (obj != null)
      {
         if (obj instanceof InputStream)
         {
            try
            {
               ((InputStream) obj).close();
            }
            catch (Throwable t)
            {
               LOG.error(errorMsg, t);
            }
         }
         else if (obj instanceof OutputStream)
         {
            try
            {
               ((OutputStream) obj).close();
            }
            catch (Throwable t)
            {
               LOG.error(errorMsg, t);
            }
         }
         else if (obj instanceof Reader)
         {
            try
            {
               ((Reader) obj).close();
            }
            catch (Throwable t)
            {
               LOG.error(errorMsg, t);
            }
         }
         else if (obj instanceof Writer)
         {
            try
            {
               ((Writer) obj).close();
            }
            catch (Throwable t)
            {
               LOG.error(errorMsg, t);
            }
         }
         else if (obj instanceof Socket)
         {
            try
            {
               ((Socket) obj).close();
            }
            catch (Throwable t)
            {
               LOG.error(errorMsg, t);
            }
         }
         else if (obj instanceof ServerSocket)
         {
            try
            {
               ((ServerSocket) obj).close();
            }
            catch (Throwable t)
            {
               LOG.error(errorMsg, t);
            }
         }
         else
         {
            LOG.error("Unknown resource type: " + obj.getClass().getName());
         }
      }
      else
      {
         LOG.debug("The I/O resource is null.");
      }
   }
}

