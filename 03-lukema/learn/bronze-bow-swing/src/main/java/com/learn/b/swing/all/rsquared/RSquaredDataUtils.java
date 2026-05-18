package com.learn.b.swing.all.rsquared;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.raw.RawDataUtils;


public class RSquaredDataUtils {
    private static final List<RSquaredData> DATA = new ArrayList<>();

    private static final int DAYS = 5;

    private static void init() {
        int sumX = 0;
        int sumX2 = 0;

        for (int x = 0; x < DAYS; x++) {
            sumX += x;
            sumX2 += x * x;
        }

        for (int i = 0; i < RawDataUtils.getData().size(); i++) {
            RSquaredData rsquaredData = new RSquaredData();
            DATA.add(rsquaredData);

            if (i < DAYS) {
                continue;
            }

            float sumY = 0;
            float sumXY = 0;
            double sumY2 = 0;

            for (int x = 0; x < DAYS; x++) {
                float y = RawDataUtils.getData().get(i - DAYS + x + 1).getClose();

                sumY += y;
                sumXY += x * y;
                sumY2 += y * y;
            }

            rsquaredData.setSlope((float) ((DAYS * sumXY - sumX * sumY) / (DAYS * sumX2 - sumX * sumX)));
            float fenZi = DAYS * sumXY - sumX * sumY;
            rsquaredData.setRsquared((float) (fenZi * fenZi / ((DAYS * sumX2 - sumX * sumX) * (DAYS * sumY2 - sumY * sumY))));
        }
    }

    public static List<RSquaredData> getData() {
        synchronized (RSquaredDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }
}
