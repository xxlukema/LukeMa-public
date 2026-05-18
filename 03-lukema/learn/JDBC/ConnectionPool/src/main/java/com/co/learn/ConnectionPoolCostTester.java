package com.co.learn;


import java.sql.*;

import org.apache.log4j.Logger;

import com.co.messaging.util.*;


/**
 Test result for 1,000,000 transactions: Stack sql statement variable is 0.116 millisecond slower per transaction.
    ### 2006-05-30 15:04:14,974 INFO  App(66) testStatementAsMembor():
    Time diff: 462 seconds.
    ### 2006-05-30 15:13:53,575 INFO  App(97) testStatementAsStackVariable():
    Time diff: 578 seconds.
 */
public class ConnectionPoolCostTester
{
   private static Logger logger = Logger.getLogger(ConnectionPoolCostTester.class.getName());

   private static final ConnectionPoolManager CONN_POOL = new ConnectionPoolManager("db.test.properties");

   private Statement statement = null;

   private int loopCounter = 1000*10;

   private static final String SQL = "select count(*) from LukeTest";

   public void testStatementAsMembor()
   {
      long start = System.currentTimeMillis();

      Connection conn = CONN_POOL.newConnection();

      try
      {
         statement = conn.createStatement();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      ResultSet rs = null;

      try
      {
         for (int i=0; i<loopCounter; i++)
         {
            rs = statement.executeQuery(SQL);
            rs.close();
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      CONN_POOL.close(conn, statement, rs);

      long end = System.currentTimeMillis();
      long timeDiff = (end - start)/1000;

      logger.info("Time diff: "+timeDiff+" seconds.");
   }


   public void testStatementAsStackVariable()
   {
      long start = System.currentTimeMillis();

      Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;

      try
      {
         for (int i=0; i<loopCounter; i++)
         {
            conn = CONN_POOL.newConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL);

            CONN_POOL.close(conn, statement, rs);
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      long end = System.currentTimeMillis();
      long timeDiff = (end - start)/1000;

      logger.info("Time diff: "+timeDiff+" seconds.");
   }



   public static void main( String[] args )
   {
      ConnectionPoolCostTester tester = new ConnectionPoolCostTester();

      tester.testStatementAsMembor();
      tester.testStatementAsStackVariable();
   }
}
