package com.learn.b.swing.all.ema;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class EMADataUtils {
    private static final List<EMAData> DATA05 = new ArrayList<>();

    private static final List<EMAData> DATA10 = new ArrayList<>();

    private static final List<EMAData> DATA15 = new ArrayList<>();

    private static final List<EMAData> DATA25 = new ArrayList<>();

    private static final List<EMAData> DATA50 = new ArrayList<>();

    private static void init() {
        init(DATA05, 5);
        init(DATA10, 10);
        init(DATA15, 15);
        init(DATA25, 25);
        init(DATA50, 50);
    }

    private static void init(List<EMAData> data, int days) {
        float factor = (float) 2.0 / (days + 1);

        float lastEMA = 0;
        float todayEMA = 0;

        for (int i = 0; i < RawDataUtils.getData().size(); i++) {
            RawData rd = RawDataUtils.getData().get(i);

            EMAData emaData = new EMAData();
            data.add(emaData);

            if (i < 1) {
                emaData.setEma(rd.getClose());
                lastEMA = emaData.getEma();

                continue;
            }

            todayEMA = (float) (factor * rd.getClose() + (1.0 - factor) * lastEMA);

            emaData.setEma(todayEMA);
            lastEMA = todayEMA;
        }
    }

    public static List<EMAData> getData05() {
        synchronized (EMADataUtils.class) {
            if (DATA05.size() == 0) {
                init();
            }
        }

        return DATA05;
    }

    public static List<EMAData> getData10() {
        synchronized (EMADataUtils.class) {
            if (DATA10.size() == 0) {
                init();
            }
        }

        return DATA10;
    }

    public static List<EMAData> getData15() {
        synchronized (EMADataUtils.class) {
            if (DATA15.size() == 0) {
                init();
            }
        }

        return DATA15;
    }

    public static List<EMAData> getData25() {
        synchronized (EMADataUtils.class) {
            if (DATA25.size() == 0) {
                init();
            }
        }

        return DATA25;
    }

    public static List<EMAData> getData50() {
        synchronized (EMADataUtils.class) {
            if (DATA50.size() == 0) {
                init();
            }
        }

        return DATA50;
    }

}
