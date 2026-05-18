package com.learn.spring;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.support.JdbcUtils;

import com.learn.util.SpringApplicationContext;


public class CallProcTest {
    protected static final Logger LOG = Logger.getLogger(CallProcTest.class);

    private Connection connection = null;

    //@Test
    @Ignore
    public void selectProcReportsWatchList()
        throws Exception {
        LOG.info("First Line.");

        String proc = "{ call CMSA.SP_CMSA_REPORTS_WATCHLIST(?, ?, ?, ?, ?, ?, ?) }";

        int investorId = 771;
        //int investorId = 588;
        int month = 12;
        int year = 2012;

        CallableStatement callableStatement = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        int rowCount = 0;

        try {
            callableStatement = connection.prepareCall(proc);

            callableStatement.setInt(1, investorId);
            callableStatement.setInt(2, month);
            callableStatement.setInt(3, year);

            callableStatement.registerOutParameter(4, OracleTypes.VARCHAR);
            callableStatement.registerOutParameter(5, OracleTypes.INTEGER);
            callableStatement.registerOutParameter(6, OracleTypes.CURSOR);
            callableStatement.registerOutParameter(7, OracleTypes.CURSOR);

            LOG.info("Before extecute().");
            callableStatement.execute();
            LOG.info("After extecute().");

            int statusCode = callableStatement.getInt(5);
            LOG.info("statusCode = " + statusCode);

            if (statusCode != 0) {
                LOG.error("ERROR with " + proc);
            } else {
                String asOfDate = callableStatement.getString(4);
                LOG.info("asOfDate = " + asOfDate);

                LOG.info("Executing SP_CMSA_REPORTS_WATCHLIST. Please wait...");

                resultSet1 = (ResultSet) callableStatement.getObject(6);

                while (resultSet1.next()) {
                    rowCount++;

                    String str = resultSet1.getString("TRANSACTION_ID");
                    LOG.info("TRANSACTION_ID: " + str);

                    Integer num = resultSet1.getInt("LOAN_ID");
                    LOG.info("LOAN_ID: " + num);

                    str = resultSet1.getString("PROSPECTUS_LOAN_ID");
                    LOG.info("PROSPECTUS_LOAN_ID: " + str);

                }

                Assert.assertTrue("rowCount is larger than zero", (rowCount > 0));

                resultSet2 = (ResultSet) callableStatement.getObject(7);

                while (resultSet2.next()) {
                    rowCount++;

                    Float value = resultSet2.getFloat("END_SCH_BAL");
                    LOG.info("END_SCH_BAL: " + value);

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
        } finally {
            JdbcUtils.closeStatement(callableStatement);
            JdbcUtils.closeResultSet(resultSet1);
            JdbcUtils.closeResultSet(resultSet2);
        }

        LOG.info("Completed. rowCount = " + rowCount);
    }

    //@Test
    @Ignore
    public void selectProcReportWatchListLoad()
        throws Exception {
        LOG.info("First Line.");

        String proc = "{ call CMSA.SP_CMSA_REPORT_WATCHLIST_LOAD(?, ?, ?, ?, ?) }";

        //int investorId = 769;
        int investorId = 588;
        int month = 11;
        int year = 2012;

        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        int rowCount = 0;

        try {
            callableStatement = connection.prepareCall(proc);

            callableStatement.setInt(1, investorId);
            callableStatement.setInt(2, month);
            callableStatement.setInt(3, year);

            callableStatement.registerOutParameter(4, OracleTypes.INTEGER);
            callableStatement.registerOutParameter(5, OracleTypes.CURSOR);

            LOG.info("Before extecute().");
            callableStatement.execute();
            LOG.info("After extecute().");

            int statusCode = callableStatement.getInt(4);
            LOG.info("statusCode = " + statusCode);

            if (statusCode != 0) {
                LOG.error("ERROR with " + proc);
            } else {
                resultSet = (ResultSet) callableStatement.getObject(5);

                while (resultSet.next()) {
                    rowCount++;

                    String str = resultSet.getString("TRANSACTION_ID");
                    LOG.info("TRANSACTION_ID: " + str);

                    Integer num = resultSet.getInt("LOAN_ID");
                    LOG.info("LOAN_ID: " + num);

                    str = resultSet.getString("PROSPECTUS_LOAN_ID");
                    LOG.info("PROSPECTUS_LOAN_ID: " + str);

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
        } finally {
            JdbcUtils.closeStatement(callableStatement);
            JdbcUtils.closeResultSet(resultSet);
        }

        LOG.info("Completed. rowCount = " + rowCount);
    }

    //@Test
    @Ignore
    public void selectProc()
        throws Exception {
        LOG.info("First Line.");

        String proc = "{ call CMSA.SP_CMSA_REPORT_WATCHLIST_LOAD(?, ?, ?, ?, ?) }";

        //int investorId = 769;
        int investorId = 588;
        int month = 11;
        int year = 2012;

        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        int rowCount = 0;

        try {
            callableStatement = connection.prepareCall(proc);

            callableStatement.setInt(1, investorId);
            callableStatement.setInt(2, month);
            callableStatement.setInt(3, year);

            callableStatement.registerOutParameter(4, OracleTypes.INTEGER);
            callableStatement.registerOutParameter(5, OracleTypes.CURSOR);

            LOG.info("Before extecute().");
            callableStatement.execute();
            LOG.info("After extecute().");

            int statusCode = callableStatement.getInt(4);
            LOG.info("statusCode = " + statusCode);

            if (statusCode != 0) {
                LOG.error("ERROR with " + proc);
            } else {
                resultSet = (ResultSet) callableStatement.getObject(5);

                while (resultSet.next()) {
                    rowCount++;

                    String str = resultSet.getString("TRANSACTION_ID");
                    LOG.info("TRANSACTION_ID: " + str);

                    Integer num = resultSet.getInt("LOAN_ID");
                    LOG.info("LOAN_ID: " + num);

                    str = resultSet.getString("PROSPECTUS_LOAN_ID");
                    LOG.info("PROSPECTUS_LOAN_ID: " + str);

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
        } finally {
            JdbcUtils.closeStatement(callableStatement);
            JdbcUtils.closeResultSet(resultSet);
        }

        LOG.info("Completed. rowCount = " + rowCount);
    }

    @Test
    //@Ignore
    public void selectDual()
        throws Exception {
        LOG.info("selectDual()");

        String selectFromDual = "select sysdate from dual";

        PreparedStatement select = null;
        ResultSet resultSet = null;

        try {
            connection.setAutoCommit(false);

            select = connection.prepareStatement(selectFromDual);

            LOG.info("Before executeQuery().");
            resultSet = select.executeQuery();
            LOG.info("After executeQuery().");

            while (resultSet.next()) {
                Date date = resultSet.getDate("sysdate");

                LOG.info("Date: " + date);
            }

            // connection.commit();
        } catch (Exception e) {
            connection.rollback();
            LOG.error("Unable to update table.", e);
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(select);
        }

        LOG.info("Completed.");
    }

    @Before
    public void before()
        throws Exception {
        LOG.info("Connecting to database...");
        DataSource dataSource = SpringApplicationContext.getBean("dataSource");
        connection = dataSource.getConnection();
        LOG.info("Connected to database.");
    }

    @After
    public void after()
        throws Exception {
        JdbcUtils.closeConnection(connection);
        LOG.info("Connection closed.");
    }

}
