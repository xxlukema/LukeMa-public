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


public class FixStageTableTest
{
    protected static final Logger LOG              = Logger.getLogger(FixStageTableTest.class);

    protected static final String SQL_STAGE_SELECT = "select COB_DATE, INDEX_NAME, CURRENCY, CLIENT_ACCOUNT_ID from CS_STG_LCH_IA";

    protected static final String SQL_STAGE_UPDATE = "update CS_STG_LCH_IA set CLIENT_ACCOUNT_ID = ? where COB_DATE = ? and INDEX_NAME = ? and CURRENCY = ? and CLIENT_ACCOUNT_ID = ?";

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

            select = connection.prepareStatement(SQL_STAGE_SELECT);
            update = connection.prepareStatement(SQL_STAGE_UPDATE);

            resultSet = select.executeQuery();

            while (resultSet.next())
            {
                Date COB_DATE = resultSet.getDate("COB_DATE");
                String INDEX_NAME = resultSet.getString("INDEX_NAME");
                String CURRENCY = resultSet.getString("CURRENCY");
                String CLIENT_ACCOUNT_ID = resultSet.getString("CLIENT_ACCOUNT_ID");

                String CLIENT_ACCOUNT_ID_NEW = CLIENT_ACCOUNT_ID.trim();
                if (CLIENT_ACCOUNT_ID.length() > CLIENT_ACCOUNT_ID_NEW.length())
                {
                    update.setString(1, CLIENT_ACCOUNT_ID_NEW);
                    update.setDate(2, COB_DATE);
                    update.setString(3, INDEX_NAME);
                    update.setString(4, CURRENCY);
                    update.setString(5, CLIENT_ACCOUNT_ID);
                    
                    int rows = update.executeUpdate();
                    count += rows;
                    if (rows == 1)
                    {
                        LOG.info("Updated: " + COB_DATE + " " + CLIENT_ACCOUNT_ID);
                    }
                    else
                    {
                        LOG.error("### Row not found: " + COB_DATE + " " + CLIENT_ACCOUNT_ID);
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
