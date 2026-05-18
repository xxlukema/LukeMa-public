package com.learn.b.swing.all.concert;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;
import com.learn.b.swing.all.volume.VolumeDataUtils;


public class VolumeDataCollection
    extends DataCollectionBase {
    private static float[] volume = null;

    public VolumeDataCollection(int y_L, int y_H) {
        super(y_L, y_H);
    }

    public void descMe() {
        setDesc("Volume");
    }

    public boolean isKUint() {
        return true;
    }

    public String getValueString(int index) {
        if (volume != null) {
            return "Volume=" + roundFloat2String(volume[index]) + "K";
        } else {
            return null;
        }
    }

    public void addData() {
        int len = RawDataUtils.getCHART_DAYS();

        volume = new float[len];

        for (int i = 0; i < len; i++) {
            int k = RawDataUtils.getData().size() - len + i;
            if (k < 0) {
                continue;
            }

            volume[i] = VolumeDataUtils.getData().get(k).getVolume();
        }

        addY(volume);
    }

    public void paint(Graphics g) {
        drawGrid(g);
        drawCross(g);
        drawVolume(g);
        drawDesc(g);
    }

    private void drawVolume(Graphics g) {
        int[] volumeI = (int[]) getADJUSTED_Y().get(0);

        int x[] = getX();

        Stroke s = new BasicStroke(2);
        ((Graphics2D) g).setStroke(s);

        Color c = Color.BLUE;
        g.setColor(c);

        RawData rd = null;
        float lastClose = 0;
        float todayClose = 0;
        for (int i = 0; i < volumeI.length; i++) {
            rd = RawDataUtils.getData().get(i);
            todayClose = rd.getClose();

            if (i == 0) {
                lastClose = rd.getOpen();
            }

            if (lastClose < todayClose) {
                c = Color.BLUE;
            } else {
                c = Color.RED;
            }

            g.setColor(c);
            g.drawLine(x[i], getY_L(), x[i], volumeI[i]);
            lastClose = todayClose;
        }
    }
}
