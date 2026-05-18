package com.learn.b.swing.all.macd;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class MACDDataUtils {
    private static final List<MACDData> DATA = new ArrayList<>();

    /*
    private static final float EMA12_PCT      = (float)0.15;    // 2 / (# periods + 1)
    private static final float EMA26_PCT      = (float)0.075;    // 2 / (26 + 1)
    private static final float EMA9MACD_PCT   = (float)0.20;    // 2 / (# periods + 1)
    */

    // Make it 5X10 days.
    private static final float EMA12_PCT = (float) 0.3333; // 2 / (# periods + 1)

    private static final float EMA26_PCT = (float) 0.1818; // 2 / (26 + 1)

    private static final float EMA9MACD_PCT = (float) 0.4210; // 2 / (# periods + 1)

    private static void init() {
        RawData seedRawData = RawDataUtils.getData().get(0);
        MACDData seedMACDData = new MACDData();
        DATA.add(seedMACDData);
        seedMACDData.setEma12(seedRawData.getClose());
        seedMACDData.setEma26(seedRawData.getClose());
        seedMACDData.setMacd_12_26(seedMACDData.getEma12() - seedMACDData.getEma26());
        seedMACDData.setEma9MACD(seedMACDData.getMacd_12_26());

        MACDData yesterdayMACDData = seedMACDData;

        for (int i = 1; i < RawDataUtils.getData().size(); i++) {
            RawData todayData = RawDataUtils.getData().get(i);

            MACDData macdData = new MACDData();
            DATA.add(macdData);

            macdData.setEma12(EMA12_PCT * todayData.getClose() + (1 - EMA12_PCT) * yesterdayMACDData.getEma12());
            macdData.setEma26(EMA26_PCT * todayData.getClose() + (1 - EMA26_PCT) * yesterdayMACDData.getEma26());
            macdData.setMacd_12_26(macdData.getEma12() - macdData.getEma26());
            macdData.setEma9MACD(EMA9MACD_PCT * macdData.getMacd_12_26() + (1 - EMA9MACD_PCT) * yesterdayMACDData.getMacd_12_26());
            macdData.setDivergence(macdData.getMacd_12_26() - macdData.getEma9MACD());

            yesterdayMACDData = macdData;
        }
    }

    public static List<MACDData> getData() {
        synchronized (MACDDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }
}
