package com.learn.core.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.learn.common.util.MbaUtils;


public class HistoryQuoteGetter {
    protected static final Logger LOG = Logger.getLogger(HistoryQuoteGetter.class);

    private static final HistoryQuoteGetter instance = new HistoryQuoteGetter();

    private static final String yahooUrl = "http://ichart.finance.yahoo.com/table.csv?s=";

    private static final String monthFormat = "MM";
    private static final String dayFormat = "d";
    private static final String yearFormat = "yyyy";

    private HistoryQuoteGetter() {
        MbaUtils.initDataDir();
    }

    private static String getMonth(Calendar calendar) {
        Calendar newCalendar = new GregorianCalendar();
        newCalendar.setTimeInMillis(calendar.getTimeInMillis());
        String month = new SimpleDateFormat(monthFormat).format(newCalendar.getTime());
        if (month.equals("01")) {
            return "00";
        } else {
            newCalendar.add(Calendar.MONTH, -1);
            return new SimpleDateFormat(monthFormat).format(newCalendar.getTime());
        }
    }

    private static String getDay(Calendar calendar) {
        return new SimpleDateFormat(dayFormat).format(calendar.getTime());
    }

    private static String getYear(Calendar calendar) {
        return new SimpleDateFormat(yearFormat).format(calendar.getTime());
    }

    private static String getDateString() {
        Calendar today = new GregorianCalendar();
        String endDate = "&d=" + getMonth(today) + "&e=" + getDay(today) + "&f=" + getYear(today);

        Calendar fourYearsToday = new GregorianCalendar();
        fourYearsToday.add(Calendar.YEAR, -4);

        String startDate = "&a=" + getMonth(fourYearsToday) + "&b=" + getDay(fourYearsToday) + "&c="
                + getYear(fourYearsToday);

        return startDate + endDate;
    }

    public static String getYahooUrlDataString(String symbol) {
        return yahooUrl + symbol.toUpperCase() + getDateString() + "&g=d&ignore=.csv";
    }

    public static String getYahooDividendDataString(String symbol) {
        return yahooUrl + symbol.toUpperCase() + getDateString() + "&g=v&ignore=.csv";
    }

    public void getDataFromYahoo(String symbol)
        throws Exception {
        symbol = MbaUtils.formalizeSysmbol(symbol);
        File csvFile = MbaUtils.getCsvFile(symbol);
        MbaUtils.initDataDir();

        String urlString = getYahooUrlDataString(symbol);
        LOG.info("[get] Getting: " + urlString);

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        URLConnection urlConnection = null;

        try {
            URL url = new URL(urlString);
            urlConnection = url.openConnection();
        } catch (Exception e) {
            LOG.error("Unable to connect to data server.", e);

            throw e;
        }

        try {
            inputStream = urlConnection.getInputStream();
            fileOutputStream = new FileOutputStream(csvFile, false);

            byte[] buf = new byte[1000];

            int len = 0;
            while ((len = inputStream.read(buf, 0, buf.length)) > 0) {
                fileOutputStream.write(buf, 0, len);
            }

            LOG.info("[get] Succeed.");
        } catch (Exception e) {
            LOG.error("[get] Failed: Unable to read data from data server: " + e.getMessage());

            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable t) {
                    LOG.error("[get] Unable to close InputStream from data server.");
                }
            }

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Throwable t) {
                    LOG.error("[get] Unable to close FileOutputStream.");
                }
            }
        }
    }

    public static HistoryQuoteGetter getInstance() {
        return instance;
    }
}
