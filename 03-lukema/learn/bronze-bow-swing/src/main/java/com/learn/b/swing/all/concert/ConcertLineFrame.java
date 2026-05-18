package com.learn.b.swing.all.concert;


import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class ConcertLineFrame
    extends JFrame {
    private static final long serialVersionUID = 1L;

    public static final int EDGE = ConcertLinePanel.EDGE;
    public static final int HEIGHT = 960;

    /**
     * Do not change 36. It is a magic number. 
     */
    public static final int WIDTH = ConcertLinePanel.WIDTH + 36;

    private static ConcertLineFrame FRAME = new ConcertLineFrame();

    public static void init() {
        FRAME.setDefaultCloseOperation(EXIT_ON_CLOSE);
        FRAME.setSize(WIDTH + 2 * EDGE, HEIGHT + 2 * EDGE);

        FRAME.getContentPane().setLayout(new GridLayout(1, 1));

        JScrollPane jsp = new JScrollPane();
        jsp.getVerticalScrollBar().setUnitIncrement(jsp.getVerticalScrollBar().getUnitIncrement() * 20);
        FRAME.getContentPane().add(jsp);

        JPanel jPanel = ConcertLinePanel.getInstance();
        jsp.setViewportView(jPanel);
    }

    public static void draw(String symbol, String dataUptodateMsg) {
        if (dataUptodateMsg != null) {
            ConcertLinePanel.DATA_UPTODATE_MSG = dataUptodateMsg;
        }

        String title = symbol;

        if (ConcertLinePanel.DATA_UPTODATE_MSG != null) {
            title += " ######## " + ConcertLinePanel.DATA_UPTODATE_MSG.replaceAll("\n", "").replaceAll("#", "");
        }

        if (ConcertLinePanel.TOMORROW_RANGE != null) {
            title += "   " + ConcertLinePanel.TOMORROW_RANGE;
        }

        FRAME.setTitle(title);
        FRAME.setVisible(true);
    }
}
