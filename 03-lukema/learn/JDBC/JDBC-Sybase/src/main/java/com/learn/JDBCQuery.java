package com.learn;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.junit.Test;


public class JDBCQuery
{
   protected static final Logger LOG = Logger.getLogger(JDBCQuery.class);

   @Test
   public void jdbcQuery()
      throws Exception
   {
      Connection connection = null;
      Statement statement = null;
      ResultSet resultSet = null;

      try
      {
         Class.forName("net.sourceforge.jtds.jdbc.Driver");

         // String url = "jdbc:oracle:thin:@(description=(address=(host="+dbhost+")(protocol=tcp)(port="+dbport+"))(connect_data=(service_name="+serviceName+")))";
         // String url = "jdbc:oracle:thin:@(description=(address_list=(address=(host="+dbhost+")(protocol=tcp)(port="+dbport+"))(connect_data=(service_name="+serviceName+")(server=dedicated))))";

         String url = "jdbc:jtds:sybase://esssybd1.uk.db.com:5000;DatabaseName=ETS_ESS_TEST";

         connection = DriverManager.getConnection(url, "ess_batch", "ess_batch01");

         // Print all warnings
         for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn.getNextWarning())
         {
            LOG.info("SQL Warning:");
            LOG.info("State  : " + warn.getSQLState());
            LOG.info("Message: " + warn.getMessage());
            LOG.info("Error  : " + warn.getErrorCode());
         }

         // Get a statement from the connection
         statement = connection.createStatement();

         // Execute the query
         resultSet = statement.executeQuery("SELECT CITY FROM Address");

         int rowCount = 0;
         // Loop through the result set
         while (resultSet.next())
         {
             LOG.info(resultSet.getString(1));
             
             rowCount++;
         }
         
         LOG.info("Rows: " + rowCount);
      }
      catch (SQLException se)
      {
          LOG.info("SQL Exception:");

         // Loop through the SQL Exceptions
         while (se != null)
         {
            LOG.info("State  : " + se.getSQLState());
            LOG.info("Message: " + se.getMessage());
            LOG.info("Error  : " + se.getErrorCode());

            se = se.getNextException();
         }
      }
      finally
      {
         if (resultSet != null)
         {
             resultSet.close();
         }

         if (statement != null)
         {
             statement.close();
         }

         if (connection != null)
         {
             connection.close();
         }
      }
   }
}
