package com.learn.applet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.JApplet;

import com.learn.applet.listener.ChartMouseInputListener;
import com.learn.applet.listener.TrendLine;
import com.learn.applet.painter.AccumDistLinePainter;
import com.learn.applet.painter.AroonPainter;
import com.learn.applet.painter.ChaikinOscillatorPainter;
import com.learn.applet.painter.CmoPainter;
import com.learn.applet.painter.DmiPainter;
import com.learn.applet.painter.EmaPainter;
import com.learn.applet.painter.HistoryPainter;
import com.learn.applet.painter.MacdPainter;
import com.learn.applet.painter.OnBalanceVolumePainter;
import com.learn.applet.painter.PainterBase;
import com.learn.applet.painter.RSquaredPainter;
import com.learn.applet.painter.RsiPainter;
import com.learn.applet.painter.StdPainter;
import com.learn.applet.painter.VolumePainter;
import com.learn.common.domain.AllDomainData;
import com.learn.common.domain.DataResponse;
import com.learn.common.domain.Dividend;
import com.learn.common.domain.HistoryDomainData;
import com.learn.common.util.ChartConstants;
import com.learn.common.util.DataStreamer;
import com.learn.common.util.MbaUtils;

public class ChartJApplet extends JApplet {
	private static final long serialVersionUID = 1L;

	private HistoryDomainData historyDomainData;

	private Image offScreenImg;

	private Graphics offScreenG;

	private int xPosIndex = 0;

	private Color backgroundColor = Color.LIGHT_GRAY;

	private final Vector<TrendLine> trendLineVector = new Vector<TrendLine>();

	private int trendLineXA = 0;

	private int trendLineYA = 0;

	private int trendLineXZ = 0;

	private int trendLineYZ = 0;

	private boolean mouseDragged = false;

	private int mouseY = 0;

	private final List<PainterBase> dataCollectionList = new LinkedList<PainterBase>();

	private static PainterBase historyDataCollection;

	private String symbol = "geos";

	private boolean success;

	private String errorMessage;

	private List<String> errorMessages = new LinkedList<String>();

	public static int RawDataSize;

	public static final int MinDeltaX = 3;

	public static final int MaxDeltaX = MinDeltaX * MbaUtils.MaxZoomTimes;

	private int days = MbaUtils.MinDays * 4;

	private int deltaX = MaxDeltaX / 4;

	private int[] dateArray;

	private static Vector<Dividend> dividends;

	public void init() {
		PainterBase.setChartJApplet(this); // This must be at the first line.

		symbol = getParameter("symbol");

		if (symbol == null || symbol.trim().length() == 0) {
			symbol = "Lvs";
		}

		setSymbol(symbol);

		String chartData = getParameter("chartData");

		success = false;
		errorMessage = "Ready.";

		if (chartData != null && chartData.length() > 0) {
			try {
				DataResponse dataResponse = DataStreamer
						.hexStringDeserializeToObject(chartData);

				if (dataResponse != null && dataResponse.isSuccess()) {
					AllDomainData allDomainData = dataResponse
							.getAllDomainData();

					dataCollectionList.add(new HistoryPainter(allDomainData
							.getHistoryDomainData()));
					dataCollectionList.add(new VolumePainter(allDomainData
							.getVolumeDomainData()));
					dataCollectionList.add(new RsiPainter(allDomainData
							.getRsiDomainData()));
					dataCollectionList.add(new OnBalanceVolumePainter(
							allDomainData.getOnBalanceVolumeDomainData()));
					dataCollectionList.add(new DmiPainter(allDomainData
							.getDmiDomainData()));
					dataCollectionList.add(new MacdPainter(allDomainData
							.getMacdDomainData()));
					dataCollectionList.add(new EmaPainter(allDomainData
							.getEmaDomainData()));
					dataCollectionList.add(new CmoPainter(allDomainData
							.getCmoDomainData()));
					dataCollectionList.add(new StdPainter(allDomainData
							.getStdDomainData()));
					dataCollectionList.add(new AccumDistLinePainter(
							allDomainData.getAccumDistLineDomainData()));
					dataCollectionList.add(new RSquaredPainter(allDomainData
							.getRSquaredDomainData()));
					dataCollectionList.add(new AroonPainter(allDomainData
							.getAroonDomainData()));
					dataCollectionList.add(new ChaikinOscillatorPainter(
							allDomainData.getChaikinOscillatorDomainData()));

					for (PainterBase dataCollection : dataCollectionList) {
						if (dataCollection instanceof HistoryPainter) {
							historyDataCollection = dataCollection;

							break;
						}
					}

					success = true;
					errorMessage = null;
				} else {
					success = false;

					if (dataResponse == null) {
						errorMessage = "Unable to reassemble data to object.";
					} else {
						errorMessage = dataResponse.getErrorMessage();
					}

					errorMessages.add(errorMessage);
				}
			} catch (Throwable e) {
				throw new RuntimeException("Exception retrieving data.", e);
			}
		}

		String dividendData = getParameter("dividendData");
		if (dividendData != null && dividendData.length() > 0) {
			try {
				dividends = DataStreamer
						.hexStringDeserializeToObject(dividendData);
			} catch (Throwable e) {
				/*
				 * dividends = new Vector<Dividend>();
				 * 
				 * Dividend dividend = new Dividend();
				 * dividend.setDate("2009-10-20"); dividend.setValue(99.99f);
				 * dividends.add(dividend);
				 */
			}
		}

		days = MbaUtils.MinDays * 4;
		deltaX = MaxDeltaX / 4;

		setPreferredSize(new Dimension(ChartConstants.PanelWidth + 2
				* ChartConstants.FrameSideEdge, ChartConstants.PanelHeight + 2
				* ChartConstants.FrameTopEdge));
		setBackground(backgroundColor);

		ChartMouseInputListener chartMouseInputListener = new ChartMouseInputListener();
		chartMouseInputListener.setChartJApplet(this);

		addMouseMotionListener(chartMouseInputListener);
		addMouseListener(chartMouseInputListener);
		addMouseWheelListener(chartMouseInputListener);
	}

