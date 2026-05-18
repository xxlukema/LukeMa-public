package com.learn.applet.painter;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.learn.common.domain.MacdDomainData;
import com.learn.common.util.ChartConstants;
import com.learn.common.util.MbaUtils;


public class MacdPainter
   extends PainterBase
{
   private MacdDomainData macdDomainData;

   private float[]        macd_12_26Raw;

   public MacdPainter(MacdDomainData macdDomainData)
   {
      super(macdDomainData);

      this.macdDomainData = macdDomainData;

      setDomainData(macdDomainData);
      macd_12_26Raw = macdDomainData.getMacd_12_26Raw();
   }

   public void descMe()
   {
      setDesc("MACD: Fast-Slow blue, Reverse yellow");
   }

   private void drawAllGrid(Graphics g)
   {
      int[] x = getDateArray();

      float maxMACD_12_26 = Integer.MIN_VALUE;
      float minMACD_12_26 = Integer.MAX_VALUE;

      if (x != null)
      {
         int yPos = getY0Position() + (int) (minMACD_12_26 / (maxMACD_12_26 - minMACD_12_26) * getHeight());

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
      if (macd_12_26Raw != null)
      {
         return "Divergence = " + MbaUtils.DecimalFormat.format(macd_12_26Raw[index]);
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
      drawMACD(g);
      drawDesc(g);
   }

   private void drawMACD(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] macd_12_26 = MbaUtils.toArray(macdDomainData.getMacd_12_26(), skipDays);
      int[] inc = MbaUtils.toArray(macdDomainData.getInc(), skipDays);

      drawLine(g, inc, Color.YELLOW, 2);
      drawAllGrid(g);
      drawLine(g, macd_12_26, Color.BLUE, 2);
   }

   @Override
   protected int getSkipDays()
   {
      return macd_12_26Raw.length - getDays();
   }
}
