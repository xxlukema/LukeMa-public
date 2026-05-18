package com.learn.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
class SelectWildCardPureJDBCTest {

    protected static final String JDBC_DRIVER = "org.postgresql.Driver";

    String url = "jdbc:postgresql://database-bc-int2.bc.int:5432/vrdfile";

    @Test
    void testQuery()
        throws Exception {

        Class.forName(JDBC_DRIVER);

        try (Connection connection = DriverManager.getConnection(url, "vrdfile", "vrdfile");
                PreparedStatement select = connection.prepareStatement("select task_name from sub_task where task_name like ?");) {

            select.setString(1, "%File%");

            try (ResultSet resultSet = select.executeQuery();) {

                while (resultSet.next()) {
                    String taskName = resultSet.getString("task_name");

                    log.info("task_name: " + taskName);
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
    }
}