	public void paint(Graphics g) {
		dateArray = MbaUtils.getDateArray(getDays(), getDeltaX());

		if (success) {
			if (MbaUtils.getDateArray(days, deltaX) == null) {
				return;
			}

			if (offScreenImg == null) {
				offScreenImg = createImage(getWidth(), getHeight());
				offScreenG = offScreenImg.getGraphics();
			}

			clearPanel(offScreenG);
			drawPanel(offScreenG);

			g.drawImage(offScreenImg, 0, 0, this);
		} else {
			drawErrorMessage(g);
			// drawMessages(g);
		}
	}

	public void drawCross(Graphics g, int y_H, int y_L) {
		g.setColor(Color.BLACK);
		((Graphics2D) g).setStroke(ChartConstants.STROKE1);

		int[] x = MbaUtils.getDateArray(days, deltaX);

		g.drawLine(x[xPosIndex], y_H, x[xPosIndex], y_L);

		if (historyDataCollection == null) {
			return;
		}

		if (mouseY < historyDataCollection.getY0Position()
				&& mouseY > historyDataCollection.getY0Position()
						- historyDataCollection.getHeight()) {
			g.drawLine(ChartConstants.X0Position, mouseY, x[x.length - 1],
					mouseY);
			String value = historyDataCollection.getYValueString(mouseY);

			g.drawString(value, x[x.length - 1] + 5, mouseY);
		}
	}

	private void clearPanel(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void drawPanel(Graphics g) {
		for (PainterBase dataCollection : dataCollectionList) {
			dataCollection.drawValue(g, xPosIndex);
			dataCollection.paint(offScreenG);
		}

		for (TrendLine tl : trendLineVector) {
			tl.drawLine(offScreenG);
		}

		if (mouseDragged) {
			TrendLine.drawLine(offScreenG, trendLineXA, trendLineYA,
					trendLineXZ, trendLineYZ);
		}
	}

	public void mouseMoved(int posIndex) {
		xPosIndex = posIndex;

		repaint();
	}

	public int getxPosIndex() {
		return xPosIndex;
	}

	public void setxPosIndex(int xPosIndex) {
		this.xPosIndex = xPosIndex;
	}

	public Vector<TrendLine> getTrendLineVector() {
		return trendLineVector;
	}

	public void setHistoryDomainData(HistoryDomainData historyDomainData) {
		this.historyDomainData = historyDomainData;
	}

	public HistoryDomainData getHistoryDomainData() {
		return historyDomainData;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

	public boolean isMouseDragged() {
		return mouseDragged;
	}

	public void setMouseDragged(boolean mouseDragged) {
		this.mouseDragged = mouseDragged;
	}

	public int getTrendLineXA() {
		return trendLineXA;
	}

	public void setTrendLineXA(int trendLineXA) {
		this.trendLineXA = trendLineXA;
	}

	public int getTrendLineYA() {
		return trendLineYA;
	}

	public void setTrendLineYA(int trendLineYA) {
		this.trendLineYA = trendLineYA;
	}

	public int getTrendLineXZ() {
		return trendLineXZ;
	}

	public void setTrendLineXZ(int trendLineXZ) {
		this.trendLineXZ = trendLineXZ;
	}

	public int getTrendLineYZ() {
		return trendLineYZ;
	}

	public void setTrendLineYZ(int trendLineYZ) {
		this.trendLineYZ = trendLineYZ;
	}

	public void drawErrorMessage(Graphics g) {
		drawMessage(g, errorMessage);
	}

	public void drawMessage(Graphics g, String message) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.RED);
		Font font = new Font("Arial", Font.BOLD, 18);
		g.setFont(font);
		g.drawString(message, 20, 100);
	}

	public void drawMessages(Graphics g) {
		if (errorMessages != null) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());

			g.setColor(Color.RED);
			Font font = new Font("Arial", Font.BOLD, 18);
			g.setFont(font);
			int yPos = 100;
			for (String msg : errorMessages) {
				g.drawString(msg, 20, yPos);
				yPos += 20;
			}
		}
	}

	public void setDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getDays() {
		return days;
	}

	public int[] getDateArray() {
		return dateArray;
	}

	public static void setDividends(Vector<Dividend> dividends) {
		ChartJApplet.dividends = dividends;
	}

	public static Vector<Dividend> getDividends() {
		return dividends;
	}

}
