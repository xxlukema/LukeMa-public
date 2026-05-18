package com.learn.applet.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.learn.applet.ChartJApplet;
import com.learn.common.util.ChartConstants;
import com.learn.common.util.MbaUtils;

public class ChartMouseInputListener extends MouseAdapter {
	private int xLen = 0;

	private ChartJApplet chartJApplet;

	private boolean mouseEntered = false;

	private boolean readyForEvent = true;

	public void setChartJApplet(ChartJApplet concertLineJApplet) {
		this.chartJApplet = concertLineJApplet;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		chartJApplet.setMouseDragged(true);

		chartJApplet.setTrendLineXZ(e.getX());
		chartJApplet.setTrendLineYZ(e.getY());

		chartJApplet.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		chartJApplet.setMouseDragged(false);

		chartJApplet.setTrendLineXA(e.getX());
		chartJApplet.setTrendLineYA(e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (chartJApplet.isMouseDragged()) {
			try {
				chartJApplet.setTrendLineXZ(e.getX());
				chartJApplet.setTrendLineYZ(e.getY());

				chartJApplet.repaint();

				TrendLine tl = new TrendLine(chartJApplet.getTrendLineXA(),
						chartJApplet.getTrendLineYA(),
						chartJApplet.getTrendLineXZ(),
						chartJApplet.getTrendLineYZ());
				chartJApplet.getTrendLineVector().add(tl);
			} finally {
				chartJApplet.setMouseDragged(false);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (readyForEvent) {
			readyForEvent = false;

			try {
				xLen = chartJApplet.getDays();

				int pos = e.getX();

				int posIndex = 0;
				if (pos < MbaUtils.getDateArray(chartJApplet.getDays(),
						chartJApplet.getDeltaX())[0]) {
					posIndex = 0;
				} else if (pos > MbaUtils.getDateArray(chartJApplet.getDays(),
						chartJApplet.getDeltaX())[xLen - 1]) {
					posIndex = xLen - 1;
				} else {
					posIndex = (int) ((pos - ChartConstants.FrameSideEdge) / chartJApplet
							.getDeltaX());
					if (posIndex > xLen - 1) {
						posIndex = xLen - 1;
					}
				}

				chartJApplet.setMouseY(e.getY());
				chartJApplet.mouseMoved(posIndex);
			} finally {
				readyForEvent = true;
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (readyForEvent) {
			readyForEvent = false;

			if (mouseEntered) {
				try {
					// Negative: Moved up. Positive: Moved down.
					int wheelRotation = e.getWheelRotation();

					if (wheelRotation < 0) // Zoom in:
					{
						if (chartJApplet.getDays() > MbaUtils.MinDays) {
							chartJApplet.setDays(chartJApplet.getDays() / 2);
							chartJApplet
									.setDeltaX(chartJApplet.getDeltaX() * 2);

							chartJApplet.repaint();
						}
					} else if (wheelRotation > 0)
					// Zoom out:
					{
						if (chartJApplet.getDays() < MbaUtils.MaxDays) {
							int days = chartJApplet.getDays() * 2;

							if (days <= ChartJApplet.RawDataSize) {
								chartJApplet.setDays(days);
								chartJApplet
										.setDeltaX(chartJApplet.getDeltaX() / 2);

								chartJApplet.repaint();
							}
						}
					}
				} finally {
					readyForEvent = true;
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseEntered = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseEntered = false;
	}

}
