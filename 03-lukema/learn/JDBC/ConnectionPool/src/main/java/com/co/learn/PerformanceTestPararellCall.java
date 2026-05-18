package com.co.learn;


import java.io.*;
import java.util.*;
import java.sql.*;

import org.apache.log4j.Logger;

import com.co.messaging.util.*;


public class PerformanceTestPararellCall
implements Runnable
{
   private static final int NUMBER_TESTS   = 100*1000;
   private static final int NUMBER_THREADS = 6;

   private static int testCounter = 0;

   private static boolean end = false;

   private static final Logger logger = Logger.getLogger(PerformanceTestSingleCall.class.getName());

   private static final ConnectionPoolManager CONN_POOL = new ConnectionPoolManager("db.test.properties");

   private static final String SELECT = "select lname, fname from LukeTest where id = 1";

   private static final long START_TIME = System.currentTimeMillis();

   synchronized public static void incTestCounter()
   {
      testCounter++;
   }


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
      for (int i=0; i<NUMBER_THREADS; i++)
      {
         PerformanceTestPararellCall r = new PerformanceTestPararellCall();
         new Thread(r).start();
      }

      while (!end)
      {
         try
         {
            Thread.sleep(500);
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
   }

   public void run()
   {
      while (testCounter < NUMBER_TESTS)
      {
         incTestCounter();
         doOneQuery();
      }

      long endTime = System.currentTimeMillis();
      logger.info("Time: "+((endTime-START_TIME)/1000)+" seconds");

      end = true;
   }
}

