package com.learn.jdbc;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

import org.junit.jupiter.api.Test;

import com.learn.util.StringConstants;

import lombok.extern.log4j.Log4j2;


@Log4j2
class SelectCurrentdatePureJDBCTest {

    /* Localhost */

    private static final String HOST = "localhost";
    private static final String USERNAME = "luke";
    private static final String PASSWORD = "luke";
    private static final String DBNAME = "test";

    private static final String URL = "jdbc:postgresql://" + HOST + ":5432/" + DBNAME;

    @Test
    void testQuery()
        throws Exception {

        log.info("Begin Test...");

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement select = connection.prepareStatement(StringConstants.SQL_SELECT_CURRENTDATE);) {

            // Print all warnings
            for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn.getNextWarning()) {
                log.info("SQL Warning:");
                log.info("State  : " + warn.getSQLState());
                log.info("Message: " + warn.getMessage());
                log.info("Error  : " + warn.getErrorCode());
            }

            try (ResultSet resultSet = select.executeQuery();) {

                while (resultSet.next()) {
                    Date date = resultSet.getDate("date");

                    log.info("Date: " + date);
                }
            }
        } catch (SQLException se) {
            log.error("SQL Exception:", se);

            // Loop through the SQL Exceptions
            while (se != null) {
                log.info("State  : " + se.getSQLState());
                log.info("Message: " + se.getMessage());
                log.info("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        }

        log.info("End Test.");
    }
}
