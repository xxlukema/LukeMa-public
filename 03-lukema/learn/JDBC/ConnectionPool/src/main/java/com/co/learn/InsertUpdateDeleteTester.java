package com.co.learn;


import java.io.*;
import java.util.*;
import java.sql.*;

import org.apache.log4j.Logger;

import com.co.messaging.util.*;


public class InsertUpdateDeleteTester
{
   public static final int TOTAL_THREADS = 100;
   public static final int TOTAL_ALLOWED_BLOCKED_THREADS = TOTAL_THREADS+200;

   private static ConnectionPoolManager connPool = new ConnectionPoolManager("db.test.properties");

   private static Logger logger = Logger.getLogger(InsertUpdateDeleteTester.class);

   private static long ID = 0;

   private static long totalThreadsRunning = 0;

   private long id = 0;

   private static String ins1 = "insert into LukeTest (id, lname, fname) values (1, 'Ma 1', 'Luke 1')";
   private static String ins2 = "insert into LukeTest (id, lname, fname) values (2, 'Ma 2', 'Luke 2')";
   private static String ins3 = "insert into LukeTest (id, lname, fname) values (3, 'Ma 3', 'Luke 3')";
   private static String ins4 = "insert into LukeTest (id, lname, fname) values (4, 'Ma 4', 'Luke 4')";
   private static String ins5 = "insert into LukeTest (id, lname, fname) values (5, 'Ma 5', 'Luke 5')";
   private static String ins6 = "insert into LukeTest (id, lname, fname) values (6, 'Ma 6', 'Luke 6')";

   private static String upd1 = "update LukeTest set lname = 'Ma 11' and fname = 'Luke 11' where id = 1";
   private static String upd2 = "update LukeTest set lname = 'Ma 22' and fname = 'Luke 22' where id = 2";
   private static String upd3 = "update LukeTest set lname = 'Ma 33' and fname = 'Luke 33' where id = 3";
   private static String upd4 = "update LukeTest set lname = 'Ma 44' and fname = 'Luke 44' where id = 4";
   private static String upd5 = "update LukeTest set lname = 'Ma 55' and fname = 'Luke 55' where id = 5";
   private static String upd6 = "update LukeTest set lname = 'Ma 66' and fname = 'Luke 66' where id = 6";

   private static String del1 = "delete from LukeTest where id = 1";
   private static String del2 = "delete from LukeTest where id = 2";
   private static String del3 = "delete from LukeTest where id = 3";
   private static String del4 = "delete from LukeTest where id = 4";
   private static String del5 = "delete from LukeTest where id = 5";
   private static String del6 = "delete from LukeTest where id = 6";

   public InsertUpdateDeleteTester()
   {
      ID++;
      id = ID;

      totalThreadsRunning++;

      logger.info("Constructed: "+id);
   }

   public void finalize()
   {
      //logger.info("finalized: "+id+" CCCCCCCCCC InsertUpdateDeleteTester.");
   }

   public long getId()
   {
      return id;
   }

   protected static void test()
   {
      for (int i=0; i<TOTAL_THREADS; i++)
      {
         InsertUpdateDeleteTesterLauncher.launchANewThread();
      }

      try
      {
         Thread.sleep(1000*60*60); 
      }
      catch (Throwable t)
      {
         t.printStackTrace();
      }
   }

   protected void testUpdate()
   {
      ThreadMonitor tm = new ThreadMonitor(this);
      Thread t = new Thread(tm);
      t.start();

      executeUpdate(ins1);
      executeUpdate(ins2);
      executeUpdate(ins3);
      //executeUpdate(ins4);
      //executeUpdate(ins5);
      //executeUpdate(ins6);

      executeUpdate(upd1);
      executeUpdate(upd2);
      executeUpdate(upd3);
      //executeUpdate(upd4);
      //executeUpdate(upd5);
      //executeUpdate(upd6);

      executeUpdate(del1);
      executeUpdate(del2);
      executeUpdate(del3);
      //executeUpdate(del4);
      //executeUpdate(del5);
      //executeUpdate(del6);

      tm.markFinish();

      logger.info(id+": finished.");

      InsertUpdateDeleteTesterLauncher.launchANewThread();

      totalThreadsRunning--;

      //logger.info("Total number of thread running:  "+totalThreadsRunning);
      //logger.info("Total number of blocked threads: "+ThreadMonitor.getBlockedCounter());
   }

   private void setSavePoint(Connection conn)
   {
      try
      {
         conn.setSavepoint();
      }
      catch (Throwable tt)
      {
      }
   }

   private void commit(Connection conn)
   {
      try
      {
         conn.commit();
      }
      catch (Throwable tt)
      {
      }
   }

   private void executeUpdate(String sql)
   {
      Statement  stmt = null;
      Connection conn = null;

      while ((conn = connPool.newConnection()) == null)
      {
         try
         {
            Thread.sleep(5000);
         }
         catch (Throwable tt)
         {
         }
      }

      try
      {
         stmt = conn.createStatement();
      }
      catch (Exception e)
      {
         logger.error(e.getMessage());

         System.exit(1);
      }

      setSavePoint(conn);

      try
      {
         System.out.print(".");
         int affactedRows = stmt.executeUpdate(sql);
         if (affactedRows > 0)
         {
            System.out.print("!");
         }
      }
      catch (Throwable t)
      {
         System.out.print(":");
      }

      commit(conn);

      if (stmt != null)
      {
         try
         {
            stmt.close();
         }
         catch (Throwable tt)
         {
         }

         stmt = null;
      }

      if (conn != null)
      {
         try
         {
            if (!conn.isClosed())
            {
               conn.close();
            }
         }
         catch (Throwable tt)
         {
         }

         conn = null;
      }
   }
}

