package com.learn.spring;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:SpringBeanConfig.xml" })
public class SelectSysdateDataSourceTest {
    protected static final Logger LOG = Logger.getLogger(SelectSysdateDataSourceTest.class);

    protected static final String SQL_SELECT_SYSDATE = "select sysdate from dual";

    @Autowired
    private DataSource dataSource;

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Hello World!");

        Connection connection = null;
        PreparedStatement select = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            select = connection.prepareStatement(SQL_SELECT_SYSDATE);

            resultSet = select.executeQuery();

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
            JdbcUtils.closeConnection(connection);
        }

        LOG.info("Completed.");
    }
}
