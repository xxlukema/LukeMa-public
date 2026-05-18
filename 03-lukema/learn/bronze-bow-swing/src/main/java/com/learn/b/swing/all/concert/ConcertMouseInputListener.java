package com.learn.b.swing.all.concert;


import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;


public class ConcertMouseInputListener
    implements MouseInputListener {
    private int dx = SharedValue.DX;

    private int xLen = 0;

    public void mouseDragged(MouseEvent e) {
        ConcertLinePanel.MOUSE_DRAGGED = true;

        ConcertLinePanel.XZ = e.getX();
        ConcertLinePanel.YZ = e.getY();

        ConcertLinePanel.refresh();
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        ConcertLinePanel.MOUSE_DRAGGED = false;

        ConcertLinePanel.XA = e.getX();
        ConcertLinePanel.YA = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        if (ConcertLinePanel.MOUSE_DRAGGED) {
            ConcertLinePanel.XZ = e.getX();
            ConcertLinePanel.YZ = e.getY();

            ConcertLinePanel.refresh();

            TrendLine tl = new TrendLine(ConcertLinePanel.XA, ConcertLinePanel.YA, ConcertLinePanel.XZ, ConcertLinePanel.YZ);
            ConcertLinePanel.TREND_LINE_LIST.add(tl);

            ConcertLinePanel.MOUSE_DRAGGED = false;
        }
    }

    public void mouseMoved(MouseEvent e) {
        xLen = DataCollectionBase.getX().length;

        int pos = e.getX();

        int posIndex = 0;
        if (pos < DataCollectionBase.getX()[0]) {
            posIndex = 0;
        } else {
            posIndex = (int) ((pos - ConcertLineFrame.EDGE) / dx);
            if (pos > DataCollectionBase.getX()[xLen - 1] || posIndex > xLen - 1) {
                posIndex = xLen - 1;
            }
        }

        ConcertLinePanel.pos(posIndex);

        ConcertLinePanel.MOUSE_Y = e.getY();
    }
}
