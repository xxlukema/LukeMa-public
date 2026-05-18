package com.learn.b.swing.all.concert;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.common.Setting;
import com.learn.b.swing.all.common.Utility;
import com.learn.b.swing.all.raw.RawDataUtils;


abstract public class DataCollectionBase {
    // @formatter:off
    private int                   y_L;
    private int                   y_H;
    private boolean               initialized = false;
    private final List<float[]>   RAW_Y       = new ArrayList<>();
    private final List<int[]>     ADJUSTED_Y  = new ArrayList<>();
    private static int[]          X;
    private float                 yMin;
    private float                 yMax;
    private String                desc;
    public static final Stroke    STROKE1     = new BasicStroke(1);
    public static final Stroke    STROKE2     = new BasicStroke(2);
    public static final Stroke    STROKE3     = new BasicStroke(3);
    public static final Stroke    STROKE4     = new BasicStroke(4);
    // @formatter:on

    public DataCollectionBase(int y_L, int y_H) {
        this.y_L = y_L;
        this.y_H = y_H;

        init();
    }

    abstract public void addData();

    abstract public void paint(Graphics g);

    abstract public void descMe();

    abstract public String getValueString(int index);

    public void drawCross(Graphics g) {
        ConcertLinePanel.drawCross(g, y_H, y_L);
    }

    public String getYValueString(int y) {
        float value = (float) (yMax - (yMax - yMin) * (float) (y - y_H) / (y_L - y_H));

        return Utility.float2str(value, 2);
    }

    public void drawValue(Graphics g, int xPosIndex) {
        String valueString = getValueString(xPosIndex);
        if (valueString != null) {
            int x = X[0] + 920;
            int y = y_H - 3;

            g.setColor(Color.LIGHT_GRAY);

            g.setColor(Color.RED);
            g.drawString(valueString, x, y);
        }
    }

