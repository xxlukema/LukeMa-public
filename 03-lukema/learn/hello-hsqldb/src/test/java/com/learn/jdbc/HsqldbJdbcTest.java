package com.learn.jdbc;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class HsqldbJdbcTest {

    // final String URL = "jdbc:hsqldb:hsql://localhost/xdb;sql.syntax_pgs=true";
    // final String URL = "jdbc:hsqldb:hsql://localhost/xdb;sql.syntax_ora=true";
    final String URL = "jdbc:hsqldb:hsql://localhost/xdb";
    final String DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    // final String SELECT_CURRENT_DATETIME = "select current_timestamp as date";
    // final String SELECT_CURRENT_DATETIME = "SELECT c1 as date FROM (VALUES (current_timestamp))";
    // final String SELECT_CURRENT_DATETIME = "select current_date as date";
    // final String SELECT_CURRENT_DATETIME = "SELECT now() as date FROM INFORMATION_SCHEMA.SYSTEM_USERS";
    final String SELECT_CURRENT_DATETIME = "SELECT now() as date FROM (values(0))";
    // final String SELECT_CURRENT_DATETIME = "SELECT current_date as date FROM (values(0))";
    // final String SELECT_CURRENT_DATETIME = "SELECT now() as date";

    @Test
    public void testJdbc()
        throws SQLException, ClassNotFoundException {
        log.debug(() -> "Start test.");

        // System.setProperty("hsqldb.method_class_names", "current_timestamp");

        try {
            Class.forName(DRIVER);

            try (Connection connection = DriverManager.getConnection(URL, "SA", "")) {
                PreparedStatement select = connection.prepareStatement(SELECT_CURRENT_DATETIME);

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
            } catch (SQLException ex) {
                log.error("JDBC exception: {}", ex.getMessage(), ex);

                SQLException ex1 = ex;
                // Loop through the SQL Exceptions
                while (ex != null) {
                    log.info("State  : " + ex.getSQLState());
                    log.info("Message: " + ex.getMessage());
                    log.info("Error  : " + ex.getErrorCode());

                    ex = ex.getNextException();
                }

                throw ex1;
            }

        } catch (ClassNotFoundException e) {
            log.error("Unable to load jdbc driver: {}", e.getMessage(), e);
            throw e;
        }

        log.debug(() -> "End test.");
    }

}
