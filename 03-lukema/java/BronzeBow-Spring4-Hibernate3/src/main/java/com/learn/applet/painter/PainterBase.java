package com.learn.applet.painter;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.learn.applet.ChartJApplet;
import com.learn.common.domain.DomainDataBase;
import com.learn.common.util.ChartConstants;
import com.learn.common.util.MbaUtils;


abstract public class PainterBase
{
   private DomainDataBase      domainData;

   private String              desc;

   private static ChartJApplet chartJApplet;

   public static ChartJApplet getChartJApplet()
   {
      return chartJApplet;
   }

   public static void setChartJApplet(ChartJApplet chartJApplet)
   {
      PainterBase.chartJApplet = chartJApplet;
   }

   public PainterBase(DomainDataBase domainData)
   {
      this.domainData = domainData;

      descMe();
   }

   abstract public void paint(Graphics g);

   abstract protected int getSkipDays();

   abstract public void descMe();

   abstract public String getValueString(int index);

   public void drawCross(Graphics g)
   {
      chartJApplet.drawCross(g, (getY0Position() - getHeight()), getY0Position());
   }

   public String getYValueString(int y)
   {
      float minY = domainData.getMin();
      float maxY = domainData.getMax();

      float value = (float) (maxY - (maxY - minY) * (y - (getY0Position() - getHeight())) / getHeight());

      return MbaUtils.DecimalFormat.format(value);
   }

   public void drawValue(Graphics g, int xPosIndex)
   {
      String valueString = getValueString(xPosIndex);
      if (valueString != null)
      {
         int x = chartJApplet.getDateArray()[0] + 280;
         int y = (getY0Position() - getHeight()) - 3;

         g.setColor(Color.LIGHT_GRAY);

         g.setColor(Color.RED);
         g.drawString(valueString, x, y);
      }
   }

   public void setDesc(String desc)
   {
      this.desc = desc;
   }

   public int getY0Position()
   {
      return domainData.getY0Position();
   }

   public void drawGrid(Graphics g)
   {
      // draw half cycle lines as thicker lines
      g.setColor(Color.CYAN);
      Stroke s = new BasicStroke(2);
      ((Graphics2D) g).setStroke(s);

      int[] dateArray = chartJApplet.getDateArray();

      for (int i = dateArray.length - 1; i >= 0; i -= ChartConstants.HALF_CYCLE)
      {
         g.drawLine(dateArray[i], getY0Position(), dateArray[i], (getY0Position() - getHeight()));
      }

      s = new BasicStroke(1);
      ((Graphics2D) g).setStroke(s);

      for (int i = 0; i < dateArray.length; i++)
      {
         g.drawLine(dateArray[i], getY0Position(), dateArray[i], (getY0Position() - getHeight()));
      }

      g.drawLine(dateArray[0], getY0Position(), dateArray[dateArray.length - 1], getY0Position());
      g.drawLine(dateArray[0], (getY0Position() - getHeight()), dateArray[dateArray.length - 1], (getY0Position() - getHeight()));

      drawQuarterLine(g, 63);
      drawQuarterLine(g, 126);
      drawQuarterLine(g, 189);
      drawQuarterLine(g, 252);
   }

   public void drawQuarterLine(Graphics g, int quarterDaysBack)
   {
      int[] dateArray = chartJApplet.getDateArray();

      int quarterDaysBackIndex = dateArray.length - quarterDaysBack;
      if (quarterDaysBackIndex >= 0)
      {
         g.setColor(Color.ORANGE);
         Stroke s = new BasicStroke(2);
         ((Graphics2D) g).setStroke(s);
         g.drawLine(dateArray[quarterDaysBackIndex], getY0Position(), dateArray[quarterDaysBackIndex], (getY0Position() - getHeight()));
      }
   }

   public void drawDesc(Graphics g)
   {
      if (desc != null)
      {
         int[] dateArray = chartJApplet.getDateArray();

         g.setColor(Color.BLACK);
         g.drawString(desc, dateArray[0] + 10, (getY0Position() - getHeight()) - 3);

         int pos1 = desc.indexOf(' ');
         int pos2 = desc.indexOf(':');
         int pos = 0;
         if (pos2 > -1 && pos1 > -1)
         {
            pos = Math.min(pos1, pos2);
         }
         else
         {
            pos = Math.max(pos1, pos2);
         }
         String shortDesc;
         if (pos > -1)
         {
            shortDesc = desc.substring(0, pos);
         }
         else
         {
            shortDesc = desc;
         }

         if (this instanceof HistoryPainter)
         {
            shortDesc = chartJApplet.getSymbol();
         }

         g.drawString(shortDesc, dateArray[dateArray.length - 1] - 80, (getY0Position() - getHeight()) - 3);
      }

      drawMinMax(g);
   }

   final public boolean isKUint()
   {
      return domainData.isKUnit();
   }

   public void drawMinMax(Graphics g)
   {
      g.setColor(Color.BLACK);

      float min = domainData.getMin();
      float max = domainData.getMax();

      int[] dateArray = chartJApplet.getDateArray();

      if (isKUint())
      {
         g.drawString(MbaUtils.LongFormat.format(max) + "K", dateArray[dateArray.length - 1] + 5, (getY0Position() - getHeight()) + 10);
         g.drawString(MbaUtils.LongFormat.format(min) + "K", dateArray[dateArray.length - 1] + 5, getY0Position());
      }
      else
      {
         g.drawString(MbaUtils.DecimalFormat.format(max), dateArray[dateArray.length - 1] + 5, (getY0Position() - getHeight()) + 10);
         g.drawString(MbaUtils.DecimalFormat.format(min), dateArray[dateArray.length - 1] + 5, getY0Position());
      }
   }

   public void drawLine(Graphics g, int[] y, Color c)
   {
      drawLine(g, y, c, 1);
   }

   public void drawLine(Graphics g, int[] y, Color c, int lineWidth)
   {
      Stroke s = new BasicStroke(lineWidth);
      ((Graphics2D) g).setStroke(s);
      if (c == null)
      {
         c = Color.BLACK;
      }
      g.setColor(c);

      int[] dateArray = chartJApplet.getDateArray();
      g.drawPolyline(dateArray, y, dateArray.length);
   }

   public void drawDashLine(Graphics g, int[] y, Color c)
   {
      if (c == null)
      {
         c = Color.BLACK;
      }
      g.setColor(c);

      int[] dateArray = chartJApplet.getDateArray();
      g.drawPolyline(dateArray, y, dateArray.length);
   }

   public int getHeight()
   {
      return domainData.getHeight();
   }

   public DomainDataBase getDomainData()
   {
      return domainData;
   }

   protected void setDomainData(DomainDataBase domainData)
   {
      this.domainData = domainData;
   }

   protected int getDays()
   {
      return chartJApplet.getDays();
   }

   public int[] getDateArray()
   {
      return chartJApplet.getDateArray();
   }
}
