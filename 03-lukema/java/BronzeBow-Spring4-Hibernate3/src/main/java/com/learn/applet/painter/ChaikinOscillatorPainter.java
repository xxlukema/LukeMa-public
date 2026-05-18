package com.learn.applet.painter;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.learn.common.domain.ChaikinOscillatorDomainData;
import com.learn.common.util.ChartConstants;
import com.learn.common.util.MbaUtils;


public class ChaikinOscillatorPainter
   extends PainterBase
{
   private ChaikinOscillatorDomainData chaikinOscillatorDomainData;

   private float[]                     dimensionlessRaw;

   public ChaikinOscillatorPainter(ChaikinOscillatorDomainData chaikinOscillatorDomainData)
   {
      super(chaikinOscillatorDomainData);

      this.chaikinOscillatorDomainData = chaikinOscillatorDomainData;

      setDomainData(chaikinOscillatorDomainData);
      dimensionlessRaw = chaikinOscillatorDomainData.getDimensionlessRaw();
   }

   public void descMe()
   {
      setDesc("ChaikinOscillator: Dimensionless Chaikin black");
   }

   public String getValueString(int index)
   {
      if (dimensionlessRaw != null)
      {
         return "Dimensionless Chaikin=" + MbaUtils.DecimalFormat.format(dimensionlessRaw[index]);
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
      drawChaikinOscillator(g);
      drawDesc(g);
   }

   private void drawAllGrid(Graphics g)
   {
      int[] x = getDateArray();

      if (x != null)
      {
         ((Graphics2D) g).setStroke(ChartConstants.STROKE1);

         g.setColor(Color.RED);
         int dy = -getHeight() / 2;
         int yPos = getY0Position() + dy;
         g.drawLine(x[0], yPos, x[x.length - 1], yPos);
         g.drawString("0", x[x.length - 1] + 5, yPos + 5);

         dy = (int) (-getHeight() * 0.15);

         yPos = (getY0Position() - getHeight()) - dy;
         g.drawLine(x[0], yPos, x[x.length - 1], yPos);
         g.drawString("0.85", x[x.length - 1] + 33, yPos + 5);

         yPos = getY0Position() + dy;
         g.drawLine(x[0], yPos, x[x.length - 1], yPos);
         g.drawString("-0.85", x[x.length - 1] + 33, yPos + 5);
      }
   }

   private void drawChaikinOscillator(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] dimensionless = MbaUtils.toArray(chaikinOscillatorDomainData.getDimensionless(), skipDays);

      drawLine(g, dimensionless, Color.BLACK, 2);
   }

   @Override
   protected int getSkipDays()
   {
      return dimensionlessRaw.length - getDays();
   }
}
