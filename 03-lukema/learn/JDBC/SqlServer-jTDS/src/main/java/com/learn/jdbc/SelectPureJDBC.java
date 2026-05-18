package com.learn.jdbc;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.jdbc.support.JdbcUtils;


// String url = "jdbc:oracle:thin:@(description=(address=(host="+dbhost+")(protocol=tcp)(port="+dbport+"))(connect_data=(service_name="+serviceName+")))";
// String url = "jdbc:oracle:thin:@(description=(address_list=(address=(host="+dbhost+")(protocol=tcp)(port="+dbport+"))(connect_data=(service_name="+serviceName+")(server=dedicated))))";

public class SelectPureJDBC {
    protected static final Logger LOG = Logger.getLogger(SelectPureJDBC.class);

    protected static final String SQL_SELECT_TIME = "select sysdate from dual";

    protected static final String OracleDriver = "oracle.jdbc.driver.OracleDriver";

    protected static final String XE_URL = "jdbc:oracle:thin:@localhost:1521:XE";

    protected static final String URL = XE_URL;

    @Test
    public void testQuery()
        throws Exception {
        Connection connection = null;
        PreparedStatement select = null;
        ResultSet resultSet = null;

        try {
            Class.forName(OracleDriver);

            connection = DriverManager.getConnection(URL, "luke", "luke");

            //connection = DriverManager.getConnection(URL, "S2F_APP", "Py7SvTIkuKycEWqqWOTEXmDjyswDYp");

            // Print all warnings
            for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn.getNextWarning()) {
                LOG.info("SQL Warning:");
                LOG.info("State  : " + warn.getSQLState());
                LOG.info("Message: " + warn.getMessage());
                LOG.info("Error  : " + warn.getErrorCode());
            }

            select = connection.prepareStatement(SQL_SELECT_TIME);

            resultSet = select.executeQuery();

            while (resultSet.next()) {
                Date date = resultSet.getDate("sysdate");

                LOG.info("Date: " + date);
            }

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
        finally {
            JdbcUtils.closeStatement(select);
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeConnection(connection);
        }
    }
}
