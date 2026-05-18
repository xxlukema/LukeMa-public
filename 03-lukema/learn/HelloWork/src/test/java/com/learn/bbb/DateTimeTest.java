package com.learn.bbb;


import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;


public class DateTimeTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testConvert() {
        LOG.info("Begin Test.");

        java.util.Date utilDate = new java.util.Date();
        LOG.info("Util date: " + utilDate);
        
        // java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        LOG.info("SQL date: " + sqlDate);
        
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(utilDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        // utilDate = new java.util.Date(cal.getTimeInMillis());
        utilDate = cal.getTime();
        LOG.info("Util date: " + utilDate);

        LOG.info("End Test.");
    }

    @Ignore
    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test.");

        Date date = new Date();

        LOG.info(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, Calendar.APRIL);
        date = cal.getTime();

        LOG.info(date);

        cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        date = cal.getTime();

        LOG.info(date);

        LOG.info(TimeUnit.SECONDS.toMinutes(2000));

        LOG.info("End Test.");
    }
}
