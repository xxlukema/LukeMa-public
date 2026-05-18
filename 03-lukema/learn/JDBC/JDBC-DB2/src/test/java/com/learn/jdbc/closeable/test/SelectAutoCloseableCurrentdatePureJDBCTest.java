package com.learn.jdbc.closeable.test;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.learn.util.JdbcConstants;


public class SelectAutoCloseableCurrentdatePureJDBCTest {

    private static final Logger LOG = LogManager.getLogger();

    @AfterClass
    public static void afterClass() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Test
    public void testQuery()
        throws Exception {

        LOG.info("Begin Test...");

        /*String url = JdbcConstants.URL;
        String user = JdbcConstants.USER;
        String passwd = JdbcConstants.PASSWD;*/

        for (int i = 0; i < JdbcConstants.VaeDb.length; i++) {

            String item[] = JdbcConstants.VaeDb[i];

            String url = null;
            if ("Crtd".equalsIgnoreCase(item[0])) {
                url = "jdbc:oracle:thin:@" + item[1] + ":" + item[2] + "/" + item[3];
            } else {
                url = "jdbc:db2://" + item[1] + ":" + item[2] + "/" + item[3];
            }

            String vae = item[0];
            String user = item[4];
            String passwd = item[5];

            query(vae, url, user, passwd);
        }

        LOG.info("End Test.");
    }

    private void query(String vae, String url, String user, String passwd)
        throws Exception {

        LOG.info("vae=" + vae + " url=" + url + " user=" + user + " passwd=" + passwd);

        try (Connection connection = DriverManager.getConnection(url, user, passwd)) {

            // Print all warnings
            for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn.getNextWarning()) {
                LOG.info("SQL Warning:");
                LOG.info("State  : " + warn.getSQLState());
                LOG.info("Message: " + warn.getMessage());
                LOG.info("Error  : " + warn.getErrorCode());
            }

            try (PreparedStatement select = connection.prepareStatement(JdbcConstants.SQL_SELECT_SYSDATE_AS)) {
                select.setFetchSize(1_000);

                try (ResultSet resultSet = select.executeQuery();) {

                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    int columns = resultSetMetaData.getColumnCount();
                    for (int i = 1; i <= columns; i++) {
                        String columnName = resultSetMetaData.getColumnName(i);
                        LOG.info("columnName: " + columnName);

                        String columnLabel = resultSetMetaData.getColumnLabel(i);
                        LOG.info("columnLabel: " + columnLabel);

                        String columnTypeName = resultSetMetaData.getColumnTypeName(i);
                        LOG.info("columnTypeName: " + columnTypeName);

                        String columnClassName = resultSetMetaData.getColumnClassName(i);
                        LOG.info("columnName: " + columnClassName);

                        int columnType = resultSetMetaData.getColumnType(i);
                        String jdbcTypeName = JDBCType.valueOf(columnType).getName();
                        LOG.info("jdbcTypeName: " + jdbcTypeName);
                    }

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
