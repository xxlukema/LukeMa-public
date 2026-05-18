package com.learn.jdbc.closeable;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.AfterClass;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;


public class SelectAutocloseableWildCardPureJDBCTest {

    private static final Logger LOG = LogManager.getLogger();

    protected static final String JdbcDriver = "org.postgresql.Driver";

    String url = "jdbc:postgresql://database-bc-int2.bc.int:5432/vrdfile";

    @AfterClass
    public static void afterClass() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Test
    public void testQuery()
        throws Exception {

        Class.forName(JdbcDriver);

        try (Connection connection = DriverManager.getConnection(url, "vrdfile", "vrdfile");
                PreparedStatement select = connection.prepareStatement("select task_name from sub_task where task_name like ?");) {

            select.setFetchSize(1_000);
            select.setString(1, "%File%");

            try (ResultSet resultSet = select.executeQuery();) {

                while (resultSet.next()) {
                    String task_name = resultSet.getString("task_name");

                    LOG.info("task_name: " + task_name);
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
