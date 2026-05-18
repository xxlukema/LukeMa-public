package com.co.learn;


import java.io.*;
import java.util.*;
import java.sql.*;

import org.apache.log4j.Logger;

import com.co.messaging.util.*;


public class TestResultSet
{
   private static final int NUMBER_TESTS = 1;

   private static final Logger logger = Logger.getLogger(TestResultSet.class);

   private static final ConnectionPoolManager CONN_POOL = new ConnectionPoolManager("db.test.properties");

   private static final String SELECT1 = "select lname, fname from LukeTest where id = 1";
   private static final String SELECT2 = "select lname, fname from LukeTest where id = 2";


   public static void doOneQuery()
   {
      Connection conn  = null;
      Statement  stmt1 = null;
      Statement  stmt2 = null;
      ResultSet  rs1   = null;
      ResultSet  rs2   = null;

      try
      {
         conn  = CONN_POOL.newConnection();

         stmt1 = conn.createStatement();
         stmt2 = conn.createStatement();

         rs1   = stmt1.executeQuery(SELECT1);
         rs2   = stmt2.executeQuery(SELECT2);

         while (rs2.next())
         {
            String lname = rs2.getString(1);
            logger.info("ResultSet2 ==> Lname: "+lname);
         }

         while (rs1.next())
         {
            String lname = rs1.getString(1);
            logger.info("ResultSet1 ==> Lname: "+lname);
         }
      }
      catch (SQLException e)
      {
         //errorWithConnection.getErrorCode() == 1205)
         logger.info("111111111111111111111111111111111111111111111");
         logger.info("###"+e.getErrorCode());
         e.printStackTrace();
         logger.info("222222222222222222222222222222222222222222222");
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         ConnectionPoolManager.close(conn, stmt1, rs1);
         ConnectionPoolManager.close(conn, stmt2, rs2);
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