    public static int[] getX() {
        initX(ConcertLinePanel.X_L);
        return X;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getY_H() {
        return y_H;
    }

    public int getY_L() {
        return y_L;
    }

    public void init() {
        if (initialized) {
            return;
        } else {
            initX(ConcertLinePanel.X_L);

            addData();
            adjustY();
            initialized = true;

            descMe();
        }
    }

    private static void initX(int x_L) {
        int len = RawDataUtils.getCHART_DAYS();

        X = new int[len];

        if (X[0] > 0) {
            return;
        }

        for (int i = 0; i < RawDataUtils.getCHART_DAYS(); i++) {
            X[i] = (int) (x_L + i * SharedValue.DX);
        }
    }

    public void drawGrid(Graphics g) {
        // draw half cycle lines as thicker lines
        g.setColor(Color.CYAN);
        Stroke s = new BasicStroke(2);
        ((Graphics2D) g).setStroke(s);
        for (int i = X.length - 1; i >= 0; i -= Setting.HALF_CYCLE) {
            g.drawLine(X[i], y_L, X[i], y_H);
        }

        s = new BasicStroke(1);
        ((Graphics2D) g).setStroke(s);

        for (int i = 0; i < X.length; i++) {
            g.drawLine(X[i], y_L, X[i], y_H);
        }

        g.drawLine(X[0], y_L, X[X.length - 1], y_L);
        g.drawLine(X[0], y_H, X[X.length - 1], y_H);

        drawQuarterLine(g, 63);
        drawQuarterLine(g, 126);
        drawQuarterLine(g, 189);
        drawQuarterLine(g, 252);

        drawDataUptodateMsg(g);
    }

    public void drawQuarterLine(Graphics g, int quarterDaysBack) {
        int quarterDaysBackIndex = X.length - quarterDaysBack;
        if (quarterDaysBackIndex >= 0) {
            g.setColor(Color.ORANGE);
            Stroke s = new BasicStroke(2);
            ((Graphics2D) g).setStroke(s);
            g.drawLine(X[quarterDaysBackIndex], y_L, X[quarterDaysBackIndex], y_H);
        }
    }

    public void drawDesc(Graphics g) {
        if (desc != null) {
            g.setColor(Color.BLACK);
            g.drawString(desc, X[0] + 10, y_H - 3);
            int pos1 = desc.indexOf(' ');
            int pos2 = desc.indexOf(':');
            int pos = 0;
            if (pos2 > -1 && pos1 > -1) {
                pos = Math.min(pos1, pos2);
            } else {
                pos = Math.max(pos1, pos2);
            }
            String shortDesc = null;
            if (pos > -1) {
                shortDesc = desc.substring(0, pos);
            } else {
                shortDesc = desc;
            }
            g.drawString(shortDesc, X[X.length - 1] - 80, y_H - 3);
        }

        drawMinMax(g);
    }

    public void drawDataUptodateMsg(Graphics g) {
        String msg = ConcertLinePanel.DATA_UPTODATE_MSG;
        if (msg != null) {
            g.setColor(Color.BLACK);
            String[] lines = msg.split("\n");
            for (int i = 0; i < lines.length; i++) {
                g.drawString(lines[i], X[X.length - 1] - 200, y_H + 14 * (i + 1));
            }
        }
    }

    public boolean isKUint() {
        return false;
    }

    public void drawMinMax(Graphics g) {
        g.setColor(Color.BLACK);
        if (isKUint()) {
            g.drawString(roundFloat2String(yMax) + "K", X[X.length - 1] + 5, y_H + 10);
            g.drawString(roundFloat2String(yMin) + "K", X[X.length - 1] + 5, y_L);
        } else {
            g.drawString(roundFloat2String(yMax), X[X.length - 1] + 5, y_H + 10);
            g.drawString(roundFloat2String(yMin), X[X.length - 1] + 5, y_L);
        }
    }

    public static String roundFloat2String(float f) {
        if (f == 0) {
            return "0";
        } else if (f > -1 && f < 1) {
            return Utility.float2str(f, 2);
        } else if (f > -10 && f < 10) {
            return Utility.float2str(f, 1);
        } else {
            return Utility.commaDelim((int) f);
        }
    }

    public void drawLine(Graphics g, int[] y, Color c) {
        drawLine(g, y, c, 1);
    }

    public void drawLine(Graphics g, int[] y, Color c, int lineWidth) {
        Stroke s = new BasicStroke(lineWidth);
        ((Graphics2D) g).setStroke(s);
        if (c == null) {
            c = Color.BLACK;
        }
        g.setColor(c);

        g.drawPolyline(X, y, X.length);
    }

    public void drawDashLine(Graphics g, int[] y, Color c) {
        if (c == null) {
            c = Color.BLACK;
        }
        g.setColor(c);

        g.drawPolyline(X, y, X.length);
    }

    public void addY(float[] yy) {
        if (yy != null && yy.length == X.length) {
            RAW_Y.add(yy);
        } else {
            if (yy == null) {
                System.out.println("****** \"float [] yy\" is null.");
            } else {
                System.out.println("****** yy.length=" + yy.length + " X.length=" + X.length);
            }
        }
    }

    private void adjustY() {
        adjustY(Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    public void adjustY(float min, float max) {

        yMin = min;
        yMax = max;

        for (int v = 0; v < RAW_Y.size(); v++) {
            float[] yy = RAW_Y.get(v);

            for (int i = 0; i < yy.length; i++) {
                yMax = Math.max(yy[i], yMax);
                yMin = Math.min(yy[i], yMin);
            }
        }

        float fy = (float) ((y_L - y_H) / (yMax - yMin));

        for (int v = 0; v < RAW_Y.size(); v++) {
            float[] yy = RAW_Y.get(v);

            int[] y = new int[yy.length];
            for (int i = 0; i < yy.length; i++) {
                y[i] = (int) (y_L - (yy[i] - yMin) * fy);

                if (y[i] < y_H) {
                    y[i] = y_H;
                }
            }

            ADJUSTED_Y.add(y);
        }
    }

    public List<int[]> getADJUSTED_Y() {
        return ADJUSTED_Y;
    }

}
