package com.learn.b.swing.all.cmo;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class CMODataUtils {
    private static final List<CMOData> DATA05 = new ArrayList<>();

    private static final List<CMOData> DATA10 = new ArrayList<>();

    private static void init() {
        init(DATA05, 5);
        init(DATA10, 10);
    }

    private static void init(List<CMOData> data, int days) {
        for (int i = 0; i < RawDataUtils.getData().size(); i++) {
            CMOData cmoData = new CMOData();
            data.add(cmoData);

            if (i < days) {
                continue;
            }

            float su = 0;
            float sd = 0;
            float tmp = 0;
            RawData newClose = RawDataUtils.getData().get(i);
            RawData oldClose = null;

            for (int k = 1; k <= days; k++) {
                oldClose = RawDataUtils.getData().get(i - k);
                tmp = newClose.getClose() - oldClose.getClose();
                newClose = oldClose;

                if (tmp > 0) {
                    su += tmp;
                } else {
                    sd -= tmp;
                }
            }

            if (su == 0 && sd == 0) {
                cmoData.setCmo(0.0f);
            } else {
                cmoData.setCmo((float) (100.0f) * ((su - sd) / (su + sd)));
            }
        }
    }

    public static List<CMOData> getData05() {
        synchronized (CMODataUtils.class) {
            if (DATA05.size() == 0) {
                init();
            }
        }

        return DATA05;
    }

    public static List<CMOData> getData10() {
        synchronized (CMODataUtils.class) {
            if (DATA10.size() == 0) {
                init();
            }
        }

        return DATA10;
    }

}
