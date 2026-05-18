package com.learn.b.swing.all.dmi;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.common.Setting;
import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class DMIDataUtils {
    private static final List<DMIData> DATA = new ArrayList<>();

    private static void init() {
        DMIData yesterdayDMIData = null;
        RawData yesterdayData = null;

        for (int i = 0; i < RawDataUtils.getData().size(); i++) {
            RawData todayData = RawDataUtils.getData().get(i);

            DMIData dmiData = new DMIData();
            DATA.add(dmiData);

            if (yesterdayDMIData != null) {
                digestYesterdayData(dmiData, todayData, yesterdayDMIData, yesterdayData);
            }

            yesterdayDMIData = dmiData;
            yesterdayData = todayData;
        }
    }

    private static void digestYesterdayData(DMIData dmiData, RawData todayData, DMIData yesterdayDMIData, RawData yesterdayData) {
        // tr1
        float fTR1 = Math.abs(todayData.getHigh() - todayData.getLow());
        float fTR2 = Math.abs(todayData.getHigh() - yesterdayData.getClose());
        float fTR3 = Math.abs(todayData.getLow() - yesterdayData.getClose());
        float tmp = Math.max(fTR1, fTR2);
        dmiData.setTr1(Math.max(tmp, fTR3));

        // plusDM1 minusDM1
        float fPlusDM1 = Math.max(0, todayData.getHigh() - yesterdayData.getHigh());
        float fMinusDM1 = Math.max(0, yesterdayData.getLow() - todayData.getLow());

        if (fPlusDM1 > fMinusDM1) {
            dmiData.setPlusDM1(fPlusDM1);
            dmiData.setMinusDM1(0);
        } else if (fPlusDM1 == fMinusDM1) {
            dmiData.setPlusDM1(0);
            dmiData.setMinusDM1(0);
        } else {
            dmiData.setPlusDM1(0);
            dmiData.setMinusDM1(fMinusDM1);
        }

        // 15th day
        float f13_14 = (float) (Setting.HALF_CYCLE - 1) / Setting.HALF_CYCLE;

        if (DATA.size() == Setting.HALF_CYCLE + 1) {
            float sumTR1 = 0;
            float sumPlusDM1 = 0;
            float sumMinusDM1 = 0;

            for (int i = 1; i < Setting.HALF_CYCLE; i++) {
                DMIData tmpDMIData = DATA.get(i);

                sumTR1 += tmpDMIData.getTr1();
                sumPlusDM1 += tmpDMIData.getPlusDM1();
                sumMinusDM1 += tmpDMIData.getMinusDM1();
            }

            dmiData.setTr14(f13_14 * sumTR1 + dmiData.getTr1());
            dmiData.setPlusDM14(f13_14 * sumPlusDM1 + dmiData.getPlusDM1());
            dmiData.setMinusDM14(f13_14 * sumMinusDM1 + dmiData.getMinusDM1());
        } else if (DATA.size() > Setting.HALF_CYCLE + 1) {
            dmiData.setTr14(f13_14 * yesterdayDMIData.getTr14() + dmiData.getTr1());
            dmiData.setPlusDM14(f13_14 * yesterdayDMIData.getPlusDM14() + dmiData.getPlusDM1());
            dmiData.setMinusDM14(f13_14 * yesterdayDMIData.getMinusDM14() + dmiData.getMinusDM1());
        }

        if (DATA.size() > Setting.HALF_CYCLE) {
            dmiData.setPlusDI14((float) 100 * dmiData.getPlusDM14() / dmiData.getTr14());
            dmiData.setMinusDI14((float) 100 * dmiData.getMinusDM14() / dmiData.getTr14());
            dmiData.setDiDiff(Math.abs(dmiData.getPlusDI14() - dmiData.getMinusDI14()));
            dmiData.setDiSum(dmiData.getPlusDI14() + dmiData.getMinusDI14());
            dmiData.setDx(100 * dmiData.getDiDiff() / dmiData.getDiSum());
        }

        if (DATA.size() == 2 * Setting.HALF_CYCLE) {
            float sumDX = 0;
            for (int i = Setting.HALF_CYCLE; i < 2 * Setting.HALF_CYCLE; i++) {
                sumDX += DATA.get(i).getDx();
            }
            dmiData.setAdx(sumDX / Setting.HALF_CYCLE);
        } else if (DATA.size() > 2 * Setting.HALF_CYCLE) {
            dmiData.setAdx((yesterdayDMIData.getAdx() * (Setting.HALF_CYCLE - 1) + dmiData.getDx()) / Setting.HALF_CYCLE);
        }

        if (DATA.size() == 3 * Setting.HALF_CYCLE - 1) {
            float sumADX = 0;
            for (int i = 2 * Setting.HALF_CYCLE - 1; i < DATA.size(); i++) {
                sumADX += DATA.get(i).getAdx();
            }
            dmiData.setAdxr(sumADX / Setting.HALF_CYCLE);
        } else if (DATA.size() > 3 * Setting.HALF_CYCLE - 1) {
            DMIData dmiData1 = DATA.get(DATA.size() - Setting.HALF_CYCLE - 1);
            DMIData dmiData2 = DATA.get(DATA.size() - 1);

            dmiData.setAdxr((dmiData1.getAdx() + dmiData2.getAdx()) / 2);
        }

        if (DATA.size() >= 3 * Setting.HALF_CYCLE - 1) {
            dmiData.setAtr14(dmiData.getTr14() / Setting.HALF_CYCLE);
        }
    }

    public static List<DMIData> getData() {
        synchronized (DMIDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }
}
