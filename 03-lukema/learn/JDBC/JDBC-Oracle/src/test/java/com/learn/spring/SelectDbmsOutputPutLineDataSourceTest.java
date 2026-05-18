package com.learn.spring;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:SpringBeanConfig.xml" })
public class SelectDbmsOutputPutLineDataSourceTest {
    private static final Logger LOG = LogManager.getLogger();

    protected static final String SQL_DBMS_OUTPUT_ENABLE = "{ call sys.dbms_output.enable(?) }";

    protected static final String SQL_DBMS_OUTPUT_RETRIEVE = "{ call sys.dbms_output.get_line(?, ?) }";

    protected static final String SQL_EXEC_PROC = "{ call emp_pkg.putline() }";

    protected static final int BUFFER_SIZE = 10240;

    @Autowired
    private DataSource dataSource;

    @AfterClass
    public static void afterClass() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Hello World!");

        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            enableDbmsOutput(connection);

            execProc(connection);

            retrieveDbmsOutput(connection);

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            LOG.error("Unable to update table.", e);
        } finally {
            JdbcUtils.closeStatement(callableStatement);
            JdbcUtils.closeConnection(connection);
        }

        LOG.info("Completed.");
    }

    public void execProc(Connection connection)
        throws SQLException {
        LOG.info("Calling proc.");
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall(SQL_EXEC_PROC);
            callableStatement.execute();
        } finally {
            JdbcUtils.closeStatement(callableStatement);
        }
        LOG.info("Call proc Completed.");
    }

    public void enableDbmsOutput(Connection connection)
        throws SQLException {
        LOG.info("Enabling DBMS_OUTPUT...");
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall(SQL_DBMS_OUTPUT_ENABLE);
            callableStatement.setInt(1, BUFFER_SIZE);
            callableStatement.execute();
        } finally {
            JdbcUtils.closeStatement(callableStatement);
        }
        LOG.info("DBMS_OUTPUT Enabled!");
    }

    public void retrieveDbmsOutput(Connection connection)
        throws SQLException {
        LOG.info("Retrieving DBMS_OUTPUT!");
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall(SQL_DBMS_OUTPUT_RETRIEVE);
            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(2, java.sql.Types.NUMERIC);

            for (callableStatement.execute(); (callableStatement.getInt(2)) == 0; callableStatement.execute()) {
                System.out.println("DBMS_OUTPUT: " + callableStatement.getString(1));
            }
        } finally {
            JdbcUtils.closeStatement(callableStatement);
        }
        LOG.info("End of DBMS_OUTPUT!");
    }
}
