package com.learn;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.junit.Test;


public class MonthEndInterestStatementQuery
{
    protected static final Logger LOG             = Logger.getLogger(MonthEndInterestStatementQuery.class);

    protected static final String OUTPUT_FILENAME = "Raw-Data.txt";

    @Test
    public void jdbcQuery()
        throws Exception
    {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            String url = "jdbc:jtds:sybase://mccciodev102.svr.us.jpmchase.net:8835;DatabaseName=OPICS_PPB";

            connection = DriverManager.getConnection(url, "coastsys", "password1");

            // Print all warnings
            for (SQLWarning warn = connection.getWarnings(); warn != null; warn = warn.getNextWarning())
            {
                LOG.info("SQL Warning:");
                LOG.info("State  : " + warn.getSQLState());
                LOG.info("Message: " + warn.getMessage());
                LOG.info("Error  : " + warn.getErrorCode());
            }

            // Get a statement from the connection
            callableStatement = connection.prepareCall("{call sp_cops_statements_rev2(?, ?)}");

            /**
             * TODO: Test month, date
             */
            int year = 2011;
            int month = Calendar.JANUARY;

            GregorianCalendar startCalendar = new GregorianCalendar();
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, month);
            startCalendar.set(Calendar.DAY_OF_MONTH, 1);

            callableStatement.setDate(1, new Date(startCalendar.getTimeInMillis()));

            LOG.info("Star date: " + new Date(startCalendar.getTimeInMillis()));

            GregorianCalendar endCalendar = new GregorianCalendar();
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, month);
            int lastDayOfMonth = endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            endCalendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);

            callableStatement.setDate(2, new Date(endCalendar.getTimeInMillis()));

            LOG.info("End date: " + new Date(endCalendar.getTimeInMillis()));

            LOG.info("callableStatement.executeQuery():");

            // Execute the query
            resultSet = callableStatement.executeQuery();

            LOG.info("Retrieving ResultSet...");

            File file = new File("target", OUTPUT_FILENAME);
            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("br\t"); //1
            stringBuilder.append("basis\t"); //2
            stringBuilder.append("bus_date\t"); //3
            stringBuilder.append("cust_short_name\t"); //4
            stringBuilder.append("cust_full_name1\t"); //5
            stringBuilder.append("cust_full_name2\t"); //6
            stringBuilder.append("accountno\t"); //7
            stringBuilder.append("int_rate\t"); //8
            stringBuilder.append("beg_balance\t"); //9
            stringBuilder.append("eod_balance\t"); //10
            stringBuilder.append("liab_dly_int\t"); //11
            stringBuilder.append("asset_dly_int\t"); //12
            stringBuilder.append("ccy\t"); //13
            stringBuilder.append("ccyamt\t"); //14
            stringBuilder.append("moveno\t"); //15
            stringBuilder.append("intflag\t"); //16
            stringBuilder.append("accr_days\t"); //17
            stringBuilder.append("accr_int\t"); //18
            stringBuilder.append("nost_acct\t"); //19
            stringBuilder.append("contact_name\t"); //20
            stringBuilder.append("cust_addr1\t"); //21
            stringBuilder.append("cust_addr2\t"); //22
            stringBuilder.append("cust_loc\t"); //23
            stringBuilder.append("cust_fax\t"); //24
            stringBuilder.append("acct_type_ind\t"); //25
            stringBuilder.append("compound"); //26
            stringBuilder.append("\n");

            bufferedWriter.write(stringBuilder.toString(), 0, stringBuilder.length());

            int rowCount = 0;
            // Loop through the result set
            while (resultSet.next())
            {
                stringBuilder = new StringBuilder();

                stringBuilder.append(resultSet.getString(1));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(2));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getDate(3));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(4));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(5));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(6));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(7));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getFloat(8));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getDouble(9));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getDouble(10));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getDouble(11));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getDouble(12));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(13));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getDouble(14));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(15));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(16));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getInt(17));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(18));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(19));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(20));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(21));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(22));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getObject(23).toString());
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(24));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(25));
                stringBuilder.append("\t");
                stringBuilder.append(resultSet.getString(26));
                stringBuilder.append("\n");

                bufferedWriter.write(stringBuilder.toString(), 0, stringBuilder.length());

                rowCount++;
            }

            bufferedWriter.close();
            fileWriter.close();

            LOG.info("Rows: " + rowCount);
        }
        catch (SQLException se)
        {
            LOG.info("SQL Exception:");

            // Loop through the SQL Exceptions
            while (se != null)
            {
                LOG.info("State  : " + se.getSQLState());
                LOG.info("Message: " + se.getMessage());
                LOG.info("Error  : " + se.getErrorCode());

                se = se.getNextException();
            }
        }
        finally
        {
            if (resultSet != null)
            {
                resultSet.close();
            }

            if (callableStatement != null)
            {
                callableStatement.close();
            }

            if (connection != null)
            {
                connection.close();
            }
        }
    }
}
