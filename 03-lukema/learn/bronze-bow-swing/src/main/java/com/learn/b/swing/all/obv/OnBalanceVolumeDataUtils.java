package com.learn.b.swing.all.obv;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class OnBalanceVolumeDataUtils {
    private static final List<OnBalanceVolumeData> DATA = new ArrayList<>();

    private static void init() {
        float lastClose = 0;
        float todayClose = 0;
        float lastOBV = 0;
        float todayOBV = 0;

        float divider = 1;

        long averageValue = (long) RawDataUtils.getData().stream().mapToLong((item) -> item.getVolume()).average().getAsDouble();
        if (averageValue > 100_000_000) {
            divider = 1_000_000;
        } else if (averageValue > 100_000) {
            divider = 1_000;
        } else {
            divider = 1;
        }

        for (RawData rd : RawDataUtils.getData()) {
            todayClose = rd.getClose();

            OnBalanceVolumeData obvData = new OnBalanceVolumeData();
            DATA.add(obvData);

            if (DATA.size() == 1) {
                lastClose = todayClose;
                continue;
            }

            if (todayClose > lastClose) {
                todayOBV = lastOBV + rd.getVolume() / divider;
            } else if (todayClose < lastClose) {
                todayOBV = lastOBV - rd.getVolume() / divider;
            } else {
                todayOBV = lastOBV;
            }

            obvData.setObv(todayOBV);
            lastOBV = todayOBV;
            lastClose = todayClose;
        }
    }

    public static List<OnBalanceVolumeData> getData() {
        synchronized (OnBalanceVolumeDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }
}
