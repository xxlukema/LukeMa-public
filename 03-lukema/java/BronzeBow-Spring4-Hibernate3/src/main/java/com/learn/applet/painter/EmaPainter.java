package com.learn.applet.painter;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.learn.common.domain.EmaDomainData;
import com.learn.common.util.ChartConstants;
import com.learn.common.util.MbaUtils;


public class EmaPainter
   extends PainterBase
{
   private EmaDomainData emaDomainData;

   private float[]       ema_5_10Raw;

   private float[]       incRaw;

   public EmaPainter(EmaDomainData emaDomainData)
   {
      super(emaDomainData);

      this.emaDomainData = emaDomainData;

      setDomainData(emaDomainData);
      ema_5_10Raw = emaDomainData.getEma_5_10Raw();
      incRaw = emaDomainData.getIncRaw();
   }

   public void descMe()
   {
      setDesc("EMA: 5day-10day blue, Reverse yellow");
   }

   private void drawAllGrid(Graphics g)
   {
      float maxEMA_5_10 = Integer.MIN_VALUE;
      float minEMA_5_10 = Integer.MAX_VALUE;

      for (float ema : ema_5_10Raw)
      {
         maxEMA_5_10 = Math.max(maxEMA_5_10, ema);
         minEMA_5_10 = Math.min(minEMA_5_10, ema);
      }

      int[] x = getDateArray();

      if (x != null)
      {
         int yPos = getY0Position() + (int) (minEMA_5_10 / (maxEMA_5_10 - minEMA_5_10) * getHeight());

         ((Graphics2D) g).setStroke(ChartConstants.STROKE4);
         g.setColor(Color.LIGHT_GRAY);
         g.drawLine(x[0], yPos, x[x.length - 1], yPos);

         ((Graphics2D) g).setStroke(ChartConstants.STROKE1);
         g.setColor(Color.BLACK);
         g.drawLine(x[0], yPos, x[x.length - 1], yPos);
         g.drawString("0", x[x.length - 1] + 5, yPos + 5);
      }
   }

   public String getValueString(int index)
   {
      if (ema_5_10Raw != null)
      {
         return "5day - 10day = " + MbaUtils.DecimalFormat.format(ema_5_10Raw[index]) + " reverse increment = " + MbaUtils.DecimalFormat.format(incRaw[index]);
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
      drawEMA(g);
      drawDesc(g);
   }

   private void drawEMA(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] ema_5_10 = MbaUtils.toArray(emaDomainData.getEma_5_10(), skipDays);
      int[] inc = MbaUtils.toArray(emaDomainData.getInc(), skipDays);

      drawLine(g, inc, Color.YELLOW, 2);
      drawAllGrid(g);
      drawLine(g, ema_5_10, Color.BLUE, 2);
   }

   @Override
   protected int getSkipDays()
   {
      return ema_5_10Raw.length - getDays();
   }
}
