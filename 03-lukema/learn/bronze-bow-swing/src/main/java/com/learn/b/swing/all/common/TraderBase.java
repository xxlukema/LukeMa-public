package com.learn.b.swing.all.common;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class TraderBase {

    private static boolean isVerbose = false;
    private static boolean isPredictingTomorrow = false;
    private static boolean isOilData = false;
    private static String delimit = " ";
    private static String symbol = null;
    private float earning = 0;
    private boolean longing = false;
    private boolean shorting = false;
    private float buyPrice = 0;
    private float sellPrice = 0;
    private float capital = 0;
    private String startDate = null;
    private int transactionCtr = 0;
    public static final float CUT_LOSS_REQUIREMENT = 0.08f;
    public static final String IN_DATE_FORMAT = "yyyy-MM-dd";
    public static final String OIL_DATE_FORMAT = "MM/dd/yyyy";
    public static final String OUT_DATE_FORMAT = "dd-MMM-yyyy";
    public static final String OUT_WEEK_FORMAT = "EEE";
    public static final String OUT_HOUR_FORMAT = "HH";
    public static final String OUT_WEEK_IN_YEAR_FORMAT = "ww";
    public static final String OUT_DAY_IN_YEAR_FORMAT = "DDD";
    public static final SimpleDateFormat IN_SIMPLE_DATE_FORMAT = new SimpleDateFormat(IN_DATE_FORMAT, Locale.US);

    public static String getSymbol() {
        return symbol;
    }

    public static void setSymbol(String symbol) {
        TraderBase.symbol = symbol;
    }

    public static boolean isVerbose() {
        return isVerbose;
    }

    public void setLonging(boolean longing) {
        this.longing = longing;
    }

    public boolean isLonging() {
        return longing;
    }

    public void setShorting(boolean shorting) {
        this.shorting = shorting;
    }

    public boolean isShorting() {
        return shorting;
    }

    public static String checkDataUptodate() {
        if (!isVerbose()) {
            return null;
        }

        boolean isUptodate = false;

        Date lastDay = retrieveLastDate();
        Date now = Calendar.getInstance().getTime();

        int lastDayDayOfYear = parseInt(lastDay, OUT_DAY_IN_YEAR_FORMAT);
        String lastDayWeek = parseString(lastDay, OUT_WEEK_FORMAT);
        int nowDayOfYear = parseInt(now, OUT_DAY_IN_YEAR_FORMAT);
        String nowWeek = parseString(now, OUT_WEEK_FORMAT);
        int nowHour = parseInt(now, OUT_HOUR_FORMAT);

        if (((lastDayDayOfYear == nowDayOfYear) || (lastDayDayOfYear + 1 == nowDayOfYear))
                || ((nowWeek.equals("Sat") || nowWeek.equals("Sun") || nowWeek.equals("Mon")) && (lastDayWeek.equals("Fri") && lastDayDayOfYear + 3 >= nowDayOfYear))) {
            if (((nowWeek.equals("Sat") || nowWeek.equals("Sun")) || nowHour < 18) || lastDayDayOfYear == nowDayOfYear) {
                isUptodate = true;
            }
        }

        if (isUptodate) {
            return null;
        } else {
            RawData rd = RawDataUtils.getData().get(RawDataUtils.getData().size() - 1);

            String warningMsg = "\n###### Data might not be uptodate: ";
            warningMsg += "\n###### Last: " + rd.getDate();

            String today = parseString(now, OUT_DATE_FORMAT);
            warningMsg += "\n###### Now:  " + today;
            return warningMsg;
        }
    }

    public static Date retrieveLastDate() {
        RawData rd = RawDataUtils.getData().get(RawDataUtils.getData().size() - 1);

        Date lastDay = null;
        try {
            lastDay = IN_SIMPLE_DATE_FORMAT.parse(rd.getDate());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return lastDay;
    }

    public static String retrieveWeekString() {
        Date lastDay = retrieveLastDate();
        Date now = Calendar.getInstance().getTime();

        String lastDayWeek = parseString(lastDay, OUT_WEEK_FORMAT);
        String nowWeek = parseString(now, OUT_WEEK_FORMAT);

        return "data: " + lastDayWeek + "   now: " + nowWeek;
    }

    public static int parseInt(Date date, String format) {
        int intDate = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            String strDate = sdf.format(date);

            intDate = Integer.parseInt(strDate);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return intDate;
    }

    public static String parseString(Date date, String format) {
        String week = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            week = sdf.format(date);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return week;
    }

    public void printTradingSummary() {
        RawData rd1 = RawDataUtils.getData().get(0);
        RawData rd2 = RawDataUtils.getData().get(RawDataUtils.getData().size() - 1);

        int growth = (int) ((rd2.getClose() / rd1.getClose() - 1) * 100);

        RawData lastRawData = RawDataUtils.getData().get(RawDataUtils.getData().size() - 1);
        closePosition(lastRawData, Reason.LIQUIDATE);

        String out = symbol + Utility.padSpace(5 - symbol.length());

        out += " #Trds: " + Utility.int2str(transactionCtr);
        out += " Ern:" + Utility.rightAlign(Utility.float2str(earning, 2), 8);

        if (capital != 0) {
            int ret = (int) (earning / capital * 100);
            out += " r: " + Utility.int2str(ret) + "%";
            out += " vs " + Utility.int2str(growth) + "%";
            out += " Cap:" + Utility.rightAlign(Utility.float2str(capital, 2), 8);
            out += " " + startDate;
        }

        System.out.println(out);

        // RawData.listMessage();

        String dateInfo = checkDataUptodate();
        if (dateInfo != null) {
            System.out.println(dateInfo);
        }
    }

    public void printQuote(String action, RawData rd) {
        String strClose = Utility.float2str(rd.getClose(), 2);

        if (isVerbose) {
            System.out.print(action + delimit + rd.getDate() + delimit + strClose + delimit);
        }
    }

    public boolean shouldCutLoss(RawData rd) {
        if ((longing && (rd.getLow() < (1.0 - CUT_LOSS_REQUIREMENT) * buyPrice)) || (shorting && (rd.getHigh() * (1.0 - CUT_LOSS_REQUIREMENT)) > sellPrice)) {
            return true;
        } else {
            return false;
        }
    }

    public void cutLoss(RawData rd) {
        if (longing) {
            sellToCloseLong(rd, Reason.CUT_LOSS);
        } else if (shorting) {
            buyToCloseShort(rd, Reason.CUT_LOSS);
        }
    }

    public void applyCutLossRule(RawData rd) {
        if (shouldCutLoss(rd)) {
            cutLoss(rd);
            shorting = false;
            longing = false;
        }
    }

    public void buyToCloseShort(RawData rd, Reason reason) {
        buy(rd);
        shorting = false;
        longing = false;
        float gain = sellPrice - rd.getClose();
        String strGain = Utility.float2str(gain, 2);

        strGain = Utility.rightAlign(strGain, 7);

        if (isVerbose) {
            System.out.println(strGain + delimit + reason);
        }
    }

    public void buyToEnterLong(RawData rd) {
        buy(rd);
        longing = true;
    }

    public void sellToCloseLong(RawData rd, Reason reason) {
        sell(rd);
        longing = false;
        shorting = false;
        float gain = rd.getClose() - buyPrice;
        String strGain = Utility.float2str(gain, 2);

        strGain = Utility.rightAlign(strGain, 7);

        if (isVerbose) {
            System.out.println(strGain + delimit + reason);
        }
    }

    public void sellToEnterShort(RawData rd) {
        sell(rd);
        shorting = true;
    }

    private void buy(RawData rd) {
        buyPrice = rd.getClose();
        earning -= buyPrice;

        if (capital == 0) {
            capital = buyPrice;
            startDate = rd.getDate();
        }

        transactionCtr++;

        if (isVerbose) {
            printQuote("Bought", rd);
        }
    }

    private void sell(RawData rd) {
        sellPrice = rd.getClose();
        earning += sellPrice;

        if (capital == 0) {
            capital = sellPrice;
            startDate = rd.getDate();
        }

        transactionCtr++;

        if (isVerbose) {
            printQuote("  Sold", rd);
        }
    }

    public void closePosition(RawData rd, Reason reason) {
        if (isLonging()) {
            sellToCloseLong(rd, reason);
        } else if (isShorting()) {
            buyToCloseShort(rd, reason);
        }
    }

    public static void retrieveTodayOHLLast() {
        TodayOHLLast.retrieveData(getSymbol());
        Date now = Calendar.getInstance().getTime();
        String today = parseString(now, IN_DATE_FORMAT);
        TodayOHLLast.setDate(today);
    }

    public static boolean isPredictingTomorrow() {
        return isPredictingTomorrow;
    }

    public static boolean isOilData() {
        return isOilData;
    }
}
