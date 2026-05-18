package com.learn.b.swing.all.aroon;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class AroonDataUtils {
    private static final List<AroonData> DATA = new ArrayList<>();

    private final static int DAYS = 5;

    private static void init() {
        for (int i = 0; i < RawDataUtils.getData().size(); i++) {
            AroonData aroonData = new AroonData();
            DATA.add(aroonData);

            if (i < DAYS) {
                continue;
            }

            float highestHigh = Integer.MIN_VALUE;
            float lowestLow = Integer.MAX_VALUE;

            int daysSinceHighestHigh = 0;
            int daysSinceLowestLow = 0;

            for (int k = 0; k <= DAYS; k++) {
                RawData rd = RawDataUtils.getData().get(i - k);
                highestHigh = Math.max(highestHigh, rd.getHigh());
                lowestLow = Math.min(lowestLow, rd.getLow());

                if (highestHigh == rd.getHigh()) {
                    daysSinceHighestHigh = k;
                }

                if (lowestLow == rd.getLow()) {
                    daysSinceLowestLow = k;
                }
            }

            // aroonUpData.aroonUp = (float) ((highestHigh-todayData.getClose())* (-100.0) / (highestHigh - lowestLow));

            // 100 + aroonUp' %R.
            float aroonUp = (float) ((100.0) * ((float) DAYS - (float) daysSinceHighestHigh) / (float) DAYS);
            float aroonDown = (float) ((100.0) * ((float) DAYS - (float) daysSinceLowestLow) / (float) DAYS);

            aroonData.setAroonDown(aroonDown);
            aroonData.setAroonUp(aroonUp);
        }
    }

    public static List<AroonData> getData() {
        synchronized (AroonDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }

}
