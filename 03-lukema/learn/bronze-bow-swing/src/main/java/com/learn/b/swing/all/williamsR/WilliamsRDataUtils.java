package com.learn.b.swing.all.williamsR;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.common.Setting;
import com.learn.b.swing.all.common.Utility;
import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class WilliamsRDataUtils {
    private static final List<WilliamsRData> DATA = new ArrayList<>();

    private static final int DAYS = Setting.HALF_CYCLE;

    private static void init() {
        for (int i = 0; i < RawDataUtils.getData().size(); i++) {
            RawData todayData = RawDataUtils.getData().get(i);

            WilliamsRData williamsData = new WilliamsRData();
            DATA.add(williamsData);

            if (i < DAYS - 1) {
                continue;
            }

            float highestHigh = Integer.MIN_VALUE;
            float lowestLow = Integer.MAX_VALUE;

            for (int k = 0; k < DAYS; k++) {
                RawData rd = RawDataUtils.getData().get(i - k);
                highestHigh = Math.max(highestHigh, rd.getHigh());
                lowestLow = Math.min(lowestLow, rd.getLow());
            }

            // williamsData.williams = (float) ((highestHigh-todayData.getClose())* (-100.0) / (highestHigh - lowestLow));

            // 100 + williams' %R.
            float tmpWilliams = (float) ((todayData.getClose() - lowestLow) * (100.0) / (highestHigh - lowestLow));
            williamsData.setWilliams(Utility.limitValueToMinMax(tmpWilliams, 0, 100));
        }
    }

    public static List<WilliamsRData> getData() {
        synchronized (WilliamsRDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }
}
