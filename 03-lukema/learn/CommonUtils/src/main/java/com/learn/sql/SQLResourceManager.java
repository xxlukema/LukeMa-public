package com.learn.sql;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;


public class SQLResourceManager
{
   private static final Logger LOG = Logger.getLogger(SQLResourceManager.class);

   public static void close(Object obj)
   {
      close(obj, "Exception closing SQL resource.");
   }

   public static void close(Object obj, String errorMsg)
   {
      if (obj != null)
      {
         if (obj instanceof Connection)
         {
            try
            {
               ((Connection) obj).close();
            }
            catch (Throwable t)
            {
               LOG.error(errorMsg, t);
            }
         }
         else if (obj instanceof Statement)
         {
            try
            {
               ((Statement) obj).close();
            }
            catch (Throwable t)
            {
               LOG.error(errorMsg, t);
            }
         }
         else if (obj instanceof ResultSet)
         {
            try
            {
               ((ResultSet) obj).close();
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
         LOG.debug("The SQL resource is null.");
      }
   }
}

