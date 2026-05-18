package com.learn;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.junit.Test;


public class DateTimeTest {
    private static final Logger LOG = Logger.getLogger(DateTimeTest.class);

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test.");

        LOG.info("Hello World! 2");

        TimeZone tz = Calendar.getInstance().getTimeZone();

        LOG.info("TimeZone = " + tz);
        LOG.info("TimeZone ID= " + tz.getID());

        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        LOG.info("date = " + date);

        LOG.info("--------------------");

        //final String format = "'ddd'MM/dd/yyyyy hh:mm:ss.mmm";
        final String format = "'&a='MM'&b='d'&c='yyyy'&g=d&ignore=.csv'";
        java.util.Date date2 = new java.util.Date();
        LOG.info("date2 = " + date2);
        LOG.info("ddd date2 = " + new SimpleDateFormat(format).format(date2));

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date2);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        date2 = calendar.getTime();
        LOG.info("New date2 = " + date2);
        LOG.info("date2 = " + new SimpleDateFormat(format).format(date2));

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        java.util.Date date3 = calendar.getTime();
        LOG.info("date3 = " + date3);
        LOG.info("date2 = " + new SimpleDateFormat(format).format(date3));

        LOG.info("End Test.");
    }
}
