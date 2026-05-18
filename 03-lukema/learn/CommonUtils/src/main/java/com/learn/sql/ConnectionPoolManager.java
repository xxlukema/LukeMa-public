/*package com.learn.sql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.learn.classpath.ClassPathPropertyLoader;
import com.learn.mail.Mailer;


public final class ConnectionPoolManager
{
   private static final Logger LOG = Logger.getLogger(ConnectionPoolManager.class);
   private Properties prop         = null;
   private final Vector<Connection> CONN_VECTOR = new Vector<Connection>();
   private int initPoolSize = 0;
   private long timePoolBuilt = 0;
   private int resourceDownSleepTimeSeconds = 130;         // default 130 seconds


   public ConnectionPoolManager(String dbPropertyFileName)
   {
      prop = ClassPathPropertyLoader.load(dbPropertyFileName);

      String driver = prop.getProperty("db.driver");

      if (driver == null)
      {
         driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
      }

      try
      {
         Class.forName(driver);
      }
      catch (Throwable t)
      {
         t.printStackTrace();
         System.exit(1);
      }

      initPoolSize = ClassPathPropertyLoader.getPropertyAsIntDefault0(prop, "db.pool.size");

      if (initPoolSize < 1 || initPoolSize > 3500)
      {
         LOG.error("Connection pool size must be specified and in the range of (1, 3500).");
         LOG.info("Exit.");
         System.exit(1);
      }

      int sleepTime = ClassPathPropertyLoader.getPropertyAsIntDefault0(prop, "sleep.time.seconds.on.resource.down");
      if (sleepTime > 0)
      {
         resourceDownSleepTimeSeconds = sleepTime;
      }

      rebuildConnectionPoolBlock();
      LOG.info("Created connection pool. Pool size: "+CONN_VECTOR.size());
   }

   public static boolean isGoodConnection(Connection conn)
   {
      try
      {
         conn.getMetaData();
      }
      catch (Throwable th)
      {
         LOG.error("Unable to get MetaData: "+th.getMessage());
         th.printStackTrace();

         return false;
      }

      return true;
   }

   public int getConnectionPoolSize()
   {
      return CONN_VECTOR.size();
   }

   synchronized public void rebuildConnectionPoolBlock()
   {
      long now = System.currentTimeMillis();

      if ((now-timePoolBuilt) > 900)
      {
         privateReConstructThePoolBlock();

         timePoolBuilt = System.currentTimeMillis();
      }
   }

   private void clearPool()
   {
      while (CONN_VECTOR.size() > 0)
      {
         Connection conn = newConnection();
         try
         {
            ((PooledConnection) conn).closeAndDumpThisConnection();
            conn = null;
         }
         catch (Throwable th)
         {
         }
      }

      CONN_VECTOR.clear();
   }

   private void privateReConstructThePoolBlock()
   {
      clearPool();

      String dbURL = prop.getProperty("db.url");
      String usr = prop.getProperty("db.usr");
      String passwd = prop.getProperty("db.passwd");

      String subject = null;
      String message = null;

      if (timePoolBuilt == 0)
      {
         subject = "Creating connection pool...";
         message = "Connecting ";
      }
      else
      {
         subject = "Recreating connection pool...";
         message = "Connections to DB created at "+(new java.util.Date(timePoolBuilt))+" were lost. Reconnecting ";
      }

      message += "to DB... <BR>URL: "+dbURL+"<BR>Usr: "+usr;

      Mailer.sendMail(subject, message);

      String logMessage = message.replaceAll("<BR>", "\n");
      LOG.info(logMessage);

      for (int i=0; i<initPoolSize; i++)
      {
         Connection conn = null;

         try
         {
            conn = DriverManager.getConnection(dbURL, usr, passwd);
            reuseConnection(conn);
         }
         catch (Exception e)
         {
            LOG.error("Unable to connect to database: "+e.getMessage());
            LOG.error("Sleeping for "+resourceDownSleepTimeSeconds+" and retry...");
            try
            {
               Thread.sleep(resourceDownSleepTimeSeconds*1000);
            }
            catch (Throwable th)
            {
            }

            privateReConstructThePoolBlock();

            return;
         }
      }

      subject = "Connect pool created";
      Mailer.sendMail(subject, "Connected to DB. <BR>URL: "+dbURL+"<BR>Usr: "+usr);
   }

   public Connection newConnectionNotFromPool()
   throws SQLException
   {
      String dbURL = prop.getProperty("db.url");
      String usr = prop.getProperty("db.usr");
      String passwd = prop.getProperty("db.passwd");

      return DriverManager.getConnection(dbURL, usr, passwd);
   }

   *//**
    * @return null if the pool is empty or if the connection pool is used up.
    *//*
   synchronized public Connection newConnection()
   {
      LOG.debug("Connection pool size: "+CONN_VECTOR.size());

      if (CONN_VECTOR.size() > 0)
      {
         return (Connection) CONN_VECTOR.remove(0);
      }
      else
      {
         LOG.error("No new connection available in the connection pool now. \n"+
                      "Problem: Either the initial pool size of +"+initPoolSize+" is too small +\n"+
                      "         or there are connection unclosed after use.");

         return null;
      }
   }

   protected void reuseConnection(Connection conn)
   {
      if (conn != null)
      {
         try
         {
            if (!conn.isClosed())
            {
               PooledConnection pc = new PooledConnection(conn, this);
               CONN_VECTOR.add(pc);
            }
         }
         catch (Throwable th)
         {
            LOG.error("Unable to reuse connection: "+th.getMessage());
            th.printStackTrace();
         }
      }
   }

   public static void close(Connection conn, Statement stmt, ResultSet rs)
   {
      SQLResourceManager.close(rs, "Exception closing sql ResultSet.");
      SQLResourceManager.close(stmt, "Exception closing sql Statement.");
      SQLResourceManager.close(conn, "Exception closing sql Connection.");
   }
}

*/