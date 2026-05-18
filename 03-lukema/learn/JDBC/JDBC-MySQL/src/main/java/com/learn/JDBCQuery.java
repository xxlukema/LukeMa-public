package com.learn;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.jdbc.support.JdbcUtils;


public class JDBCQuery {
   protected static final Logger LOG = Logger.getLogger(JDBCQuery.class);

   @Test
   public void testQuery()
      throws Exception {
      Connection connection = null;
      Statement statement = null;
      ResultSet resultSet = null;

      try {
         Class.forName("com.mysql.jdbc.Driver");

         // String url = "jdbc:oracle:thin:@(description=(address=(host="+dbhost+")(protocol=tcp)(port="+dbport+"))(connect_data=(service_name="+serviceName+")))";
         // String url = "jdbc:oracle:thin:@(description=(address_list=(address=(host="+dbhost+")(protocol=tcp)(port="+dbport+"))(connect_data=(service_name="+serviceName+")(server=dedicated))))";

         String url = "jdbc:mysql://localhost:3306/test";

         connection = DriverManager.getConnection(url, "root", "");

         // Print all warnings
         for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn.getNextWarning()) {
            LOG.info("SQL Warning:");
            LOG.info("State  : " + warn.getSQLState());
            LOG.info("Message: " + warn.getMessage());
            LOG.info("Error  : " + warn.getErrorCode());
         }

         // Get a statement from the connection
         statement = connection.createStatement();

         // Execute the query
         resultSet = statement.executeQuery("SELECT name FROM company");

         // Loop through the result set
         while (resultSet.next()) {
            LOG.info(resultSet.getString(1));
         }

         // Test closing/reopen statement.

         LOG.info("Test closing/reopen statement.");

         JdbcUtils.closeStatement(statement);

         // Get a statement from the connection
         statement = connection.createStatement();

         // Execute the query
         resultSet = statement.executeQuery("SELECT name FROM company");

         // Loop through the result set
         while (resultSet.next()) {
            LOG.info(resultSet.getString(1));
         }
         
         String update = "update company set name = 'Invalid Ma'";
         PreparedStatement preparedStatement = connection.prepareStatement(update);
         
         /**
          * TODO: the SQL is update. preparedStatement.executeQuery() will fail.
          */
         preparedStatement.executeQuery();
      }
      catch (SQLException se) {
         LOG.error("SQL Exception:", se);

         // Loop through the SQL Exceptions
         while (se != null) {
            LOG.info("State  : " + se.getSQLState());
            LOG.info("Message: " + se.getMessage());
            LOG.info("Error  : " + se.getErrorCode());

            se = se.getNextException();
         }
      }
      catch (Exception e) {
         LOG.error("Exception with the main body.", e);
      }
      finally {
         JdbcUtils.closeStatement(statement);
         JdbcUtils.closeResultSet(resultSet);
         JdbcUtils.closeConnection(connection);
         
         JdbcUtils.closeStatement(null);
         JdbcUtils.closeStatement(statement);
      }
   }
}
