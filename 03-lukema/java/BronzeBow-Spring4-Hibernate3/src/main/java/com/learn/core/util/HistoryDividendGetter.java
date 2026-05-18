package com.learn.core.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import com.learn.common.util.MbaUtils;


public class HistoryDividendGetter {
    protected static final Logger LOG = Logger.getLogger(HistoryDividendGetter.class);

    private static final HistoryDividendGetter instance = new HistoryDividendGetter();

    private HistoryDividendGetter() {
        MbaUtils.initDataDir();
    }

    public void getDataFromYahoo(String symbol)
        throws Exception {
        symbol = MbaUtils.formalizeSysmbol(symbol);
        File divCsvFile = MbaUtils.getDivCsvFile(symbol);
        MbaUtils.initDataDir();

        String urlString = HistoryQuoteGetter.getYahooDividendDataString(symbol);
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
            fileOutputStream = new FileOutputStream(divCsvFile, false);

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

    public static HistoryDividendGetter getInstance() {
        return instance;
    }
}
