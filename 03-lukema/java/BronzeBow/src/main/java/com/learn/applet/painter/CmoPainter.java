package com.learn.applet.painter;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.learn.common.domain.CmoDomainData;
import com.learn.common.util.ChartConstants;
import com.learn.common.util.MbaUtils;


public class CmoPainter
   extends PainterBase
{
   private CmoDomainData cmoDomainData;

   private float[]       cmo05Raw;

   private float[]       cmo10Raw;

   public CmoPainter(CmoDomainData cmoDomainData)
   {
      super(cmoDomainData);

      this.cmoDomainData = cmoDomainData;

      setDomainData(cmoDomainData);
      cmo05Raw = cmoDomainData.getCmo05Raw();
      cmo10Raw = cmoDomainData.getCmo10Raw();
   }

   public void descMe()
   {
      setDesc("CMO: 5-blue 10-red");
   }

   public String getValueString(int index)
   {
      if (cmo05Raw != null)
      {
         return "CMO05=" + MbaUtils.LongFormat.format(cmo05Raw[index]) + "   CMO10=" + MbaUtils.LongFormat.format(cmo10Raw[index]);
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
      drawCMO10(g);
      drawCMO5(g);
      drawDesc(g);
   }

   private void drawAllGrid(Graphics g)
   {
      int[] x = getDateArray();

      if (x != null)
      {
         ((Graphics2D) g).setStroke(ChartConstants.STROKE1);

         g.setColor(Color.RED);
         int dy = -getHeight() / 4;
         int yPos = getY0Position() + dy;
         g.drawLine(x[0], yPos, x[x.length - 1], yPos);
         g.drawString("-50", x[x.length - 1] + 5, yPos + 5);
         yPos = (getY0Position() - getHeight()) - dy;
         g.drawLine(x[0], yPos, x[x.length - 1], yPos);
         g.drawString("50", x[x.length - 1] + 5, yPos + 5);

         g.setColor(Color.BLACK);
         dy = -getHeight() / 2;
         yPos = (getY0Position() - getHeight()) - dy;
         g.drawLine(x[0], yPos, x[x.length - 1], yPos);
         g.drawString("0", x[x.length - 1] + 5, yPos + 5);
      }
   }

   private void drawCMO5(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] cmo05 = MbaUtils.toArray(cmoDomainData.getCmo05(), skipDays);

      drawLine(g, cmo05, Color.BLUE, 2);
   }

   private void drawCMO10(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] cmo10 = MbaUtils.toArray(cmoDomainData.getCmo10(), skipDays);

      drawLine(g, cmo10, Color.RED, 2);
   }

   @Override
   protected int getSkipDays()
   {
      return cmo05Raw.length - getDays();
   }
}
