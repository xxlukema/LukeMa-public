package com.learn.b.swing.all.raw;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.learn.b.swing.all.common.TraderBase;
import com.learn.b.swing.all.concert.SharedValue;
import com.learn.b.swing.all.concert.ZoomSize;
import com.learn.b.swing.all.oil.OilOHLC;

import lombok.extern.log4j.Log4j2;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;


@Log4j2
public class RawDataUtils {

    private static final String YEAR_BEGIN_DATE_FORMAT = "yyyy";
    private static final SimpleDateFormat YEAR_BEGIN_SIMPLE_DATE_FORMAT = new SimpleDateFormat(YEAR_BEGIN_DATE_FORMAT, Locale.US);
    private static Date YEAR_BEGIN;
    private static Document CONFIG_DOC;
    private static int CHART_DAYS = 0;
    private static boolean inited = false;
    private static final List<RawData> DATA = new ArrayList<>();
    private static final List<String> ERROR_MESSAGE_LIST = new ArrayList<>();
    private static String CONFIG_FILE_NAME = "mba.chart.config.xml";

    private static void init() {
        synchronized (RawDataUtils.class) {
            if (inited) {
                return;
            }

            inited = true;

            try {
                URL url = getResource(CONFIG_FILE_NAME);

                SAXReader reader = new SAXReader();
                CONFIG_DOC = reader.read(url);

                String yr = getConfigData("//Config/BeginYear");

                if (yr != null) {
                    yr = yr.trim();

                    YEAR_BEGIN = YEAR_BEGIN_SIMPLE_DATE_FORMAT.parse(yr);
                }
            } catch (Throwable t) {
                log.error("Unable to read config file: " + CONFIG_FILE_NAME, t);
            }

            if (YEAR_BEGIN == null) {
                try {
                    YEAR_BEGIN = YEAR_BEGIN_SIMPLE_DATE_FORMAT.parse("1000");
                } catch (Throwable t) {
                    log.error("Unable to parse year.", t);
                }
            }

            retrieveChartDays();
        }
    }

