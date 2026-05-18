package com.learn;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.jdbc.support.JdbcUtils;

import com.learn.util.SpringApplicationContext;


public class FixTargetTableTest
{
    protected static final Logger LOG               = Logger.getLogger(FixTargetTableTest.class);

    protected static final String SQL_TARGET_SELECT = "select COB_DATE, CH_ACCT_NBR from CS_CH_IA";

    protected static final String SQL_TARGET_UPDATE = "update CS_CH_IA set CH_ACCT_NBR = ? where COB_DATE = ? and CH_ACCT_NBR = ?";

    @Test
    public void runTest()
        throws Exception
    {
        LOG.info("Hello World!");

        DataSource dataSource = SpringApplicationContext.getBean("dataSource");

        Connection connection = null;
        PreparedStatement select = null;
        PreparedStatement update = null;
        ResultSet resultSet = null;
        int count = 0;

        try
        {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            select = connection.prepareStatement(SQL_TARGET_SELECT);
            update = connection.prepareStatement(SQL_TARGET_UPDATE);

            resultSet = select.executeQuery();

            while (resultSet.next())
            {
                Date COB_DATE = resultSet.getDate("COB_DATE");
                String CH_ACCT_NBR = resultSet.getString("CH_ACCT_NBR");
                String CH_ACCT_NBR_NEW = CH_ACCT_NBR.trim();
                if (CH_ACCT_NBR.length() > CH_ACCT_NBR_NEW.length())
                {
                    update.setString(1, CH_ACCT_NBR_NEW);
                    update.setDate(2, COB_DATE);
                    update.setString(3, CH_ACCT_NBR);
                    
                    int rows = update.executeUpdate();
                    count += rows;
                    if (rows == 1)
                    {
                        LOG.info("Updated: " + COB_DATE + " " + CH_ACCT_NBR);
                    }
                    else
                    {
                        LOG.error("### Row not found: " + COB_DATE + " " + CH_ACCT_NBR_NEW);
                    }
                }
            }

            connection.commit();
        }
        catch (Exception e)
        {
            connection.rollback();

            LOG.error("Unable to update table.", e);
        }
        finally
        {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(select);
            JdbcUtils.closeStatement(update);
            JdbcUtils.closeConnection(connection);
        }

        LOG.info("Completed. Rows updated: " + count);
    }

}
