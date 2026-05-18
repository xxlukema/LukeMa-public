package com.learn.b.swing.all.concert;


import java.awt.Color;
import java.awt.Graphics;

import com.learn.b.swing.all.obv.OnBalanceVolumeDataUtils;
import com.learn.b.swing.all.pvt.PriceVolumeTrendDataUtils;
import com.learn.b.swing.all.raw.RawDataUtils;


public class OnBalanceVolumeDataCollection
    extends DataCollectionBase {

    private float[] obv;
    private float[] pvt;

    public OnBalanceVolumeDataCollection(int y_L, int y_H) {
        super(y_L, y_H);
    }

    public void descMe() {
        setDesc("OBV_PVT: OBV-blue PVT-red. OBV and PVT should go same directions as price. If OBV and PVT conflict, use PVT.");
    }

    public boolean isKUint() {
        return true;
    }

    public String getValueString(int index) {
        if (obv != null) {
            return "OBV=" + roundFloat2String(obv[index]) + "K" + "   PVT=" + roundFloat2String(pvt[index]) + "K(Adjusted)";
        } else {
            return null;
        }
    }

    public void addData() {
        int len = RawDataUtils.getCHART_DAYS();

        obv = new float[len];
        pvt = new float[len];

        float obvMin = Integer.MAX_VALUE;
        float obvMax = Integer.MIN_VALUE;
        float pvtMin = Integer.MAX_VALUE;
        float pvtMax = Integer.MIN_VALUE;

        for (int i = 0; i < len; i++) {
            int k = RawDataUtils.getData().size() - len + i;
            if (k < 0) {
                continue;
            }

            obv[i] = OnBalanceVolumeDataUtils.getData().get(k).getObv();
            obvMin = Math.min(obvMin, obv[i]);
            obvMax = Math.max(obvMax, obv[i]);

            pvt[i] = PriceVolumeTrendDataUtils.getData().get(k).getPvt();
            pvtMin = Math.min(pvtMin, pvt[i]);
            pvtMax = Math.max(pvtMax, pvt[i]);
        }

        addY(obv);

        float k = (obvMax - obvMin) / (pvtMax - pvtMin);

        for (int i = 0; i < len; i++) {
            pvt[i] = k * (pvt[i] - pvtMin) + obvMin;
        }

        addY(pvt);
    }

    public void paint(Graphics g) {
        drawGrid(g);
        drawCross(g);
        drawOnBalanceVolume(g);
        drawDesc(g);
    }

    private void drawOnBalanceVolume(Graphics g) {
        int[] obvI = (int[]) getADJUSTED_Y().get(0);
        int[] pvtI = (int[]) getADJUSTED_Y().get(1);

        drawLine(g, obvI, Color.BLUE, 2);
        drawLine(g, pvtI, Color.RED, 2);
    }
}
