package com.learn.jdbc.closeable;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        
        /**
         * rad-uws-tefms:
         * 
         */
        // String url0 = "jdbc:oracle:thin:@(Description=(Failover=ON)(Address_List=(Load_Balance=ON)(address=(protocol=tcp)(host=eagnmnmed10e)(port=1521))(address=(protocol=tcp)(host=eagnmnmed10f)(port=1521)))(Connect_data=(service_name=tefms.usps.gov)(SERVER=DEDICATED)))";
        String url0 = "jdbc:oracle:thin:@eagnmnmed10f:1521/tefms.usps.gov";
        String username0 = "uws";
        String password0 = "jM4SHc6B#DGqfyB";
        
        /*
         * rad-fmsplng-dnav
         * 
         * TODO: eagnmnmed110 is bad @ 2018-01-17
         */
        String url1 = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=eagnmnmed111.usps.gov)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=dnav.usps.gov)(FAILOVER_MODE=(TYPE=SELECT)(METHOD=BASIC)(RETRIES=180)(DELAY=5))))";
        // String url1 = "jdbc:oracle:thin:@eagnmnmed111:1521/dnav.usps.gov"; 
        String username1 = "fmsplng";
        String password1 = "dJ7PbMcrdG#qW3d";
        
        /*
         * rad-eems-deems:
         */
        // String url2 = "jdbc:oracle:thin:@(Description=(Failover=ON)(Address_List=(Load_Balance=ON)(address=(protocol=tcp)(host=eagnmnmed5a3)(port=1521))(address=(protocol=tcp)(host=eagnmnmed5a4)(port=1521)))(Connect_data=(service_name=deems.usps.gov)(SERVER=DEDICATED)))";
        String url2 = "jdbc:oracle:thin:@eagnmnmed5a3:1521/deems.usps.gov";
        String username2 = "eems";
        String password2 = "S7W82zF7#2jMuL2";

        try (Connection connection = DriverManager.getConnection(url1, username1, password1);
                PreparedStatement select = connection.prepareStatement(JdbcConstants.SQL_SELECT_SYSDATE);) {

            // Print all warnings
            for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn.getNextWarning()) {
                LOG.info("SQL Warning:");
                LOG.info("State  : " + warn.getSQLState());
                LOG.info("Message: " + warn.getMessage());
                LOG.info("Error  : " + warn.getErrorCode());
            }

            select.setFetchSize(1_000);
            
            try (ResultSet resultSet = select.executeQuery();) {

                while (resultSet.next()) {
                    Date date = resultSet.getDate("sysdate");

                    LOG.info("Date: " + date);
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
        
        LOG.info("End Test.");
    }
}
