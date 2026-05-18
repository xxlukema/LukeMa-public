package com.learn.common.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MbaUtils {
    public static final SimpleDateFormat IN_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    private static final TimeZone TZ = TimeZone.getTimeZone("America/Chicago");

    private static final File csvDirFile = new File("target/data");

    private static boolean initialized = false;

    public static final DecimalFormat DecimalFormat = new DecimalFormat("#,##0.00");

    public static final DecimalFormat LongFormat = new DecimalFormat("#,###");

    public static final String DefaultSymbol = "pba";

    public static final int MaxZoomTimes = 4;

    public static final int MinDays = 66;

    public static final int MaxDays = MinDays * MaxZoomTimes;

    public static final String SPACE = " ";

    private static final Pattern REGEXP_SYMBOL_lowercase = Pattern
            .compile("^[\\^]{0,1}[a-z0-9]{1,5}[\\.]{0,1}[a-z0-9]{0,3}$");

    ////////////////////////////////////////
    public static void initDataDir() {
        if (!initialized) {
            synchronized (MbaUtils.class) {
                if (!initialized) {
                    if (csvDirFile.exists() && !csvDirFile.isDirectory()) {
                        csvDirFile.delete();
                    }

                    if (!csvDirFile.exists()) {
                        csvDirFile.mkdirs();
                    }

                    initialized = true;
                }

                Properties properties = System.getProperties();
                String userName = properties.getProperty("user.name");
                if (!("lukema".equals(userName))) {
                    properties.put("http.proxyHost", "proxy.wellsfargo.com");
                    properties.put("http.proxyPort", "8080");
                }
            }
        }
    }

    public static int[] getDateArray(int days, int deltaX) {
        int[] dateArray = new int[days];

        for (int i = 0; i < days; i++) {
            dateArray[i] = ChartConstants.X0Position + i * deltaX;
        }

        return dateArray;
    }

    public static int[] toArray(int[] intArray, int skipDays) {
        int[] array = new int[intArray.length - skipDays];
        for (int i = 0; i < array.length; i++) {
            array[i] = intArray[skipDays + i];
        }

        return array;
    }

    public static float[] toArray(float[] floatArray, int skipDays) {
        float[] array = new float[floatArray.length - skipDays];
        for (int i = 0; i < array.length; i++) {
            array[i] = floatArray[skipDays + i];
        }

        return array;
    }

    public static float limitValueToMinMax(float value, float min, float max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }

    public static String formalizeSysmbol(String symbol)
        throws Exception {
        if (symbol == null) {
            throw new Exception("Symbol can not be null.");
        }

        symbol = symbol.trim();

        if (symbol.length() == 0 || symbol.length() > 9) {
            throw new Exception("Symbol must be 1-9 chars long.");
        }

        symbol = symbol.toLowerCase();

        Matcher matcher = REGEXP_SYMBOL_lowercase.matcher(symbol);

        if (!matcher.matches()) {
            throw new Exception("Symbol is not valid: " + symbol);
        }

        int dotPos = symbol.indexOf('.');
        if (dotPos > -1) {
            if (dotPos == symbol.length() - 1 || dotPos == 0 || symbol.startsWith("^.")) {
                throw new Exception("Symbol can not begin or end with '.'");
            }
        }

        return symbol;
    }

    public static File getCsvFile(String symbol) {
        String csvFileName = symbol + ".csv";

        File csvFile = new File(csvDirFile, csvFileName);

        return csvFile;
    }

    public static File getDivCsvFile(String symbol) {
        String csvFileName = symbol + ".div.csv";

        File csvFile = new File(csvDirFile, csvFileName);

        return csvFile;
    }

    public static boolean isCsvFileUptodate(File csvFile) {
        if (!csvFile.exists()) {
            return false;
        }

        GregorianCalendar benchmarkCalendar = new GregorianCalendar(TZ);
        int hour = benchmarkCalendar.get(Calendar.HOUR_OF_DAY);

        if (benchmarkCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            benchmarkCalendar.add(Calendar.DAY_OF_YEAR, -1);
        } else if (benchmarkCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            benchmarkCalendar.add(Calendar.DAY_OF_YEAR, -2);
        } else if (hour < 16) {
            if (benchmarkCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                benchmarkCalendar.add(Calendar.DAY_OF_YEAR, -3);
            } else {
                benchmarkCalendar.add(Calendar.DAY_OF_YEAR, -1);
            }
        }

        Date dateInCsvFile = benchmarkCalendar.getTime();

        return checkDataFromCsvFileForUptodateWithTodayDate(csvFile, dateInCsvFile);
    }

    private static boolean checkDataFromCsvFileForUptodateWithTodayDate(File csvFile, Date dateInCsvFile) {
        String nowString = IN_SIMPLE_DATE_FORMAT.format(dateInCsvFile);

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            fileReader = new FileReader(csvFile);
            bufferedReader = new BufferedReader(fileReader);
            int counter = 0;
            for (String line = null; (line = bufferedReader.readLine()) != null;) {
                if (!line.startsWith(nowString)) {
                    counter++;
                } else {
                    return true;
                }

                if (counter > 4) {
                    break;
                }
            }
        } catch (Exception e) {
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                }
            }

            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                }
            }
        }

        return false;
    }

}
