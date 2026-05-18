package com.learn.spring;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;

import lombok.extern.log4j.Log4j2;


@Log4j2
class FixTargetTableTest {

    protected static final String SQL_TARGET_SELECT = "select COB_DATE, CH_ACCT_NBR from CS_CH_IA";

    protected static final String SQL_TARGET_UPDATE = "update CS_CH_IA set CH_ACCT_NBR = ? where COB_DATE = ? and CH_ACCT_NBR = ?";

    @Autowired
    DataSource dataSource;

    @Test
    void runTest()
        throws Exception {
        log.info("Hello World!");

        Connection connection = null;
        PreparedStatement select = null;
        PreparedStatement update = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            select = connection.prepareStatement(SQL_TARGET_SELECT);
            update = connection.prepareStatement(SQL_TARGET_UPDATE);

            resultSet = select.executeQuery();

            while (resultSet.next()) {
                Date cobDate = resultSet.getDate("COB_DATE");
                String chAcctNbr = resultSet.getString("CH_ACCT_NBR");
                String chAcctNbrNew = chAcctNbr.trim();
                if (chAcctNbr.length() > chAcctNbrNew.length()) {
                    update.setString(1, chAcctNbrNew);
                    update.setDate(2, cobDate);
                    update.setString(3, chAcctNbr);

                    int rows = update.executeUpdate();
                    count += rows;
                    if (rows == 1) {
                        log.info("Updated: " + cobDate + " " + chAcctNbr);
                    } else {
                        log.error("### Row not found: " + cobDate + " " + chAcctNbrNew);
                    }
                }
            }
        } catch (SQLException e) {
            log.error("Unable to update table.", e);
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(select);
            JdbcUtils.closeStatement(update);
            JdbcUtils.closeConnection(connection);
        }

        log.info("Completed. Rows updated: " + count);
    }

}
