package com.learn.b.swing.all.bollinger;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class BollingerDataUtils {
    private static final List<BollingerData> DATA = new ArrayList<>();

    public static final int halfCycle = 20;

    public static final int D = 2;

    private static void init() {
        // Middle band
        for (int i = 0; i < RawDataUtils.getData().size(); i++) {
            BollingerData bollingerData = new BollingerData();
            DATA.add(bollingerData);

            if (i < halfCycle) {
                continue;
            }

            float sumClose = 0;
            for (int k = i - halfCycle; k < i; k++) {
                RawData oldRawData = RawDataUtils.getData().get(k);
                sumClose += oldRawData.getClose();
            }

            bollingerData.setMiddleBand(sumClose / halfCycle);

            double sumSquare = 0;
            for (int k = i - halfCycle; k < i; k++) {
                RawData oldRawData = RawDataUtils.getData().get(k);
                sumSquare += Math.pow((oldRawData.getClose() - bollingerData.getMiddleBand()), 2);
            }

            float deviation = (float) Math.pow((sumSquare / halfCycle), 0.5);

            bollingerData.setUpperBand(bollingerData.getMiddleBand() + D * deviation);
            bollingerData.setLowerBand(bollingerData.getMiddleBand() - D * deviation);
        }
    }

    public static List<BollingerData> getData() {
        synchronized (BollingerDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }
}
