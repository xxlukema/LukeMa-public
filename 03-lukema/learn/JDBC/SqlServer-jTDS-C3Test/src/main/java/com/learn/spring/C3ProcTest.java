package com.learn.spring;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.support.JdbcUtils;

import com.learn.util.SpringApplicationContext;


public class C3ProcTest {
    protected static final Logger LOG = Logger.getLogger(C3ProcTest.class);

    private Connection connDev = null;

    private Connection connSit = null;

    @Test
    public void execProc()
        throws Exception {
        LOG.info("execProc()");

        /** @Client INT, @Calendar NVARCHAR(20) **/
         String execProc = "{call USP_RptOSARWorkFlow(?, ?)}" ;

        //String execProc = "{call USP_ZZ_Test(?, ?)}";

        CallableStatement stmt = null;
        ResultSet resultSet = null;

        try {
            connSit.setAutoCommit(false);

            stmt = connSit.prepareCall(execProc);
            stmt.setInt(1, 2); // Wells Fargo
            stmt.setInt(2, 118); // 2012-09-30 00:00:00

            LOG.info("Before executeQuery().");
            resultSet = stmt.executeQuery();
            LOG.info("After executeQuery().");

            while (resultSet.next()) {
                Date date = resultSet.getDate("Calendar");

                LOG.info("Calendar: " + date);
            }

            // connection.commit();
        } catch (Exception e) {
            connSit.rollback();
            LOG.error("Unable to update table.", e);
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(stmt);
        }

        LOG.info("Completed.");
    }

    @Ignore
    public void selectDual()
        throws Exception {
        LOG.info("selectDual()");

        String selectFromDual = "select Calendar from Calendar";

        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            connSit.setAutoCommit(false);

            stmt = connSit.prepareStatement(selectFromDual);

            LOG.info("Before executeQuery().");
            resultSet = stmt.executeQuery();
            LOG.info("After executeQuery().");

            while (resultSet.next()) {
                Date date = resultSet.getDate("Calendar");

                LOG.info("Calendar: " + date);
            }

            // connection.commit();
        } catch (Exception e) {
            connSit.rollback();
            LOG.error("Unable to update table.", e);
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(stmt);
        }

        LOG.info("Completed.");
    }

    @Before
    public void before()
        throws Exception {
        LOG.info("Connecting to dev database...");
        DataSource dataSource = SpringApplicationContext.getBean("dataSource-dev");
        connDev = dataSource.getConnection();
        LOG.info("Connected to dev database.");
        connDev.setAutoCommit(true);

        LOG.info("Connecting to sit database...");

        dataSource = SpringApplicationContext.getBean("dataSource-sit");
        connSit = dataSource.getConnection();

        LOG.info("Connected to sit database.");
        connSit.setAutoCommit(true);
    }

    @After
    public void after()
        throws Exception {
        JdbcUtils.closeConnection(connDev);
        LOG.info("Connection dev closed.");

        JdbcUtils.closeConnection(connSit);
        LOG.info("Connection sit closed.");
    }

}
