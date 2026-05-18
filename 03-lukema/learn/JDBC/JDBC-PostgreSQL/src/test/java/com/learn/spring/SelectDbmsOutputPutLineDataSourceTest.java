package com.learn.spring;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.log4j.Log4j2;


@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration({ "classpath:SpringBeanConfig.xml" })
class SelectDbmsOutputPutLineDataSourceTest {

    protected static final String SQL_DBMS_OUTPUT_ENABLE = "{ call sys.dbms_output.enable(?) }";

    protected static final String SQL_DBMS_OUTPUT_RETRIEVE = "{ call sys.dbms_output.get_line(?, ?) }";

    protected static final String SQL_EXEC_PROC = "{ call emp_pkg.putline() }";

    protected static final int BUFFER_SIZE = 10240;

    @Autowired
    private DataSource dataSource;

    @Test
    void runTest()
        throws Exception {
        log.info("Hello World!");

        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            enableDbmsOutput(connection);

            execProc(connection);

            retrieveDbmsOutput(connection);

            connection.commit();
        } catch (SQLException e) {
            log.error("Unable to update table.", e);
        } finally {
            JdbcUtils.closeStatement(callableStatement);
            JdbcUtils.closeConnection(connection);
        }

        log.info("Completed.");
    }

    public void execProc(Connection connection)
        throws SQLException {
        log.info("Calling proc.");
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall(SQL_EXEC_PROC);
            callableStatement.execute();
        } finally {
            JdbcUtils.closeStatement(callableStatement);
        }
        log.info("Call proc Completed.");
    }

    public void enableDbmsOutput(Connection connection)
        throws SQLException {
        log.info("Enabling DBMS_OUTPUT...");
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall(SQL_DBMS_OUTPUT_ENABLE);
            callableStatement.setInt(1, BUFFER_SIZE);
            callableStatement.execute();
        } finally {
            JdbcUtils.closeStatement(callableStatement);
        }
        log.info("DBMS_OUTPUT Enabled!");
    }

    public void retrieveDbmsOutput(Connection connection)
        throws SQLException {
        log.info("Retrieving DBMS_OUTPUT!");
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
        log.info("End of DBMS_OUTPUT!");
    }
}
