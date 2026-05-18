package com.learn.b.swing.all.momentum;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class MomentumDataUtils {
    private static final List<MomentumData> DATA = new ArrayList<>();

    private static void init() {
        RawData rdOneDayBack = RawDataUtils.getData().get(0);
        MomentumData mdOneDayBack = new MomentumData();
        DATA.add(mdOneDayBack);
        RawData rdTwoDaysBack = RawDataUtils.getData().get(1);
        MomentumData mdTwoDaysBack = new MomentumData();
        DATA.add(mdTwoDaysBack);

        for (int i = 2; i < RawDataUtils.getData().size(); i++) {
            RawData rdTodayData = RawDataUtils.getData().get(i);

            MomentumData mdTodayData = new MomentumData();
            DATA.add(mdTodayData);

            mdTodayData.setMf(rdTodayData.getClose() - rdTwoDaysBack.getClose());

            float trA = Math.abs(rdTodayData.getHigh() - rdTodayData.getLow());
            float trB = Math.abs(rdTodayData.getHigh() - rdOneDayBack.getClose());
            float trC = Math.abs(rdTodayData.getLow() - rdOneDayBack.getClose());
            mdTodayData.setTr(Math.max(trA, Math.max(trB, trC)));

            mdTodayData.setAverageX((rdTodayData.getHigh() + rdTodayData.getLow() + rdTodayData.getClose()) / 3);

            mdTodayData.setHighTBP(rdTwoDaysBack.getClose() + Math.max(mdOneDayBack.getMf(), mdTwoDaysBack.getMf()));
            mdTodayData.setLowTBP(rdTwoDaysBack.getClose() + Math.min(mdOneDayBack.getMf(), mdTwoDaysBack.getMf()));

            mdTodayData.setHighStop(mdTodayData.getAverageX() + mdTodayData.getTr());
            mdTodayData.setLowStop(mdTodayData.getAverageX() - mdTodayData.getTr());

            mdTodayData.setHighTarget(mdTodayData.getAverageX() * 2 - rdTodayData.getLow());
            mdTodayData.setLowTarget(mdTodayData.getAverageX() * 2 - rdTodayData.getHigh());

            rdTwoDaysBack = rdOneDayBack;
            rdOneDayBack = rdTodayData;
            mdTwoDaysBack = mdOneDayBack;
            mdOneDayBack = mdTodayData;
        }
    }

    public static List<MomentumData> getData() {
        synchronized (MomentumDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }

}
