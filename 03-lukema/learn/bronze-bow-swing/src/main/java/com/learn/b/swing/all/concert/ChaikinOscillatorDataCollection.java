package com.learn.b.swing.all.concert;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.learn.b.swing.all.raw.RawDataUtils;
import com.learn.b.swing.all.chaikin.ChaikinOscillatorData;
import com.learn.b.swing.all.chaikin.ChaikinOscillatorDataUtils;


public class ChaikinOscillatorDataCollection
    extends DataCollectionBase {
    private static float[] dimensionless = null;

    public ChaikinOscillatorDataCollection(int y_L, int y_H) {
        super(y_L, y_H);
    }

    public void descMe() {
        // setDesc("ChaikinOscillator: EMA3-EMA10 black");
        setDesc("ChaikinOscillator: Dimensionless Chaikin black");
    }

    /*
    public boolean isKUint()
    {
      return true;
    }
    */

    public String getValueString(int index) {
        if (dimensionless != null) {
            return "Dimensionless Chaikin=" + roundFloat2String(dimensionless[index]);
        } else {
            return null;
        }
    }

    public void addData() {
        int len = RawDataUtils.getCHART_DAYS();

        dimensionless = new float[len];

        for (int i = 0; i < len; i++) {
            int k = RawDataUtils.getData().size() - len + i;
            if (k < 0) {
                continue;
            }

            // dimensionless[i] = ((ChaikinOscillatorData) chaikinV.get(i)).getEMA5_EMA10() / 1000;
            dimensionless[i] = ((ChaikinOscillatorData) ChaikinOscillatorDataUtils.getData().get(k)).getDimensionless();
        }

        addY(dimensionless);
    }

    public void paint(Graphics g) {
        drawGrid(g);
        drawAllGrid(g);
        drawCross(g);
        drawChaikinOscillator(g);
        drawDesc(g);
    }

    private void drawAllGrid(Graphics g) {
        int[] x = getX();

        if (x != null) {
            ((Graphics2D) g).setStroke(STROKE1);

            g.setColor(Color.WHITE);
            int dy = (getY_H() - getY_L()) / 2;
            int yPos = getY_L() + dy;
            g.drawLine(x[0], yPos, x[x.length - 1], yPos);
            g.drawString("0", x[x.length - 1] + 5, yPos + 5);

            dy = (int) ((getY_H() - getY_L()) * 0.15);

            yPos = getY_H() - dy;
            g.drawLine(x[0], yPos, x[x.length - 1], yPos);
            g.drawString("0.85", x[x.length - 1] + 33, yPos + 5);

            yPos = getY_L() + dy;
            g.drawLine(x[0], yPos, x[x.length - 1], yPos);
            g.drawString("-0.85", x[x.length - 1] + 33, yPos + 5);
        }
    }

    private void drawChaikinOscillator(Graphics g) {
        int[] dimensionless = (int[]) getADJUSTED_Y().get(0);

        drawLine(g, dimensionless, Color.BLACK, 2);
    }
}
