package com.learn.applet.painter;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.learn.common.domain.RSquaredDomainData;
import com.learn.common.util.ChartConstants;
import com.learn.common.util.MbaUtils;


public class RSquaredPainter
   extends PainterBase
{
   private RSquaredDomainData rSquaredDomainData;

   private float[]            slopeRaw;

   private float[]            rsquaredRaw;

   private float              min;

   private float              max;

   private float              slopeFactor = 0;

   public RSquaredPainter(RSquaredDomainData rSquaredDomainData)
   {
      super(rSquaredDomainData);

      this.rSquaredDomainData = rSquaredDomainData;

      setDomainData(rSquaredDomainData);
      slopeRaw = rSquaredDomainData.getSlopeRaw();
      rsquaredRaw = rSquaredDomainData.getRsquaredRaw();

      setMax();
   }

   private void setMax()
   {
      min = Integer.MAX_VALUE;
      max = Integer.MIN_VALUE;

      float slopeMin = Integer.MAX_VALUE;
      float slopeMax = Integer.MIN_VALUE;

      // Find zero point
      for (int i = 0; i < slopeRaw.length; i++)
      {
         min = Math.min(min, slopeRaw[i]);
         min = Math.min(min, rsquaredRaw[i]);

         max = Math.max(max, slopeRaw[i]);
         max = Math.max(max, rsquaredRaw[i]);

         slopeMin = Math.min(min, slopeRaw[i]);
         slopeMax = Math.max(max, slopeRaw[i]);
      }

      slopeFactor = Math.max(Math.abs(slopeMin), slopeMax);

   }

   public void descMe()
   {
      setDesc("RSquared: Regrsn Slope-black, RSquared-blue");
   }

   public String getValueString(int index)
   {
      if (slopeRaw != null)
      {
         String ret = "";
         if (slopeFactor > 1)
         {
            ret += "Actual ";
         }

         ret += "Slope=" + MbaUtils.DecimalFormat.format(slopeRaw[index]);
         ret += " R Squared=" + MbaUtils.DecimalFormat.format(rsquaredRaw[index]);

         return ret;
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
      drawRSquared(g);
      drawDesc(g);
   }

   private void drawRSquared(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] slope = MbaUtils.toArray(rSquaredDomainData.getSlope(), skipDays);
      int[] rsquared = MbaUtils.toArray(rSquaredDomainData.getRsquared(), skipDays);

      Color slopColor = Color.BLACK;
      if (slopeFactor > 1)
      {
         slopColor = Color.RED;
      }
      drawLine(g, slope, slopColor, 2);
      drawLine(g, rsquared, Color.BLUE, 2);
   }

   private void drawAllGrid(Graphics g)
   {
      int[] x = getDateArray();

      if (x != null)
      {
         ((Graphics2D) g).setStroke(ChartConstants.STROKE1);

         g.setColor(Color.RED);
         float dy = (float) (max / (max - min)) * getHeight();
         int yPos = (getY0Position() - getHeight()) + (int) dy;
         g.drawLine(x[0], yPos, x[x.length - 1], yPos);
         g.drawString("0", x[x.length - 1] + 5, yPos + 5);

         // 95% confidence/coverage line (0.77)
         g.setColor(Color.GREEN);
         dy = (float) (max * (1.0 - 0.77) / (max - min)) * getHeight();
         yPos = (getY0Position() - getHeight()) + (int) dy;
         g.drawLine(x[0], yPos, x[x.length - 1], yPos);
         g.drawString("0.77(95%)", x[x.length - 1] + 5, yPos + 5);
      }
   }

   @Override
   protected int getSkipDays()
   {
      return slopeRaw.length - getDays();
   }
}
