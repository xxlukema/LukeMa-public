package com.learn.b.swing.all.concert;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.learn.b.swing.all.macd.MACDDataUtils;
import com.learn.b.swing.all.raw.RawDataUtils;


public class MACDDataCollection
    extends DataCollectionBase {

    private float[] macd_12_26 = null;
    private float[] inc = null;
    private float maxMACD_12_26 = Integer.MIN_VALUE;
    private float minMACD_12_26 = Integer.MAX_VALUE;

    public MACDDataCollection(int y_L, int y_H) {
        super(y_L, y_H);
    }

    public void descMe() {
        setDesc("MACD: FastEMA-SlowEMA blue, Reverse red, ZeroLine white");
    }

    private void drawAllGrid(Graphics g) {
        int[] x = getX();

        if (x != null) {
            int yPos = getY_L() + (int) (minMACD_12_26 / (maxMACD_12_26 - minMACD_12_26) * (getY_L() - getY_H()));

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
        if (macd_12_26 != null) {
            return "Divergence = " + roundFloat2String(macd_12_26[index]);
        } else {
            return null;
        }
    }

    public void addData() {
        int len = RawDataUtils.getCHART_DAYS();

        macd_12_26 = new float[len];
        inc = new float[len];

        macd_12_26[0] = MACDDataUtils.getData().get(0).getDivergence();
        inc[0] = 0;
        macd_12_26[1] = MACDDataUtils.getData().get(1).getDivergence();
        inc[1] = macd_12_26[1] - macd_12_26[0];

        float sign = 0;

        for (int i = 2; i < len; i++) {
            int k = RawDataUtils.getData().size() - len + i;
            if (k < 0) {
                continue;
            }

            macd_12_26[i] = MACDDataUtils.getData().get(k).getDivergence();

            if (macd_12_26[i] == 0) {
                sign = 0;
            } else {
                sign = (float) (macd_12_26[i] / Math.abs(macd_12_26[i]));
            }

            inc[i] = macd_12_26[i] - macd_12_26[i - 2];

            if ((inc[i] * sign) > 0) {
                inc[i] = 0;
            }
        }

        for (int i = 0; i < macd_12_26.length; i++) {
            maxMACD_12_26 = Math.max(macd_12_26[i], maxMACD_12_26);
            minMACD_12_26 = Math.min(macd_12_26[i], minMACD_12_26);
        }

        addY(macd_12_26);
        addY(inc);
    }

    public void paint(Graphics g) {
        drawGrid(g);
        drawCross(g);
        drawMACD(g);
        drawDesc(g);
    }

    private void drawMACD(Graphics g) {
        int[] macd_12_26 = (int[]) getADJUSTED_Y().get(0);
        int[] inc = (int[]) getADJUSTED_Y().get(1);

        drawAllGrid(g);
        drawLine(g, inc, Color.RED, 2);
        drawLine(g, macd_12_26, Color.BLUE, 2);
    }

    public void adjustY() {
        adjustY(minMACD_12_26, maxMACD_12_26);
    }
}
