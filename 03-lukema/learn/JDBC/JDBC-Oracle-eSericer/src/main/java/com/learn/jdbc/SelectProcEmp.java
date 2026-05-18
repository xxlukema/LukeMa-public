package com.learn.jdbc;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.support.JdbcUtils;


public class SelectProcEmp {
    protected static final Logger LOG = Logger.getLogger(SelectProcEmp.class);

    protected static final String SQL_SELECT_DUAL = "select sysdate from dual";

    protected static final String SQL_PROC_EMP = "{ call emp_package.select_emp(?, ?) }";

    protected static final String OracleDriver = "oracle.jdbc.driver.OracleDriver";

    protected static final String XE_URL = "jdbc:oracle:thin:@localhost:1521:XE";

    protected static final String URL = XE_URL;

    private Connection connection = null;

    @Before
    public void before()
        throws Exception {

        Class.forName(OracleDriver);
        connection = DriverManager.getConnection(URL, "luke", "luke");

        // Print all warnings
        for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn.getNextWarning()) {
            LOG.info("SQL Warning:");
            LOG.info("State  : " + warn.getSQLState());
            LOG.info("Message: " + warn.getMessage());
            LOG.info("Error  : " + warn.getErrorCode());
        }
    }

    @After
    public void after()
        throws Exception {

        JdbcUtils.closeConnection(connection);
    }

    @Test
    public void testProcEmp()
        throws Exception {
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            callableStatement = connection.prepareCall(SQL_PROC_EMP);

            callableStatement.setInt(1, 1);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);

            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(2);

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                LOG.info("id: " + id);
                String name = resultSet.getString("name");
                LOG.info("name: " + name);
                Date date = resultSet.getDate("birth_date");
                LOG.info("birth date: " + date);
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
            JdbcUtils.closeStatement(callableStatement);
            JdbcUtils.closeResultSet(resultSet);
        }
    }

    @Test
    @Ignore
    public void testConnection()
        throws Exception {
        PreparedStatement select = null;
        ResultSet resultSet = null;

        try {
            select = connection.prepareStatement(SQL_SELECT_DUAL);

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
        }
    }
}
