package com.learn.b.swing.all.concert;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;


public class ConcertLinePanel
    extends JPanel {
    // @formatter:off
    private static final long                      serialVersionUID   = 1L;
    private Image                                  offScreenImg       = null;
    private Graphics                               offScreenG         = null;
    public static final int                        EDGE               = 20;
    public static final int                        HISTORY_HEIGHT     = 330;
    public static final int                        TA_HEIGHT          = 53;
    public static final int                        TA_HEIGHT_LARGE    = 70;
    public static final int                        SEPARATOR_HEIGHT   = 20;
    public static final int                        X_L                = EDGE;
    public static final int                        HISTORY_H          = SEPARATOR_HEIGHT;
    public static final int                        HISTORY_L          = HISTORY_H + HISTORY_HEIGHT;
    public static final int                        VOLUME_H           = HISTORY_L + SEPARATOR_HEIGHT;
    public static final int                        VOLUME_L           = VOLUME_H + TA_HEIGHT;
    public static final int                        MACD_H             = VOLUME_L + SEPARATOR_HEIGHT;
    public static final int                        MACD_L             = MACD_H + TA_HEIGHT_LARGE;
    public static final int                        DMI_H              = MACD_L + SEPARATOR_HEIGHT;
    public static final int                        DMI_L              = DMI_H + TA_HEIGHT_LARGE;
    public static final int                        OBV_H              = DMI_L + SEPARATOR_HEIGHT;
    public static final int                        OBV_L              = OBV_H + TA_HEIGHT;
    public static final int                        CMO_H              = OBV_L + SEPARATOR_HEIGHT;
    public static final int                        CMO_L              = CMO_H + TA_HEIGHT_LARGE;
    public static final int                        RSI_H              = CMO_L + SEPARATOR_HEIGHT;
    public static final int                        RSI_L              = RSI_H + TA_HEIGHT_LARGE;
    public static final int                        EMA_H              = RSI_L + SEPARATOR_HEIGHT;
    public static final int                        EMA_L              = EMA_H + TA_HEIGHT_LARGE;
    public static final int                        AROON_H            = EMA_L + SEPARATOR_HEIGHT;
    public static final int                        AROON_L            = AROON_H + TA_HEIGHT;
    public static final int                        CHAIKIN_H          = AROON_L + SEPARATOR_HEIGHT;
    public static final int                        CHAIKIN_L          = CHAIKIN_H + TA_HEIGHT;
    public static final int                        STD_H              = CHAIKIN_L + SEPARATOR_HEIGHT;
    public static final int                        STD_L              = STD_H + TA_HEIGHT;
    public static final int                        RSQRD_H            = STD_L + SEPARATOR_HEIGHT;
    public static final int                        RSQRD_L            = RSQRD_H + TA_HEIGHT;
    public static final int                        ACCUM_DIST_H       = RSQRD_L + SEPARATOR_HEIGHT;
    public static final int                        ACCUM_DIST_L       = ACCUM_DIST_H + TA_HEIGHT;
    public static final int                        HEIGHT             = ACCUM_DIST_L;
    public static final int                        WIDTH              = 1_750;
    private static final ConcertMouseInputListener mouseInputListener = new ConcertMouseInputListener();
    private static int                             xPosIndex          = 0;
    private static int[]                           X                  = null;
    private static Stroke                          stroke1            = new BasicStroke(1);
    public static String                           DATA_UPTODATE_MSG  = null;
    private static Color                           BG_COLOR           = Color.LIGHT_GRAY;
    protected static List<TrendLine>               TREND_LINE_LIST    = new ArrayList<>();
    protected static int                           XA                 = 0;
    protected static int                           YA                 = 0;
    protected static int                           XZ                 = 0;
    protected static int                           YZ                 = 0;
    protected static boolean                       MOUSE_DRAGGED      = false;
    protected static int                           MOUSE_Y            = 0;
    public static String                           TOMORROW_RANGE     = null;
    
    private static final Lock                      LOCK               = new ReentrantLock();
    private static ConcertLinePanel                INSTANCE           = null;

    private static final HistoryDataCollection           HISTORY_DataCollection    = new HistoryDataCollection(HISTORY_L, HISTORY_H);
    private static final VolumeDataCollection            VOLUME_DataCollection     = new VolumeDataCollection(VOLUME_L, VOLUME_H);
    private static final RSIDataCollection               RSI_DataCollection        = new RSIDataCollection(RSI_L, RSI_H);
    private static final OnBalanceVolumeDataCollection   OBV_DataCollection        = new OnBalanceVolumeDataCollection(OBV_L, OBV_H);
    private static final DMIDataCollection               DMI_DataCollection        = new DMIDataCollection(DMI_L, DMI_H);
    private static final MACDDataCollection              MACD_DataCollection       = new MACDDataCollection(MACD_L, MACD_H);
    private static final EMADataCollection               EMA_DataCollection        = new EMADataCollection(EMA_L, EMA_H);
    private static final CMODataCollection               CMO_DataCollection        = new CMODataCollection(CMO_L, CMO_H);
    private static final STDDataCollection               STD_DataCollection        = new STDDataCollection(STD_L, STD_H);
    private static final AccumDistLineDataCollection     ACCUM_DIST_DataCollection = new AccumDistLineDataCollection(ACCUM_DIST_L, ACCUM_DIST_H);
    private static final RSquaredDataCollection          RSQRD_DataCollection      = new RSquaredDataCollection(RSQRD_L, RSQRD_H);
    private static final AroonDataCollection             AROON_DataCollection      = new AroonDataCollection(AROON_L, AROON_H);
    private static final ChaikinOscillatorDataCollection CHAIKIN_DataCollection    = new ChaikinOscillatorDataCollection(CHAIKIN_L, CHAIKIN_H);
    // @formatter:on

    private ConcertLinePanel() {
    }

    public static JPanel getInstance() {
        LOCK.lock();
        try {
            if (INSTANCE == null) {
                ConcertLinePanel concertLinePanel = new ConcertLinePanel();

                concertLinePanel.setPreferredSize(new Dimension(WIDTH + 2 * EDGE, HEIGHT + 2 * EDGE));
                concertLinePanel.setBackground(BG_COLOR);
                concertLinePanel.addMouseMotionListener(mouseInputListener);
                concertLinePanel.addMouseListener(mouseInputListener);

                INSTANCE = concertLinePanel;
            }
        } finally {
            LOCK.unlock();
        }

        return INSTANCE;
    }

    public void paint(Graphics g) {
        if (X == null) {
            X = DataCollectionBase.getX();
            if (X == null) {
                return;
            } else {
                xPosIndex = X.length - 1;
            }
        }

        if (offScreenImg == null) {
            offScreenImg = createImage(getSize().width, getSize().height);
            offScreenG = offScreenImg.getGraphics();
        }

        clearPanel(offScreenG);
        drawPanel(offScreenG);

        g.drawImage(offScreenImg, 0, 0, this);
    }

    public static void drawCross(Graphics g, int y_H, int y_L) {
        g.setColor(Color.BLACK);
        ((Graphics2D) g).setStroke(stroke1);

        g.drawLine(X[xPosIndex], y_H, X[xPosIndex], y_L);

        if (MOUSE_Y < HISTORY_L && MOUSE_Y > HISTORY_H) {
            g.drawLine(X_L, MOUSE_Y, X[X.length - 1], MOUSE_Y);
            String value = HISTORY_DataCollection.getYValueString(MOUSE_Y);

            g.drawString(value, X[X.length - 1] + 5, MOUSE_Y);
        }
    }

    public static String getDataUptodateMsg() {
        return DATA_UPTODATE_MSG;
    }

    public void clearPanel(Graphics g) {
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, WIDTH + 2 * EDGE, HEIGHT + 2 * EDGE);
    }

    public void drawPanel(Graphics g) {
        HISTORY_DataCollection.paint(offScreenG);
        HISTORY_DataCollection.drawValue(g, xPosIndex);

        VOLUME_DataCollection.paint(offScreenG);
        VOLUME_DataCollection.drawValue(g, xPosIndex);

        RSI_DataCollection.paint(offScreenG);
        RSI_DataCollection.drawValue(g, xPosIndex);

        OBV_DataCollection.paint(offScreenG);
        OBV_DataCollection.drawValue(g, xPosIndex);

        DMI_DataCollection.paint(offScreenG);
        DMI_DataCollection.drawValue(g, xPosIndex);

        MACD_DataCollection.paint(offScreenG);
        MACD_DataCollection.drawValue(g, xPosIndex);

        EMA_DataCollection.paint(offScreenG);
        EMA_DataCollection.drawValue(g, xPosIndex);

        CMO_DataCollection.paint(offScreenG);
        CMO_DataCollection.drawValue(g, xPosIndex);

        STD_DataCollection.paint(offScreenG);
        STD_DataCollection.drawValue(g, xPosIndex);

        ACCUM_DIST_DataCollection.paint(offScreenG);
        ACCUM_DIST_DataCollection.drawValue(g, xPosIndex);

        RSQRD_DataCollection.paint(offScreenG);
        RSQRD_DataCollection.drawValue(g, xPosIndex);

        AROON_DataCollection.paint(offScreenG);
        AROON_DataCollection.drawValue(g, xPosIndex);

        CHAIKIN_DataCollection.paint(offScreenG);
        CHAIKIN_DataCollection.drawValue(g, xPosIndex);

        for (TrendLine tl : TREND_LINE_LIST) {
            tl.drawLine(offScreenG);
        }

        if (MOUSE_DRAGGED) {
            TrendLine.drawLine(offScreenG, XA, YA, XZ, YZ);
        }
    }

    public static void refresh() {
        INSTANCE.repaint();
    }

    public static void pos(int posIndex) {
        xPosIndex = posIndex;

        INSTANCE.repaint();
    }
}
