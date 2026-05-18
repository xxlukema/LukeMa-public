package com.learn.spring;


import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.learn.util.JdbcConstants;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:SpringBeanConfig.xml" })
public class SelectSysdateDataSourceTest {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private DataSource dataSource;

    @BeforeClass
    public static void beforeClass() {
        LOG.info("Before Class.");

        System.setProperty("oracle.net.tns_admin", "C:/app/lukema/product/11.2.0/client_1/network/admin");

        String tnsAdmin = System.getenv("TNS_ADMIN");
        String oracleHome = System.getenv("ORACLE_HOME");

        LOG.info("TNS_ADMIN=" + tnsAdmin + ". File.separatorChar=" + File.separatorChar + ". ORACLE_HOME=" + oracleHome);
    }

    @AfterClass
    public static void afterClass() {
        LOG.info("After Class.");
    }

    @Before
    public void before() {
        LOG.info("Before Test.");
    }

    @After
    public void after() {
        LOG.info("After Test.");
    }

    @Test
    public void testSpring()
        throws Exception {
        LOG.info("Begin Test...");

        try (Connection connection = dataSource.getConnection(); PreparedStatement select = connection.prepareStatement(JdbcConstants.SQL_SELECT_SYSDATE);) {
            
            select.setFetchSize(1_000);
            // connection.setAutoCommit(false);

            try (ResultSet resultSet = select.executeQuery();) {

                while (resultSet.next()) {
                    Date date = resultSet.getDate("sysdate");

                    LOG.info("Date: " + date);
                }
            }

            // connection.commit();
        } catch (Exception e) {
            LOG.error("Unable to update table.", e);
        }

        LOG.info("End Test.");
    }
}
