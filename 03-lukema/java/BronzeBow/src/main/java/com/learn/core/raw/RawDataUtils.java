package com.learn.core.raw;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.common.util.MbaUtils;


public class RawDataUtils {
    private static final Logger LOG = LogManager.getLogger();

    private static final int DATA_BEGIN_YEAR = 2007;

    public static Vector<RawData> getNewRawDataVector(File csvFile) {
        MbaUtils.initDataDir();

        Vector<RawData> rawDataVector = new Vector<RawData>();

        readDataFromFile(csvFile, rawDataVector);

        return rawDataVector;
    }

    private static void readDataFromFile(File csvFile, Vector<RawData> rawDataVector) {
        if (!csvFile.exists()) {
            LOG.error("File not found: " + csvFile.getAbsolutePath());

            return;
        }

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(csvFile));

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();

                if (line.indexOf("#") == 0) {
                    continue;
                }

                // String [] fields = line.split("[,| \t;]");
                String[] fields = null;
                fields = line.split(" ");

                if (fields == null || fields.length != 7) {
                    fields = line.split(",");
                }

                RawData data = new RawData();

                if (fields != null && fields.length == 7) {
                    String date = fields[0].trim();

                    if (!isInYearRange(date)) {
                        continue;
                    }

                    String open = fields[1].trim();
                    String high = fields[2].trim();
                    String low = fields[3].trim();
                    String close = fields[4].trim();
                    String volume = fields[5].trim();
                    String adjClose = fields[6].trim();

                    data.setDate(date);
                    data.setOpen(Float.parseFloat(open));
                    data.setHigh(Float.parseFloat(high));
                    data.setLow(Float.parseFloat(low));
                    data.setClose(Float.parseFloat(close));
                    data.setVolume(Long.parseLong(volume) / 1000);
                    data.setAdjustedClose(Float.parseFloat(adjClose));

                    adjust(data);

                    rawDataVector.add(0, data);
                }
                else {
                    LOG.debug("Invalid data: " + line);
                }
            }
        }
        catch (Exception e) {
            LOG.error("Unable to read from cvs file: " + csvFile.getAbsolutePath(), e);
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    LOG.error("Unable to close BufferedReader for cvs file: " + csvFile.getAbsolutePath(), e);
                }
            }
        }
    }

    private static void adjust(RawData rawData) {
        if (rawData.getClose() != rawData.getAdjustedClose()) {
            float adjust = rawData.getAdjustedClose() / rawData.getClose();

            rawData.setOpen(rawData.getOpen() * adjust);

            rawData.setHigh(rawData.getHigh() * adjust);
            rawData.setLow(rawData.getLow() * adjust);
            rawData.setClose(rawData.getClose() * adjust);
        }
    }

    private static boolean isInYearRange(String date) {
        if (date == null) {
            return false;
        }

        int pos = date.indexOf("-");
        if (pos != 4) {
            return false;
        }

        String yy = date.substring(0, 4);

        int year;

        try {
            year = Integer.parseInt(yy);
        }
        catch (Throwable t) {
            return false;
        }

        return (year >= DATA_BEGIN_YEAR);
    }

}
