package com.learn.b.swing.all.accum_dist_line;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class AccumDistLineDataUtils {
    private static final List<AccumDistLineData> DATA = new ArrayList<>();

    private static void init() {
        float high = 0;
        float low = 0;
        float close = 0;
        long volume = 0;
        int todayAccumDistLine = 0;
        double todayAccumDistLineIncrease = 0;

        for (RawData todayData : RawDataUtils.getData()) {
            high = todayData.getHigh();
            low = todayData.getLow();
            close = todayData.getClose();
            volume = todayData.getVolume();
            todayAccumDistLineIncrease = ((2.0 * close - high - low) / (high - low)) * volume;
            todayAccumDistLine += (int) todayAccumDistLineIncrease;

            AccumDistLineData accumDistLineData = new AccumDistLineData();
            accumDistLineData.setAccumDistLine(todayAccumDistLine);

            DATA.add(accumDistLineData);
        }
    }

    public static List<AccumDistLineData> getData() {
        synchronized (AccumDistLineDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }

}
