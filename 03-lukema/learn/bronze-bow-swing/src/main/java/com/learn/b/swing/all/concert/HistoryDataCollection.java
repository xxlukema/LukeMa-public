package com.learn.b.swing.all.concert;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.learn.b.swing.all.bollinger.BollingerDataUtils;
import com.learn.b.swing.all.common.TraderBase;
import com.learn.b.swing.all.common.Utility;
import com.learn.b.swing.all.ema.EMAData;
import com.learn.b.swing.all.ema.EMADataUtils;
import com.learn.b.swing.all.momentum.MomentumData;
import com.learn.b.swing.all.momentum.MomentumDataUtils;
import com.learn.b.swing.all.raw.RawDataUtils;


public class HistoryDataCollection
    extends DataCollectionBase {

    // @formatter:off
    private static String[] date           = null;
    private static float[]  open           = null;
    private static float[]  high           = null;
    private static float[]  low            = null;
    private static float[]  close          = null;
    private static String   tomorrowRanges = null;
    // @formatter:on

    public HistoryDataCollection(int y_L, int y_H) {
        super(y_L, y_H);
    }

    // Oil has 110 data.
    private boolean isStock() {
        return RawDataUtils.getData().size() > 150;
    }

    private boolean isFarZoom() {
        return SharedValue.ZOOM_SIZE == ZoomSize.FAR;
    }

    public void descMe() {
        String desc = "History: Bollinger-cyan. EMA: ";

        if (isFarZoom()) {
            desc += "25day-blue 50day-red 15d-grey";
        } else {
            if (isStock()) {
                desc += "5day-blue 10day-red 15d-grey";
            } else {
                desc += "5day-blue 10day-red";
            }
        }

        setDesc(desc);
    }

    public String getValueString(int index) {
        if (date != null) {
            return dateToWeek(index) + " Date=" + date[index] + " Open=" + Utility.float2str(open[index], 2) + " High="
                    + Utility.float2str(high[index], 2) + " Low="
                    + Utility.float2str(low[index], 2) + " Close=" + Utility.float2str(close[index], 2);
        } else {
            return null;
        }
    }

    public String dateToWeek(int index) {
        String strDate = date[index];
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(strDate);

            df = new SimpleDateFormat("EEE");
            return df.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    public void addData() {
        addRawData();
        addBollingerData();
        addMomentumData();
        addEMAData();
    }

    private void addRawData() {
        int len = RawDataUtils.getCHART_DAYS();
        date = new String[len];
        open = new float[len];
        high = new float[len];
        low = new float[len];
        close = new float[len];

        for (int i = 0; i < len; i++) {
            int k = RawDataUtils.getData().size() - len + i;
            if (k < 0) {
                continue;
            }

            date[i] = RawDataUtils.getData().get(k).getDate();
            open[i] = RawDataUtils.getData().get(k).getOpen();
            high[i] = RawDataUtils.getData().get(k).getHigh();
            low[i] = RawDataUtils.getData().get(k).getLow();
            close[i] = RawDataUtils.getData().get(k).getClose();
        }

        addY(open);
        addY(high);
        addY(low);
        addY(close);
    }

    private void addBollingerData() {
        int len = RawDataUtils.getCHART_DAYS();
        float[] bollingerUpper = new float[len];
        float[] bollingerLower = new float[len];

        for (int i = 0; i < len; i++) {
            int k = RawDataUtils.getData().size() - len + i;
            if (k < 0) {
                continue;
            }

            bollingerUpper[i] = BollingerDataUtils.getData().get(k).getUpperBand();
            bollingerLower[i] = BollingerDataUtils.getData().get(k).getLowerBand();
        }

        addY(bollingerUpper);
        addY(bollingerLower);
    }

    private void addEMAData() {

        List<EMAData> ema05V = EMADataUtils.getData05();
        List<EMAData> ema10V = EMADataUtils.getData10();

        List<EMAData> ema15V = null;
        List<EMAData> ema25V = null;
        List<EMAData> ema50V = null;

        ema15V = EMADataUtils.getData15();
        ema25V = EMADataUtils.getData25();
        ema50V = EMADataUtils.getData50();

        int len = RawDataUtils.getCHART_DAYS();
        float[] ema05 = new float[len];
        float[] ema10 = new float[len];

        float[] ema15 = new float[len];
        float[] ema25 = new float[len];
        float[] ema50 = new float[len];

        for (int i = 0; i < len; i++) {
            int k = RawDataUtils.getData().size() - len + i;
            if (k < 0) {
                continue;
            }

            ema05[i] = ((EMAData) ema05V.get(k)).getEma();
            ema10[i] = ((EMAData) ema10V.get(k)).getEma();
            ema15[i] = ((EMAData) ema15V.get(k)).getEma();
            ema25[i] = ((EMAData) ema25V.get(k)).getEma();
            ema50[i] = ((EMAData) ema50V.get(k)).getEma();
        }

        addY(ema05);
        addY(ema10);
        addY(ema15);
        addY(ema25);
        addY(ema50);
    }

    private void addMomentumData() {
        int len = RawDataUtils.getCHART_DAYS();
        int yesterdayIndex = Math.max(0, MomentumDataUtils.getData().size() - len - 1);
        MomentumData yesterdayMD = MomentumDataUtils.getData().get(yesterdayIndex);

        MomentumData md = null;

        float[] highTBP = new float[len];
        float[] lowTBP = new float[len];
        float[] highTarget = new float[len];
        float[] lowTarget = new float[len];
        float[] highStop = new float[len];
        float[] lowStop = new float[len];
        float[] tomorrowHighTarget = new float[len];
        float[] tomorrowLowTarget = new float[len];
        float[] tomorrowHighStop = new float[len];
        float[] tomorrowLowStop = new float[len];

        for (int i = 0; i < len; i++) {
            int k = RawDataUtils.getData().size() - len + i;
            if (k < 0) {
                continue;
            }

            md = (MomentumData) MomentumDataUtils.getData().get(k);
            highTBP[i] = md.getHighTBP();
            lowTBP[i] = md.getLowTBP();
            highTarget[i] = yesterdayMD.getHighTarget();
            lowTarget[i] = yesterdayMD.getLowTarget();
            highStop[i] = yesterdayMD.getHighStop();
            lowStop[i] = yesterdayMD.getLowStop();
            tomorrowHighTarget[i] = md.getHighTarget();
            tomorrowLowTarget[i] = md.getLowTarget();
            tomorrowHighStop[i] = md.getHighStop();
            tomorrowLowStop[i] = md.getLowStop();

            yesterdayMD = md;
        }

        tomorrowRanges = "(" + Utility.float2str(tomorrowLowStop[len - 1], 2) + " | " + Utility.float2str(tomorrowLowTarget[len - 1], 2) + ",  "
                + Utility.float2str(tomorrowHighTarget[len - 1], 2) + " | " + Utility.float2str(tomorrowHighStop[len - 1], 2) + ")";

        System.out.println("\n\t" + TraderBase.getSymbol() + ": " + tomorrowRanges + "\n");

        String weekString = TraderBase.retrieveWeekString();

        ConcertLinePanel.TOMORROW_RANGE = weekString + "   " + tomorrowRanges;

        addY(highTBP);
        addY(lowTBP);
        addY(highTarget);
        addY(lowTarget);
        addY(highStop);
        addY(lowStop);
        addY(tomorrowHighTarget);
        addY(tomorrowLowTarget);
        addY(tomorrowHighStop);
        addY(tomorrowLowStop);
    }

    public void paint(Graphics g) {
        drawGrid(g);
        drawCross(g);
        drawBollinger(g);
        drawMomentum(g);
        drawEMA(g);
        drawOHLC(g);
        drawMomentumWarning(g);
        drawDesc(g);
    }

    private void drawOHLC(Graphics g) {
        int[] open = (int[]) getADJUSTED_Y().get(0);
        int[] high = (int[]) getADJUSTED_Y().get(1);
        int[] low = (int[]) getADJUSTED_Y().get(2);
        int[] close = (int[]) getADJUSTED_Y().get(3);

        int lineWidth = 2;
        int openCloseLength = 3;

        if (!isFarZoom()) {
            lineWidth = 2;
            openCloseLength = 3;
        } else {
            lineWidth = 1;
            openCloseLength = 2;
        }

        int[] x = getX();
        Stroke s = new BasicStroke(lineWidth);

        for (int i = 0; i < open.length; i++) {
            Color c = null;

            // these open/close are after adjustment for drawing.
            // if open > close, the actual values before adjust
            // for draw is open < close.
            if (open[i] > close[i]) {
                c = Color.BLUE;
            } else {
                c = Color.RED;
            }

            g.setColor(c);

            ((Graphics2D) g).setStroke(s);

            g.drawLine(x[i], low[i], x[i], high[i]);
            g.drawLine(x[i] - openCloseLength, open[i], x[i], open[i]);
            g.drawLine(x[i] + openCloseLength, close[i], x[i], close[i]);
        }
    }

    private void drawBollinger(Graphics g) {
        int[] bollingerUpper = (int[]) getADJUSTED_Y().get(4);
        int[] bollingerLower = (int[]) getADJUSTED_Y().get(5);

        Color c = Color.CYAN;
        drawLine(g, bollingerUpper, c, 1);
        drawLine(g, bollingerLower, c, 1);
    }

    private void drawEMA(Graphics g) {
        int[] ema05 = (int[]) getADJUSTED_Y().get(16);
        int[] ema10 = (int[]) getADJUSTED_Y().get(17);
        int[] ema15 = null;
        int[] ema25 = null;
        int[] ema50 = null;

        // Oil has 110 data.
        if (isStock()) {
            ema15 = (int[]) getADJUSTED_Y().get(18);

            if (isFarZoom()) {
                ema25 = (int[]) getADJUSTED_Y().get(19);
                ema50 = (int[]) getADJUSTED_Y().get(20);
            }
        }

        float dash05[] = { 5.0f, 2.0f };
        float dash10[] = { 10.0f, 5.0f };
        float dash15[] = { 15.0f, 7.0f };
        float dash25[] = { 25.0f, 12.0f };
        float dash50[] = { 50.0f, 25.0f };

        float lineWidth = 3.0f;
        if (isFarZoom()) {
            lineWidth = 2.0f;
        }

        BasicStroke dashed05 = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash05, 0.0f);
        BasicStroke dashed10 = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash10, 0.0f);
        BasicStroke dashed15 = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash15, 0.0f);
        BasicStroke dashed25 = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash25, 0.0f);
        BasicStroke dashed50 = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash50, 0.0f);

        Color c05 = Color.BLUE;
        Color c10 = Color.RED;
        Color c15 = Color.GRAY;
        Color c25 = Color.BLUE;
        Color c50 = Color.RED;

        if (!isFarZoom()) {
            ((Graphics2D) g).setStroke(dashed05);
            drawDashLine(g, ema05, c05);

            ((Graphics2D) g).setStroke(dashed10);
            drawDashLine(g, ema10, c10);
        }

        // Oil has 110 data.
        if (isStock()) {
            ((Graphics2D) g).setStroke(dashed15);
            drawDashLine(g, ema15, c15);

            if (isFarZoom()) {
                ((Graphics2D) g).setStroke(dashed25);
                drawDashLine(g, ema25, c25);

                ((Graphics2D) g).setStroke(dashed50);
                drawDashLine(g, ema50, c50);
            }
        }
    }

    private void drawMomentum(Graphics g) {
        drawMomentumTBP(g);
        drawMomentumTarget(g);
        drawMomentumStop(g);
        drawMomentumTomorrow(g);
    }

    private void drawMomentumTBP(Graphics g) {
        int[] highTBP = (int[]) getADJUSTED_Y().get(6);
        int[] lowTBP = (int[]) getADJUSTED_Y().get(7);

        Color c = Color.WHITE;
        drawLine(g, highTBP, c, 2);
        drawLine(g, lowTBP, c, 2);
    }

    private void drawMomentumWarning(Graphics g) {
        int[] high = (int[]) getADJUSTED_Y().get(1);
        int[] low = (int[]) getADJUSTED_Y().get(2);
        int[] highTBP = (int[]) getADJUSTED_Y().get(6);
        int[] lowTBP = (int[]) getADJUSTED_Y().get(7);
        int[] highTarget = (int[]) getADJUSTED_Y().get(8);
        int[] lowTarget = (int[]) getADJUSTED_Y().get(9);
        int[] x = getX();

        Stroke s = null;
        int diameter = 0;
        if (SharedValue.ZOOM_SIZE == ZoomSize.CLOSE) {
            diameter = 5;
            s = new BasicStroke(3);
        } else if (SharedValue.ZOOM_SIZE == ZoomSize.MEDIUM) {
            diameter = 4;
            s = new BasicStroke(2);
        } else {
            diameter = 3;
            s = new BasicStroke(2);
        }

        ((Graphics2D) g).setStroke(s);
        for (int i = 0; i < x.length; i++) {
            // if(highTBP[i] > Math.max(lowTarget[i], low[i]))
            if (highTBP[i] > low[i]) {
                g.setColor(Color.BLUE);
                if (highTBP[i] > lowTarget[i]) {
                    g.fillOval(x[i] - diameter, highTBP[i] - diameter, 2 * diameter, 2 * diameter);
                } else {
                    g.drawOval(x[i] - diameter, highTBP[i] - diameter, 2 * diameter, 2 * diameter);
                }
            }
            // else if(lowTBP[i] < Math.min(highTarget[i], high[i]))
            else if (lowTBP[i] < high[i]) {
                g.setColor(Color.RED);
                if (lowTBP[i] < highTarget[i]) {
                    g.fillOval(x[i] - diameter, lowTBP[i] - diameter, 2 * diameter, 2 * diameter);
                } else {
                    g.drawOval(x[i] - diameter, lowTBP[i] - diameter, 2 * diameter, 2 * diameter);
                }
            }
        }
    }

    private void drawMomentumTarget(Graphics g) {
        int[] highTarget = (int[]) getADJUSTED_Y().get(8);
        int[] lowTarget = (int[]) getADJUSTED_Y().get(9);

        Color c = Color.YELLOW;
        drawLine(g, highTarget, c, 3);
        drawLine(g, lowTarget, c, 3);

        c = Color.MAGENTA;
        drawLine(g, highTarget, c, 1);
        drawLine(g, lowTarget, c, 1);
    }

    private void drawMomentumStop(Graphics g) {
        int[] highStop = (int[]) getADJUSTED_Y().get(10);
        int[] lowStop = (int[]) getADJUSTED_Y().get(11);

        Color c = Color.MAGENTA;
        drawLine(g, highStop, c, 1);
        drawLine(g, lowStop, c, 1);
    }

    private void drawMomentumTomorrow(Graphics g) {
        int[] tomorrowHighTarget = new int[2];
        int[] tomorrowLowTarget = new int[2];
        int[] tomorrowHighStop = new int[2];
        int[] tomorrowLowStop = new int[2];

        int[] tomHighTarget = (int[]) getADJUSTED_Y().get(12);
        int[] tomLowTarget = (int[]) getADJUSTED_Y().get(13);
        int[] tomHighStop = (int[]) getADJUSTED_Y().get(14);
        int[] tomLowStop = (int[]) getADJUSTED_Y().get(15);

        for (int i = 0; i < tomorrowHighTarget.length; i++) {
            tomorrowHighTarget[i] = tomHighTarget[tomHighTarget.length - 2 + i];
            tomorrowLowTarget[i] = tomLowTarget[tomHighTarget.length - 2 + i];
            tomorrowHighStop[i] = tomHighStop[tomHighStop.length - 2 + i];
            tomorrowLowStop[i] = tomLowStop[tomHighStop.length - 2 + i];
        }

        int[] tomX = new int[2];
        tomX[0] = getX()[getX().length - 1];
        tomX[1] = tomX[0] + SharedValue.DX;

        Color c = Color.YELLOW;
        g.setColor(c);
        Stroke s = new BasicStroke(3);
        ((Graphics2D) g).setStroke(s);
        g.drawPolyline(tomX, tomorrowHighTarget, tomX.length);
        g.drawPolyline(tomX, tomorrowLowTarget, tomX.length);

        c = Color.MAGENTA;
        g.setColor(c);
        s = new BasicStroke(1);
        ((Graphics2D) g).setStroke(s);
        g.drawPolyline(tomX, tomorrowHighTarget, tomX.length);
        g.drawPolyline(tomX, tomorrowLowTarget, tomX.length);

        g.drawPolyline(tomX, tomorrowHighStop, tomX.length);
        g.drawPolyline(tomX, tomorrowLowStop, tomX.length);
    }
}
