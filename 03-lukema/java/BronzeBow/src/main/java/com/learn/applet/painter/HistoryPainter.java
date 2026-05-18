package com.learn.applet.painter;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Vector;

import com.learn.applet.ChartJApplet;
import com.learn.common.domain.Dividend;
import com.learn.common.domain.HistoryDomainData;
import com.learn.common.util.MbaUtils;


public class HistoryPainter
   extends PainterBase
{
   private HistoryDomainData                 historyDomainData;

   private String[]            date;

   private float[]             openRaw;

   private float[]             highRaw;

   private float[]             lowRaw;

   private float[]             closeRaw;

   private final static String DataOutOfDateWarn = "Warning: Data for the latest days were missing";

   public HistoryPainter(HistoryDomainData historyDomainData)
   {
      super(historyDomainData);

      this.historyDomainData = historyDomainData;

      setDomainData(historyDomainData);
      getChartJApplet().setHistoryDomainData(historyDomainData);

      date = historyDomainData.getDate();
      openRaw = historyDomainData.getOpenRaw();
      highRaw = historyDomainData.getHighRaw();
      lowRaw = historyDomainData.getLowRaw();
      closeRaw = historyDomainData.getCloseRaw();

      ChartJApplet.RawDataSize = date.length;
   }

   private boolean isFarZoom()
   {
      return getChartJApplet().getDays() >= MbaUtils.MaxDays * 4;
   }

   public void descMe()
   {
      String desc = "Bollinger-cyan. EMA: ";

      if (isFarZoom())
      {
         desc += "25d-blue 50d-red 15d-grey";
      }
      else
      {
         desc += "5d-blue 10d-red 15d-grey";
      }

      setDesc(desc);
   }

   public String getValueString(int index)
   {
      if (date != null)
      {
         index += getSkipDays();
         return "Date=" + date[index] + " Open=" + MbaUtils.DecimalFormat.format(openRaw[index]) + " High=" + MbaUtils.DecimalFormat.format(highRaw[index])
               + " Low=" + MbaUtils.DecimalFormat.format(lowRaw[index]) + " Close=" + MbaUtils.DecimalFormat.format(closeRaw[index]);
      }
      else
      {
         return null;
      }
   }

   public void paint(Graphics g)
   {
      drawGrid(g);
      drawCross(g);
      drawDividends(g);
      drawBollinger(g);
      drawMomentum(g);
      drawEMA(g);
      drawOHLC(g);
      drawMomentumWarning(g);
      drawDesc(g);

      if (highRaw[highRaw.length - 1] == lowRaw[lowRaw.length - 1] && openRaw[openRaw.length - 1] == lowRaw[lowRaw.length - 1]
            && openRaw[openRaw.length - 1] == closeRaw[closeRaw.length - 1])
      {
         drawWarningMessage(g);
      }
   }

   private void drawDividends(Graphics g)
   {
      Vector<Dividend> dividends = ChartJApplet.getDividends();
      if (dividends == null || dividends.size() == 0)
      {
         return;
      }

      int[] x = getDateArray();
      int skipDays = getSkipDays();
      int[] low = MbaUtils.toArray(historyDomainData.getLow(), skipDays);

      for (int i = 0; i < x.length; i++)
      {
         String day = date[i + skipDays];

         for (Dividend dividend : dividends)
         {
            if (dividend.getDate().equals(day))
            {
               g.setColor(Color.BLUE);

               if (getY0Position() - low[i] < 80)
               {
                  g.drawString("D:" + dividend.getValue(), x[i] + 2, (getY0Position() - getHeight() + 20));
               }
               else
               {
                  g.drawString("D:" + dividend.getValue(), x[i] + 2, getY0Position() - 20);
               }

               g.drawLine(x[i], getY0Position(), x[i], (getY0Position() - getHeight()));
            }
         }
      }
   }

   private void drawWarningMessage(Graphics g)
   {
      g.setColor(Color.RED);
      Font font = new Font("Arial", Font.BOLD, 18);
      g.setFont(font);

      g.drawString(DataOutOfDateWarn, 400, 60);
      g.drawString(DataOutOfDateWarn, 400, 130);
      g.drawString(DataOutOfDateWarn, 400, 200);
   }

   private void drawOHLC(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] open = MbaUtils.toArray(historyDomainData.getOpen(), skipDays);
      int[] high = MbaUtils.toArray(historyDomainData.getHigh(), skipDays);
      int[] low = MbaUtils.toArray(historyDomainData.getLow(), skipDays);
      int[] close = MbaUtils.toArray(historyDomainData.getClose(), skipDays);

      int lineWidth = 2;
      int openCloseLength = 3;

      if (!isFarZoom())
      {
         lineWidth = 2;
         openCloseLength = 3;
      }
      else
      {
         lineWidth = 1;
         openCloseLength = 2;
      }

      int[] x = getDateArray();
      Stroke s = new BasicStroke(lineWidth);

      for (int i = 0; i < open.length; i++)
      {
         Color c = null;

         // these open/close are after adjustment for drawing. 
         // if open > close, the actual values before adjust 
         // for draw is open < close.
         if (open[i] > close[i])
         {
            c = Color.BLUE;
         }
         else
         {
            c = Color.RED;
         }

         g.setColor(c);

         ((Graphics2D) g).setStroke(s);

         g.drawLine(x[i], low[i], x[i], high[i]);
         g.drawLine(x[i] - openCloseLength, open[i], x[i], open[i]);
         g.drawLine(x[i] + openCloseLength, close[i], x[i], close[i]);
      }
   }

   private void drawBollinger(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] bollingerUpper = MbaUtils.toArray(historyDomainData.getBollingerUpper(), skipDays);
      int[] bollingerLower = MbaUtils.toArray(historyDomainData.getBollingerLower(), skipDays);
      
      Color c = Color.CYAN;
      drawLine(g, bollingerUpper, c, 1);
      drawLine(g, bollingerLower, c, 1);
   }

   private void drawEMA(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] ema05 = MbaUtils.toArray(historyDomainData.getEma05(), skipDays);
      int[] ema10 = MbaUtils.toArray(historyDomainData.getEma10(), skipDays);
      int[] ema15 = MbaUtils.toArray(historyDomainData.getEma15(), skipDays);
      int[] ema25 = MbaUtils.toArray(historyDomainData.getEma25(), skipDays);
      int[] ema50 = MbaUtils.toArray(historyDomainData.getEma50(), skipDays);

      float dash05[] = { 5.0f, 2.0f };
      float dash10[] = { 10.0f, 5.0f };
      float dash15[] = { 15.0f, 7.0f };
      float dash25[] = { 25.0f, 12.0f };
      float dash50[] = { 50.0f, 25.0f };

      float lineWidth = 3.0f;
      if (isFarZoom())
      {
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

      if (!isFarZoom())
      {
         ((Graphics2D) g).setStroke(dashed05);
         drawDashLine(g, ema05, c05);

         ((Graphics2D) g).setStroke(dashed10);
         drawDashLine(g, ema10, c10);
      }

      ((Graphics2D) g).setStroke(dashed15);
      drawDashLine(g, ema15, c15);

      if (isFarZoom())
      {
         ((Graphics2D) g).setStroke(dashed25);
         drawDashLine(g, ema25, c25);

         ((Graphics2D) g).setStroke(dashed50);
         drawDashLine(g, ema50, c50);
      }
   }

   private void drawMomentum(Graphics g)
   {
      drawMomentumTBP(g);
      drawMomentumTarget(g);
      drawMomentumStop(g);
      drawMomentumTomorrow(g);
   }

   private void drawMomentumTBP(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] highTBP = MbaUtils.toArray(historyDomainData.getHighTBP(), skipDays);
      int[] lowTBP = MbaUtils.toArray(historyDomainData.getLowTBP(), skipDays);

      Color c = Color.WHITE;
      drawLine(g, highTBP, c, 2);
      drawLine(g, lowTBP, c, 2);
   }

   private void drawMomentumWarning(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] high = MbaUtils.toArray(historyDomainData.getHigh(), skipDays);
      int[] low = MbaUtils.toArray(historyDomainData.getLow(), skipDays);
      int[] highTBP = MbaUtils.toArray(historyDomainData.getHighTBP(), skipDays);
      int[] lowTBP = MbaUtils.toArray(historyDomainData.getLowTBP(), skipDays);
      int[] highTarget = MbaUtils.toArray(historyDomainData.getHighTarget(), skipDays);
      int[] lowTarget = MbaUtils.toArray(historyDomainData.getLowTarget(), skipDays);
      int[] x = getDateArray();

      Stroke s = null;
      int diameter = 0;
      if (isFarZoom())
      {
         diameter = 3;
         s = new BasicStroke(2);
      }
      else
      {
         diameter = 5;
         s = new BasicStroke(3);
      }

      ((Graphics2D) g).setStroke(s);
      for (int i = 0; i < x.length; i++)
      {
         // if(highTBP[i] > Math.max(lowTarget[i], low[i]))
         if (highTBP[i] > low[i])
         {
            g.setColor(Color.BLUE);
            if (highTBP[i] > lowTarget[i])
            {
               g.fillOval(x[i] - diameter, highTBP[i] - diameter, 2 * diameter, 2 * diameter);
            }
            else
            {
               g.drawOval(x[i] - diameter, highTBP[i] - diameter, 2 * diameter, 2 * diameter);
            }
         }
         // else if(lowTBP[i] < Math.min(highTarget[i], high[i]))
         else if (lowTBP[i] < high[i])
         {
            g.setColor(Color.RED);
            if (lowTBP[i] < highTarget[i])
            {
               g.fillOval(x[i] - diameter, lowTBP[i] - diameter, 2 * diameter, 2 * diameter);
            }
            else
            {
               g.drawOval(x[i] - diameter, lowTBP[i] - diameter, 2 * diameter, 2 * diameter);
            }
         }
      }
   }

   private void drawMomentumTarget(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] highTarget = MbaUtils.toArray(historyDomainData.getHighTarget(), skipDays);
      int[] lowTarget = MbaUtils.toArray(historyDomainData.getLowTarget(), skipDays);

      Color c = Color.YELLOW;
      drawLine(g, highTarget, c, 3);
      drawLine(g, lowTarget, c, 3);

      c = Color.MAGENTA;
      drawLine(g, highTarget, c, 1);
      drawLine(g, lowTarget, c, 1);
   }

   private void drawMomentumStop(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] highStop = MbaUtils.toArray(historyDomainData.getHighStop(), skipDays);
      int[] lowStop = MbaUtils.toArray(historyDomainData.getLowStop(), skipDays);

      Color c = Color.MAGENTA;
      drawLine(g, highStop, c, 1);
      drawLine(g, lowStop, c, 1);
   }

   private void drawMomentumTomorrow(Graphics g)
   {
      int[] tomorrowHighTarget = new int[2];
      int[] tomorrowLowTarget = new int[2];
      int[] tomorrowHighStop = new int[2];
      int[] tomorrowLowStop = new int[2];

      int skipDays = getSkipDays();

      int[] tomHighTarget = MbaUtils.toArray(historyDomainData.getTomorrowHighTarget(), skipDays);
      int[] tomLowTarget = MbaUtils.toArray(historyDomainData.getTomorrowLowTarget(), skipDays);
      int[] tomHighStop = MbaUtils.toArray(historyDomainData.getTomorrowHighStop(), skipDays);
      int[] tomLowStop = MbaUtils.toArray(historyDomainData.getTomorrowLowStop(), skipDays);

      for (int i = 0; i < tomorrowHighTarget.length; i++)
      {
         tomorrowHighTarget[i] = tomHighTarget[tomHighTarget.length - 2 + i];
         tomorrowLowTarget[i] = tomLowTarget[tomHighTarget.length - 2 + i];
         tomorrowHighStop[i] = tomHighStop[tomHighStop.length - 2 + i];
         tomorrowLowStop[i] = tomLowStop[tomHighStop.length - 2 + i];
      }

      int[] tomX = new int[2];
      tomX[0] = getDateArray()[getDateArray().length - 1];
      tomX[1] = tomX[0] + getChartJApplet().getDeltaX();

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

   @Override
   protected int getSkipDays()
   {
      return openRaw.length - getDays();
   }
}
