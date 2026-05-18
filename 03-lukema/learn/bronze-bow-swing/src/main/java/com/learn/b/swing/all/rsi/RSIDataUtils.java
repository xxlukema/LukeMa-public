package com.learn.b.swing.all.rsi;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.common.Setting;
import com.learn.b.swing.all.raw.RawDataUtils;


public class RSIDataUtils {
    private static final List<RSIData> DATA = new ArrayList<>();

    private static final int DAYS = Setting.HALF_CYCLE;

    private static void init() {
        float up = 0;
        float down = 0;
        float todayRSI = 0;
        float lastClose = RawDataUtils.getData().get(0).getClose();

        for (int i = 0; i < RawDataUtils.getData().size(); i++) {
            RSIData rsiData = new RSIData();
            DATA.add(rsiData);

            if (i < DAYS) {
                continue;
            }

            if (i == DAYS) {
                for (int m = 1; m < DAYS + 1; m++) {
                    float todayClose = RawDataUtils.getData().get(m).getClose();
                    up += Math.max(0, (todayClose - lastClose));
                    down += Math.max(0, (lastClose - todayClose));
                    lastClose = todayClose;
                }

                up /= DAYS;
                down /= DAYS;
            } else {
                float todayClose = RawDataUtils.getData().get(i).getClose();
                up = (up * (DAYS - 1) + Math.max(0, (todayClose - lastClose))) / DAYS;
                down = (down * (DAYS - 1) + Math.max(0, (lastClose - todayClose))) / DAYS;
                lastClose = todayClose;
            }

            todayRSI = (float) (up * 100 / (up + down));
            rsiData.setRsi(todayRSI);
        }
    }

    public static List<RSIData> getData() {
        synchronized (RSIDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }

}