    private static void retrieveChartDays() {
        String chartDaysStr = null;

        if (SharedValue.ZOOM_SIZE == ZoomSize.FAR) {
            chartDaysStr = RawDataUtils.getConfigData("//Config/Zoom/FarDays");
        } else if (SharedValue.ZOOM_SIZE == ZoomSize.CLOSE) {
            chartDaysStr = RawDataUtils.getConfigData("//Config/Zoom/CloseDays");
        } else {
            chartDaysStr = RawDataUtils.getConfigData("//Config/Zoom/MediumDays");
        }

        int chartDaysInt = 0;
        if (chartDaysStr != null) {
            try {
                chartDaysInt = Integer.parseInt(chartDaysStr.trim());
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        if (chartDaysInt == 0) {
            CHART_DAYS = 60;
        } else {
            // CHART_DAYS = Math.min(chartDaysInt, RawDataUtils.getData().size());
            CHART_DAYS = chartDaysInt;
        }
    }

    public static List<RawData> getData() {
        init();
        return DATA;
    }

    public static void retrieveDataFromRest(String symbol)
        throws Exception {

        DATA.clear();

        init();

        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.MONTH, -18); // from 18 month ago

        /*
        public static final String BASE_URL = "http://localhost:8080";
        private static String URI_TEMPLATE = BASE_URL + "/spring/getguote/%s/%d/%d";
        private static final RestTemplate Rest_Template = new RestTemplate();

        String uri = String.format(URI_TEMPLATE, symbol, from.getTimeInMillis(), to.getTimeInMillis());
        log.info("URI: " + uri);
        ResponseEntity<HistoricalQuote[]> response = Rest_Template.exchange(uri, HttpMethod.GET, null, HistoricalQuote[].class);
        HistoricalQuote[] historicalQuotes = response.getBody();
        */

        // log.debug(() -> "Retrieving data from YahooFinance...");

        Stock stock = YahooFinance.get(symbol, from, to, Interval.DAILY);

        if (stock == null) {
            log.error("Unable to retrieve data from YahooFinance. Check symbol.");
            throw new Exception("Invalid stock symbol");
        }

        List<HistoricalQuote> historicalQuotes = stock.getHistory();

        log.debug("Data received from YahooFinance. Size: {}", () -> historicalQuotes.size());

        historicalQuotes.forEach((historicalQuote) -> {
            RawData data = new RawData();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            data.setDate(simpleDateFormat.format(historicalQuote.getDate().getTime()));
            data.setOpen(historicalQuote.getOpen().floatValue());
            data.setHigh(historicalQuote.getHigh().floatValue());
            data.setLow(historicalQuote.getLow().floatValue());
            data.setClose(historicalQuote.getClose().floatValue());
            data.setVolume(historicalQuote.getVolume() / 1000);
            data.setAdjustedClose(historicalQuote.getAdjClose().floatValue());

            adjust(data);

            DATA.add(data);
        });
    }

    private static String getConfigData(String xpath) {
        Node node = CONFIG_DOC.selectSingleNode(xpath);

        String value = node.getStringValue();

        if (value == null) {
            return null;
        } else {
            return value.trim();
        }
    }

    private static URL getResource(String fileName)
        throws Exception {
        if (fileName == null) {
            throw new Exception("File name is null.");
        }

        fileName = fileName.trim();

        if (fileName.length() == 0) {
            throw new Exception("File name is empty.");
        }

        URL url = null;

        ClassLoader cl = RawDataUtils.class.getClassLoader();
        if (cl != null) {
            url = cl.getResource(fileName);
        } else {
            log.error("ClassLoader is null: " + RawDataUtils.class.getName());
        }

        if (url == null) {
            cl = ClassLoader.getSystemClassLoader();
            if (cl != null) {
                url = cl.getResource(fileName);
            } else {
                log.error("System ClassLoader is null.");
            }
        }

        if (url == null) {
            throw new Exception("Resource URL is null: " + fileName);
        }

        return url;
    }

    public static void readOilData() {
        boolean isDateAscendinglySorted = false;

        String quote = OilOHLC.retrieveData();

        if (quote == null || quote.length() == 0) {
            return;
        }

        try {
            String[] lines = quote.split("[|]");

            SimpleDateFormat oilInFormat = new SimpleDateFormat(TraderBase.OIL_DATE_FORMAT, Locale.US);
            SimpleDateFormat rawDataFormat = TraderBase.IN_SIMPLE_DATE_FORMAT;

            String line = null;

            for (int i = 0; i < lines.length; i++) {
                line = lines[i].trim();

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
                    Date tmpDate = oilInFormat.parse(date);
                    date = rawDataFormat.format(tmpDate);

                    if (!isInYearRange(date)) {
                        continue;
                    }

                    String open = fields[1].trim();
                    String high = fields[2].trim();
                    String low = fields[3].trim();
                    String close = fields[4].trim();
                    String volume = fields[5].trim();
                    String adjClose = fields[4].trim();

                    data.setDate(date);
                    data.setOpen(Float.parseFloat(open));
                    data.setHigh(Float.parseFloat(high));
                    data.setLow(Float.parseFloat(low));
                    data.setClose(Float.parseFloat(close));
                    data.setVolume(Integer.parseInt(volume));
                    data.setAdjustedClose(Float.parseFloat(adjClose));

                    adjust(data);

                    data.setValid(true);
                } else {
                    data.setErrorMessage(line);
                    ERROR_MESSAGE_LIST.add(line);
                    data.setValid(false);
                }

                if (data.isValid()) {
                    if (isDateAscendinglySorted) {
                        // Wilder's data is sorted as the earliest date data as the first line.
                        DATA.add(data);
                    } else {
                        // Yahoo data is sorted as the latest date data as the first line.
                        DATA.add(0, data);
                    }
                } else {
                    ERROR_MESSAGE_LIST.add(line);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
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
        int pos = date.indexOf("-");
        if (pos != 4) {
            return false;
        }

        String yy = date.substring(0, 4);

        Date year = null;

        try {
            year = YEAR_BEGIN_SIMPLE_DATE_FORMAT.parse(yy);
        } catch (Throwable t) {
            t.printStackTrace();

            return false;
        }

        return (year.compareTo(YEAR_BEGIN) >= 0);
    }

    public static int getCHART_DAYS() {
        init();

        return CHART_DAYS;
    }

}
