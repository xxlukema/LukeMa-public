package com.learn;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
      Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;

      try
      {
         Class.forName("com.mysql.jdbc.Driver");

         // String url = "jdbc:oracle:thin:@(description=(address=(host="+dbhost+")(protocol=tcp)(port="+dbport+"))(connect_data=(service_name="+serviceName+")))";
         // String url = "jdbc:oracle:thin:@(description=(address_list=(address=(host="+dbhost+")(protocol=tcp)(port="+dbport+"))(connect_data=(service_name="+serviceName+")(server=dedicated))))";

         String url = "jdbc:mysql://localhost:3306/test";

         conn = DriverManager.getConnection(url, "root", "");

         // Print all warnings
         for (SQLWarning warn = conn.getWarnings(); warn != null; warn = warn.getNextWarning())
         {
            LOG.info("SQL Warning:");
            LOG.info("State  : " + warn.getSQLState());
            LOG.info("Message: " + warn.getMessage());
            LOG.info("Error  : " + warn.getErrorCode());
         }

         // Get a statement from the connection
         stmt = conn.createStatement();

         // Execute the query
         rs = stmt.executeQuery("SELECT name FROM customers");

         // Loop through the result set
         while (rs.next())
         {
            LOG.info(rs.getString(1));
         }
      }
      finally
      {
         if (rs != null)
         {
            rs.close();
         }

         if (stmt != null)
         {
            stmt.close();
         }

         if (conn != null)
         {
            conn.close();
         }
      }
   }
}
