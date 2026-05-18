package com.co.learn;


import java.io.*;
import java.util.*;
import java.sql.*;

import org.apache.log4j.Logger;

import com.co.messaging.util.*;


public class PerformanceTestSingleCall
{
   private static final int NUMBER_TESTS = 100*1000;

   private static final Logger logger = Logger.getLogger(PerformanceTestSingleCall.class);

   private static final ConnectionPoolManager CONN_POOL = new ConnectionPoolManager("db.test.properties");

   private static final String SELECT = "select lname, fname from LukeTest where id = 1";


   public static void doOneQuery()
   {
      Connection conn = null;
      Statement  stmt = null;
      ResultSet  rs   = null;

      try
      {
         conn = CONN_POOL.newConnection();
         stmt = conn.createStatement();
         rs = stmt.executeQuery(SELECT);

         /*
         while (rs.next())
         {
            String lname = rs.getString(1);
            logger.info("Lname: "+lname);
         }
         */
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         ConnectionPoolManager.close(conn, stmt, rs);
      }
   }

   public static void doTest()
   {
      long startTime = System.currentTimeMillis();

      for (int i=0; i<NUMBER_TESTS; i++)
      {
         doOneQuery();
      }

      long endTime = System.currentTimeMillis();

      logger.info("Time: "+((endTime-startTime)/1000)+" seconds");
   }
}

