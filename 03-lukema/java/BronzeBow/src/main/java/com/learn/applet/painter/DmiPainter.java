package com.learn.applet.painter;


import java.awt.Color;
import java.awt.Graphics;

import com.learn.common.domain.DmiDomainData;
import com.learn.common.util.MbaUtils;


public class DmiPainter
   extends PainterBase
{
   private DmiDomainData dmiDomainData;

   private float[]       diPlusRaw;

   private float[]       diMinusRaw;

   private float[]       adxRaw;

   public DmiPainter(DmiDomainData dmiDomainData)
   {
      super(dmiDomainData);

      this.dmiDomainData = dmiDomainData;

      setDomainData(dmiDomainData);
      diPlusRaw = dmiDomainData.getDiPlusRaw();
      diMinusRaw = dmiDomainData.getDiMinusRaw();
      adxRaw = dmiDomainData.getAdxRaw();
   }

   public void descMe()
   {
      setDesc("DMI: DI+ blue, DI- red, ADX yellow");
   }

   public String getValueString(int index)
   {
      if (diPlusRaw != null)
      {
         return "DI+=" + MbaUtils.DecimalFormat.format(diPlusRaw[index]) + " DI-=" + MbaUtils.DecimalFormat.format(diMinusRaw[index]) + " ADX="
               + MbaUtils.DecimalFormat.format(adxRaw[index]);
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
      drawDMI(g);
      drawDesc(g);
   }

   private void drawDMI(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] diPlus = MbaUtils.toArray(dmiDomainData.getDiPlus(), skipDays);
      int[] diMinus = MbaUtils.toArray(dmiDomainData.getDiMinus(), skipDays);
      int[] adx = MbaUtils.toArray(dmiDomainData.getAdx(), skipDays);

      drawLine(g, diPlus, Color.BLUE, 2);
      drawLine(g, diMinus, Color.RED, 2);
      drawLine(g, adx, Color.YELLOW, 2);
   }

   @Override
   protected int getSkipDays()
   {
      return diPlusRaw.length - getDays();
   }
}
