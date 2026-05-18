package com.learn.b.swing.all.chaikin;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.accum_dist_line.AccumDistLineData;
import com.learn.b.swing.all.accum_dist_line.AccumDistLineDataUtils;
import com.learn.b.swing.all.raw.RawDataUtils;


public class ChaikinOscillatorDataUtils {
    private static final List<ChaikinOscillatorData> DATA = new ArrayList<>();

    private static void init() {
        float expPerct5 = (float) (2.0 / (5 + 1));
        float expPerct10 = (float) (2.0 / (10 + 1));

        float lastEMA5 = 0;
        float lastEMA10 = 0;

        for (int i = 0; i < RawDataUtils.getData().size(); i++) {
            AccumDistLineData adlData = AccumDistLineDataUtils.getData().get(i);

            ChaikinOscillatorData chaikinData = new ChaikinOscillatorData();
            DATA.add(chaikinData);

            float ema5 = (float) (lastEMA5 * (1.0 - expPerct5) + adlData.getAccumDistLine() * expPerct5);
            float ema10 = (float) (lastEMA10 * (1.0 - expPerct10) + adlData.getAccumDistLine() * expPerct10);
            chaikinData.setEma5(ema5);
            chaikinData.setEma10(ema10);

            chaikinData.setEma5_ema10(ema5 - ema10);

            float max = Integer.MIN_VALUE;
            for (int k = 0; k < 5; k++) {
                int m = Math.max(0, i - k);

                ChaikinOscillatorData oldData = getData().get(m);
                max = Math.max(max, Math.abs(oldData.getEma5_ema10()));
            }

            chaikinData.setDimensionless(chaikinData.getEma5_ema10() / max);

            lastEMA5 = ema5;
            lastEMA10 = ema10;
        }
    }

    public static List<ChaikinOscillatorData> getData() {
        synchronized (ChaikinOscillatorDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }
}
