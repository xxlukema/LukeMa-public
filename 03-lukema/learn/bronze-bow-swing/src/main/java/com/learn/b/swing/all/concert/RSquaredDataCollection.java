package com.learn.b.swing.all.concert;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.learn.b.swing.all.raw.RawDataUtils;
import com.learn.b.swing.all.rsquared.RSquaredDataUtils;


public class RSquaredDataCollection
    extends DataCollectionBase {
    
    // @formatter:off
    private float[] slope         = null;
    private float[] adjustedSlope = null;
    private float[] rsquared      = null;
    private float   MIN           = Integer.MAX_VALUE;
    private float   MAX           = Integer.MIN_VALUE;
    private float   SLOPE_MIN     = Integer.MAX_VALUE;
    private float   SLOPE_MAX     = Integer.MIN_VALUE;
    private float   SLOPE_FACTOR  = 0;
    // @formatter:on

    public RSquaredDataCollection(int y_L, int y_H) {
        super(y_L, y_H);
    }

    public void descMe() {
        setDesc("RSquared: Linear Regression Slope-black, R Squared-blue");
    }

    public String getValueString(int index) {
        if (slope != null) {
            String ret = "";
            if (SLOPE_FACTOR > 1) {
                ret += "Actual ";
            }

            ret += "Slope=" + roundFloat2String(slope[index]);
            ret += " R Squared=" + roundFloat2String(rsquared[index]);

            return ret;
        } else {
            return null;
        }
    }

    public void addData() {
        int len = RawDataUtils.getCHART_DAYS();

        slope = new float[len];
        rsquared = new float[len];

        for (int i = 0; i < len; i++) {
            int k = RawDataUtils.getData().size() - len + i;
            if (k < 0) {
                continue;
            }

            slope[i] = RSquaredDataUtils.getData().get(k).getSlope();
            rsquared[i] = RSquaredDataUtils.getData().get(k).getRsquared();
        }

        // Find zero point
        for (int i = 0; i < slope.length; i++) {
            MIN = Math.min(MIN, slope[i]);
            MIN = Math.min(MIN, rsquared[i]);

            MAX = Math.max(MAX, slope[i]);
            MAX = Math.max(MAX, rsquared[i]);

            SLOPE_MIN = Math.min(MIN, slope[i]);
            SLOPE_MAX = Math.max(MAX, slope[i]);
        }

        SLOPE_FACTOR = Math.max(Math.abs(SLOPE_MIN), SLOPE_MAX);

        if (SLOPE_FACTOR > 1) {
            adjustedSlope = new float[len];

            for (int i = 0; i < slope.length; i++) {
                adjustedSlope[i] = (float) (slope[i] / SLOPE_FACTOR);
            }

            addY(adjustedSlope);
        } else {
            addY(slope);
        }

        addY(rsquared);
    }

    public void paint(Graphics g) {
        drawGrid(g);
        drawAllGrid(g);
        drawCross(g);
        drawRSquared(g);
        drawDesc(g);
    }

    private void drawRSquared(Graphics g) {
        int[] slope = (int[]) getADJUSTED_Y().get(0);
        int[] rsquared = (int[]) getADJUSTED_Y().get(1);

        Color slopColor = Color.BLACK;
        if (SLOPE_FACTOR > 1) {
            slopColor = Color.RED;
        }
        drawLine(g, slope, slopColor, 2);
        drawLine(g, rsquared, Color.BLUE, 2);
    }

    private void drawAllGrid(Graphics g) {
        int[] x = getX();

        if (x != null) {
            ((Graphics2D) g).setStroke(STROKE1);

            g.setColor(Color.WHITE);
            float dy = (float) (MAX / (MAX - MIN)) * (getY_L() - getY_H());
            int yPos = getY_H() + (int) dy;
            g.drawLine(x[0], yPos, x[x.length - 1], yPos);
            g.drawString("0", x[x.length - 1] + 5, yPos + 5);

            // 95% confidence/coverage line (0.77)
            g.setColor(Color.WHITE);
            dy = (float) (MAX * (1.0 - 0.77) / (MAX - MIN)) * (getY_L() - getY_H());
            yPos = getY_H() + (int) dy;
            g.drawLine(x[0], yPos, x[x.length - 1], yPos);
            g.drawString("0.77(95%)", x[x.length - 1] + 5, yPos + 5);
        }
    }
}
