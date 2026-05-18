package com.learn.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.jdbc.support.JdbcUtils;


public class SelectPureSqlServerProc {
    protected static final Logger LOG = Logger.getLogger(SelectPureSqlServerProc.class);

    protected static final String SqlServer_SQL_SELECT_SYSDATE = "exec usp_z_luke_test ?, ?";

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

            select.setInt(1, 2);
            select.setString(2, "Test Sql String");

            resultSet = select.executeQuery();

            while (resultSet.next()) {
                int clientID = resultSet.getInt("ClientID");

                String clientName = resultSet.getString("ClientName");

                int cMGClientId = resultSet.getInt("CMGClientId");

                LOG.info("clientID: " + clientID + " : " + "clientName: " + clientName + " : "
                        + "cMGClientId: " + cMGClientId);

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
