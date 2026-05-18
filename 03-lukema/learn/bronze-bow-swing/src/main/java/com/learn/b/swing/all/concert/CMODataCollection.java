package com.learn.b.swing.all.concert;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.learn.b.swing.all.cmo.CMOData;
import com.learn.b.swing.all.cmo.CMODataUtils;
import com.learn.b.swing.all.common.Utility;
import com.learn.b.swing.all.raw.RawDataUtils;


public class CMODataCollection
    extends DataCollectionBase {

    private static float[] cmo05 = null;
    private static float[] cmo10 = null;

    public CMODataCollection(int y_L, int y_H) {
        super(y_L, y_H);
    }

    public void descMe() {
        setDesc("CMO: 5-blue 10-red");
    }

    public void addData() {
        int len = RawDataUtils.getCHART_DAYS();

        cmo05 = new float[len];
        cmo10 = new float[len];

        for (int i = 0; i < len; i++) {
            int k = RawDataUtils.getData().size() - len + i;
            if (k < 0) {
                continue;
            }

            cmo05[i] = ((CMOData) CMODataUtils.getData05().get(k)).getCmo();
            cmo10[i] = ((CMOData) CMODataUtils.getData10().get(k)).getCmo();
        }

        addY(cmo05);
        addY(cmo10);
    }

    public String getValueString(int index) {
        if (cmo05 != null) {
            return "CMO05=" + Utility.float2str(cmo05[index], 0) + "   CMO10=" + Utility.float2str(cmo10[index], 0);
        } else {
            return null;
        }
    }

    public void paint(Graphics g) {
        drawGrid(g);
        drawAllGrid(g);
        drawCross(g);
        drawCMO10(g);
        drawCMO5(g);
        drawDesc(g);
    }

    private void drawAllGrid(Graphics g) {
        int[] x = getX();

        if (x != null) {
            ((Graphics2D) g).setStroke(STROKE1);

            g.setColor(Color.WHITE);
            int dy = (getY_H() - getY_L()) / 4;
            int yPos = getY_L() + dy;
            g.drawLine(x[0], yPos, x[x.length - 1], yPos);
            g.drawString("-50", x[x.length - 1] + 5, yPos + 5);
            yPos = getY_H() - dy;
            g.drawLine(x[0], yPos, x[x.length - 1], yPos);
            g.drawString("50", x[x.length - 1] + 5, yPos + 5);

            g.setColor(Color.WHITE);
            dy = (getY_H() - getY_L()) / 2;
            yPos = getY_H() - dy;
            g.drawLine(x[0], yPos, x[x.length - 1], yPos);
            g.drawString("0", x[x.length - 1] + 5, yPos + 5);
        }
    }

    private void drawCMO5(Graphics g) {
        int[] cmo = (int[]) getADJUSTED_Y().get(0);

        drawLine(g, cmo, Color.BLUE, 2);
    }

    private void drawCMO10(Graphics g) {
        int[] cmo = (int[]) getADJUSTED_Y().get(1);

        drawLine(g, cmo, Color.RED, 2);
    }

    public void adjustY() {
        adjustY(-100, 100);
    }
}
