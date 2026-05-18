package com.learn.b.swing.all.concert;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.learn.b.swing.all.raw.RawDataUtils;
import com.learn.b.swing.all.rsi.RSIDataUtils;
import com.learn.b.swing.all.williamsR.WilliamsRDataUtils;
import com.learn.b.swing.all.common.Utility;


public class RSIDataCollection
    extends DataCollectionBase {

    private static float[] rsi = null;
    private static float[] williams = null;

    public RSIDataCollection(int y_L, int y_H) {
        super(y_L, y_H);
    }

    public void descMe() {
        // setDesc("RSI_Williams: RSI-red Williams%R-blue");

        setDesc("RSI: Slow-red Fast-blue");
    }

    public void addData() {
        int len = RawDataUtils.getCHART_DAYS();

        rsi = new float[len];
        williams = new float[len];

        for (int i = 0; i < len; i++) {
            int k = RawDataUtils.getData().size() - len + i;
            if (k < 0) {
                continue;
            }
            rsi[i] = RSIDataUtils.getData().get(k).getRsi();
            williams[i] = WilliamsRDataUtils.getData().get(k).getWilliams();
        }

        addY(rsi);
        addY(williams);
    }

    public String getValueString(int index) {
        if (rsi != null) {
            // return "RSI="+Utility.float2str(rsi[index], 0)+"   100+Williams%R="+Utility.float2str(williams[index], 0);

            return "Fast=" + Utility.float2str(williams[index], 0) + "   Slow=" + Utility.float2str(rsi[index], 0);
        } else {
            return null;
        }
    }

    public void paint(Graphics g) {
        drawGrid(g);
        drawAllGrid(g);
        drawCross(g);
        drawRSI(g);
        drawWilliamsR(g);
        drawDesc(g);
    }

    private void drawAllGrid(Graphics g) {
        int[] x = getX();

        if (x != null) {
            int dy = (getY_H() - getY_L()) / 5;

            ((Graphics2D) g).setStroke(STROKE1);

            for (int i = 1; i < 5; i++) {
                int yPos = getY_L() + i * dy;
                if (i == 1 || i == 4) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.CYAN);
                }
                g.drawLine(x[0], yPos, x[x.length - 1], yPos);
                if (i == 1 || i == 4) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.CYAN);
                }
                g.drawString("" + 20 * i, x[x.length - 1] + 5, yPos + 5);
            }
        }
    }

    private void drawRSI(Graphics g) {
        int[] rsi = (int[]) getADJUSTED_Y().get(0);

        drawLine(g, rsi, Color.RED, 2);
    }

    private void drawWilliamsR(Graphics g) {
        int[] williams = (int[]) getADJUSTED_Y().get(1);

        drawLine(g, williams, Color.BLUE, 2);
    }

    public void adjustY() {
        adjustY(0, 100);
    }
}
