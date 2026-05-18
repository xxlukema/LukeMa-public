package com.learn.jdbc;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.learn.util.JdbcConstants;

// String url = "jdbc:oracle:thin:@(description=(address=(host="+dbhost+")(protocol=tcp)(port="+dbport+"))(connect_data=(service_name="+serviceName+")))";
// String url = "jdbc:oracle:thin:@(description=(address_list=(address=(host="+dbhost+")(protocol=tcp)(port="+dbport+"))(connect_data=(service_name="+serviceName+")(server=dedicated))))";
// public static final String URL_USPS = "jdbc:oracle:thin:@eagnmnmed5a3:1521/deems.usps.gov";


public class SelectSysdatePureJDBC {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testQuery()
        throws Exception {

        try (Connection connection = DriverManager.getConnection(JdbcConstants.URL, JdbcConstants.USER, JdbcConstants.PASSWD)) {

            // Print all warnings
            for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn.getNextWarning()) {
                LOG.info("SQL Warning:");
                LOG.info("State  : " + warn.getSQLState());
                LOG.info("Message: " + warn.getMessage());
                LOG.info("Error  : " + warn.getErrorCode());
            }

            try (PreparedStatement select = connection.prepareStatement(JdbcConstants.SQL_SELECT_SYSDATE)) {

                select.setFetchSize(1_000);

                try (ResultSet resultSet = select.executeQuery()) {

                    while (resultSet.next()) {
                        Date date = resultSet.getDate("sysdate");

                        LOG.info("Date: " + date);
                    }
                }
            }

        } catch (SQLException se) {
            LOG.error("SQL Exception:", se);

            // Loop through the SQL Exceptions
            while (se != null) {
                LOG.info("State  : " + se.getSQLState());
                LOG.info("Message: " + se.getMessage());
                LOG.info("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        }
    }
}
