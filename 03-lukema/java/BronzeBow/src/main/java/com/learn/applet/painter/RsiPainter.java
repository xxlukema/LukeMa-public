package com.learn.applet.painter;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.learn.common.domain.RsiDomainData;
import com.learn.common.util.ChartConstants;
import com.learn.common.util.MbaUtils;


public class RsiPainter
   extends PainterBase
{
   private RsiDomainData rsiDomainData;

   private float[]       rsiRaw;

   private float[]       williamsRaw;

   public RsiPainter(RsiDomainData rsiDomainData)
   {
      super(rsiDomainData);

      this.rsiDomainData = rsiDomainData;

      setDomainData(rsiDomainData);
      rsiRaw = rsiDomainData.getRsiRaw();
      williamsRaw = rsiDomainData.getWilliamsRaw();
   }

   public void descMe()
   {
      // setDesc("RSI_Williams: RSI-red Williams%R-blue");

      setDesc("RSI: Slow-red Fast-blue");
   }

   public String getValueString(int index)
   {
      if (rsiRaw != null)
      {
         // return "RSI="+Utility.float2str(rsi[index], 0)+"   100+Williams%R="+Utility.float2str(williams[index], 0);

         return "Fast=" + MbaUtils.DecimalFormat.format(williamsRaw[index]) + "   Slow=" + MbaUtils.DecimalFormat.format(rsiRaw[index]);
      }
      else
      {
         return null;
      }
   }

   public void paint(Graphics g)
   {
      drawGrid(g);
      drawAllGrid(g);
      drawCross(g);
      drawRSI(g);
      drawWilliamsR(g);
      drawDesc(g);
   }

   private void drawAllGrid(Graphics g)
   {
      int[] x = getDateArray();

      if (x != null)
      {
         int dy = -getHeight() / 5;

         ((Graphics2D) g).setStroke(ChartConstants.STROKE1);

         for (int i = 1; i < 5; i++)
         {
            int yPos = getY0Position() + i * dy;
            if (i == 1 || i == 4)
            {
               g.setColor(Color.RED);
            }
            else
            {
               g.setColor(Color.CYAN);
            }
            g.drawLine(x[0], yPos, x[x.length - 1], yPos);
            if (i == 1 || i == 4)
            {
               g.setColor(Color.RED);
            }
            else
            {
               g.setColor(Color.BLACK);
            }
            g.drawString("" + 20 * i, x[x.length - 1] + 5, yPos + 5);
         }
      }
   }

   private void drawRSI(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] rsi = MbaUtils.toArray(rsiDomainData.getRsi(), skipDays);

      drawLine(g, rsi, Color.RED, 2);
   }

   private void drawWilliamsR(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] williams = MbaUtils.toArray(rsiDomainData.getWilliams(), skipDays);

      drawLine(g, williams, Color.BLUE, 2);
   }

   @Override
   protected int getSkipDays()
   {
      return rsiRaw.length - getDays();
   }
}
