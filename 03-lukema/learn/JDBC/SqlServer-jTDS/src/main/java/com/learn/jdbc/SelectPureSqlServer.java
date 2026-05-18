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


public class SelectPureSqlServer {
    protected static final Logger LOG = Logger.getLogger(SelectPureSqlServer.class);

    protected static final String SqlServer_SQL_SELECT_SYSDATE = "SELECT GETDATE() AS sysdate";

    protected static final String OracleDriver = "oracle.jdbc.driver.OracleDriver";

    protected static final String XE_URL = "jdbc:oracle:thin:@localhost:1521:XE";

    protected static final String SqlServer_URL = "jdbc:jtds:sqlserver://10.28.54.79:1433;DatabaseName=CMSAC3";

    protected static final String SqlServer_jTDS_Driver = "net.sourceforge.jtds.jdbc.Driver";

    protected static final String URL = SqlServer_URL;

    protected static final String SqlServer_SQL_SELECT_AppUserRoles = "SELECT AccessLevelID, RoleCD, Role from AppUserRoles";

    @Test
    public void testQuery()
        throws Exception {
        Connection connection = null;
        PreparedStatement select = null;
        ResultSet resultSet = null;

        try {
            Class.forName(SqlServer_jTDS_Driver);

            // connection = DriverManager.getConnection(LDAP_URL, "s2f",
            // "s2f123");

            connection = DriverManager.getConnection(URL, "CMSAC3", "C3DevDemo!");

            // Print all warnings
            for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn.getNextWarning()) {
                LOG.info("SQL Warning:");
                LOG.info("State  : " + warn.getSQLState());
                LOG.info("Message: " + warn.getMessage());
                LOG.info("Error  : " + warn.getErrorCode());
            }

            select = connection.prepareStatement(SqlServer_SQL_SELECT_SYSDATE);

            resultSet = select.executeQuery();

            while (resultSet.next()) {
                Date date = resultSet.getDate("sysdate");

                LOG.info("Date: " + date);
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
        } finally {
            JdbcUtils.closeStatement(select);
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeConnection(connection);
        }
    }

    @Test
    public void testQuery2()
        throws Exception {
        Connection connection = null;
        PreparedStatement select = null;
        ResultSet resultSet = null;

        try {
            Class.forName(SqlServer_jTDS_Driver);

            // connection = DriverManager.getConnection(LDAP_URL, "s2f",
            // "s2f123");

            connection = DriverManager.getConnection(URL, "CMSAC3", "C3DevDemo!");

            // Print all warnings
            for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn.getNextWarning()) {
                LOG.info("SQL Warning:");
                LOG.info("State  : " + warn.getSQLState());
                LOG.info("Message: " + warn.getMessage());
                LOG.info("Error  : " + warn.getErrorCode());
            }

            select = connection.prepareStatement(SqlServer_SQL_SELECT_AppUserRoles);

            resultSet = select.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("AccessLevelID");
                LOG.info("id: " + id);

                String roleCd = resultSet.getString("RoleCD");
                LOG.info("roleCd: " + roleCd);

                String role = resultSet.getString("Role");
                LOG.info("role: " + role);
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
        } finally {
            JdbcUtils.closeStatement(select);
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeConnection(connection);
        }
    }

}
