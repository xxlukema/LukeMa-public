package com.learn.b.swing.all.concert;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.learn.b.swing.all.raw.RawDataUtils;
import com.learn.b.swing.all.ema.EMAData;
import com.learn.b.swing.all.ema.EMADataUtils;


public class EMADataCollection
    extends DataCollectionBase {

    // @formatter:off
    private static float[] ema_5_10    = null;
    private static float[] inc         = null;
    private static float   maxEMA_5_10 = Integer.MIN_VALUE;
    private static float   minEMA_5_10 = Integer.MAX_VALUE;
    // @formatter:on

    public EMADataCollection(int y_L, int y_H) {
        super(y_L, y_H);
    }

    public void descMe() {
        setDesc("EMA: 5day-10day blue, Reverse red, ZeroLine white");
    }

    private void drawAllGrid(Graphics g) {
        int[] x = getX();

        if (x != null) {
            int yPos = getY_L() + (int) (minEMA_5_10 / (maxEMA_5_10 - minEMA_5_10) * (getY_L() - getY_H()));

            ((Graphics2D) g).setStroke(STROKE4);
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(x[0], yPos, x[x.length - 1], yPos);

            ((Graphics2D) g).setStroke(STROKE1);
            g.setColor(Color.WHITE);
            g.drawLine(x[0], yPos, x[x.length - 1], yPos);

            g.drawLine(x[0], yPos, x[x.length - 1], yPos);
            g.drawString("0", x[x.length - 1] + 5, yPos + 5);
        }
    }

    public String getValueString(int index) {
        if (ema_5_10 != null) {
            return "5day - 10day = " + roundFloat2String(ema_5_10[index]) + " reverse increment = " + roundFloat2String(inc[index]);
        } else {
            return null;
        }
    }

    public void addData() {
        int len = RawDataUtils.getCHART_DAYS();

        ema_5_10 = new float[len];
        inc = new float[len];

        ema_5_10[0] = ((EMAData) EMADataUtils.getData05().get(0)).getEma() - ((EMAData) EMADataUtils.getData10().get(0)).getEma();
        inc[0] = 0;
        ema_5_10[1] = ((EMAData) EMADataUtils.getData05().get(1)).getEma() - ((EMAData) EMADataUtils.getData10().get(1)).getEma();
        inc[1] = ema_5_10[1] - ema_5_10[0];

        float sign = 0;

        for (int i = 2; i < len; i++) {
            int k = RawDataUtils.getData().size() - len + i;
            if (k < 0) {
                continue;
            }

            ema_5_10[i] = ((EMAData) EMADataUtils.getData05().get(k)).getEma() - ((EMAData) EMADataUtils.getData10().get(k)).getEma();

            if (ema_5_10[i] == 0) {
                sign = 0;
            } else {
                sign = (float) (ema_5_10[i] / Math.abs(ema_5_10[i]));
            }

            inc[i] = ema_5_10[i] - ema_5_10[i - 2];

            if ((inc[i] * sign) > 0) {
                inc[i] = 0;
            }
        }

        for (int i = 0; i < ema_5_10.length; i++) {
            maxEMA_5_10 = Math.max(ema_5_10[i], maxEMA_5_10);
            minEMA_5_10 = Math.min(ema_5_10[i], minEMA_5_10);
        }

        addY(ema_5_10);
        addY(inc);
    }

    public void paint(Graphics g) {
        drawGrid(g);
        drawCross(g);
        drawEMA(g);
        drawDesc(g);
    }

    private void drawEMA(Graphics g) {
        int[] ema_5_10 = (int[]) getADJUSTED_Y().get(0);
        int[] inc = (int[]) getADJUSTED_Y().get(1);

        drawAllGrid(g);
        drawLine(g, inc, Color.RED, 2);
        drawLine(g, ema_5_10, Color.BLUE, 2);
    }
}
