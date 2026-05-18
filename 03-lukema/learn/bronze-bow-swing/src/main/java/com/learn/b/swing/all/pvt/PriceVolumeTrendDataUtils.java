package com.learn.b.swing.all.pvt;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class PriceVolumeTrendDataUtils {
    private static final List<PriceVolumeTrendData> DATA = new ArrayList<>();

    private static void init() {
        float lastClose = 0;
        float todayClose = 0;
        float lastPVT = 0;
        float todayPVT = 0;

        for (int i = 0; i < RawDataUtils.getData().size(); i++) {
            RawData rd = RawDataUtils.getData().get(i);
            todayClose = rd.getClose();

            PriceVolumeTrendData pvtData = new PriceVolumeTrendData();
            DATA.add(pvtData);

            if (i == 0) {
                lastClose = todayClose;
                continue;
            }

            todayPVT = lastPVT + (todayClose / lastClose - 1) * rd.getVolume() / 1000;

            pvtData.setPvt(todayPVT);
            lastPVT = todayPVT;
            lastClose = todayClose;
        }
    }

    public static List<PriceVolumeTrendData> getData() {
        synchronized (PriceVolumeTrendDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }

}
