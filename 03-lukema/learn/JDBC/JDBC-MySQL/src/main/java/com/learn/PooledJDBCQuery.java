package com.learn;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;


public class PooledJDBCQuery
{
   public static void main(String[] args)
   {
      Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;

      MysqlConnectionPoolDataSource dataSource = null;

      final ConnectionEventListener connectionEventListener = new MyConnectionListener();

      PooledConnection pooledConnection = null;
      final int NB_TESTS = 5;

      try
      {
         dataSource = new MysqlConnectionPoolDataSource();

         dataSource.setServerName("localhost");
         dataSource.setDatabaseName("test");
         dataSource.setUser("root");
         dataSource.setPassword("");

         pooledConnection = dataSource.getPooledConnection();

         pooledConnection.addConnectionEventListener(connectionEventListener);

         for (int i = 0; i < NB_TESTS; i++)
         {
            conn = pooledConnection.getConnection();

            // Print all warnings
            for (SQLWarning warn = conn.getWarnings(); warn != null; warn = warn.getNextWarning())
            {
               System.out.println("SQL Warning:");
               System.out.println("State  : " + warn.getSQLState());
               System.out.println("Message: " + warn.getMessage());
               System.out.println("Error  : " + warn.getErrorCode());
            }

            // Get a statement from the connection
            stmt = conn.createStatement();

            // Execute the query
            rs = stmt.executeQuery("SELECT name FROM company");

            // Loop through the result set
            while (rs.next())
            {
               System.out.println(rs.getString(1));
            }

            conn.close();
         }
      }
      catch (SQLException se)
      {
         System.out.println("SQL Exception:");

         // Loop through the SQL Exceptions
         while (se != null)
         {
            System.out.println("State  : " + se.getSQLState());
            System.out.println("Message: " + se.getMessage());
            System.out.println("Error  : " + se.getErrorCode());

            se = se.getNextException();
         }
      }
      catch (Exception e)
      {
         System.out.println(e);
      }
      finally
      {
         if (rs != null)
         {
            try
            {
               rs.close();
            }
            catch (SQLException e)
            {
               e.printStackTrace();
            }
         }

         if (stmt != null)
         {
            try
            {
               stmt.close();
            }
            catch (SQLException e)
            {
               e.printStackTrace();
            }
         }

         if (conn != null)
         {
            try
            {
               conn.close();
            }
            catch (SQLException e)
            {
               e.printStackTrace();
            }
         }

         if (pooledConnection != null)
         {
            try
            {
               pooledConnection.close();
            }
            catch (SQLException e)
            {
               e.printStackTrace();
            }
         }
      }
   }
}
