package com.learn.b.swing.all.std;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class StandardDeviationDataUtils {
    private static final List<StandardDeviationData> DATA = new ArrayList<>();

    private static final int DAYS = 5;

    private static void init() {
        for (int i = 0; i < RawDataUtils.getData().size(); i++) {
            StandardDeviationData stdData = new StandardDeviationData();
            DATA.add(stdData);

            if (i < DAYS) {
                continue;
            }

            float sumClose = 0;
            for (int k = i - DAYS + 1; k <= i; k++) {
                RawData oldRawData = RawDataUtils.getData().get(k);
                sumClose += oldRawData.getClose();
            }

            stdData.setCloseSMA(sumClose / DAYS);

            double sumSquare = 0;
            for (int k = i - DAYS + 1; k <= i; k++) {
                RawData oldRawData = RawDataUtils.getData().get(k);
                sumSquare += Math.pow((oldRawData.getClose() - stdData.getCloseSMA()), 2);
            }

            float deviation = (float) Math.pow((sumSquare / DAYS), 0.5);

            stdData.setStd(deviation);
        }

        expend10DayEMAofSTD();
    }

    private static void expend10DayEMAofSTD() {
        int days = DAYS * 2;

        float sm = (float) 2.0 / (days + 1);

        float lastEMA = 0;
        float todayEMA = 0;

        for (int i = 1; i < DATA.size(); i++) {
            StandardDeviationData stdData = DATA.get(i);

            todayEMA = (float) (sm * stdData.getStd() + (1.0 - sm) * lastEMA);

            stdData.setStdEMA(todayEMA);
            lastEMA = todayEMA;

            float vi = stdData.getStd() / stdData.getEMAofSTD();

            int timePeriod = (int) (14 / vi);
            stdData.setTimePeriod(timePeriod);

        }
    }

    public static List<StandardDeviationData> getData() {
        synchronized (StandardDeviationDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }
}
