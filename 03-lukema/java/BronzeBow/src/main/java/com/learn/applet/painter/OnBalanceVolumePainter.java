package com.learn.applet.painter;


import java.awt.Color;
import java.awt.Graphics;

import com.learn.common.domain.OnBalanceVolumeDomainData;
import com.learn.common.util.MbaUtils;


public class OnBalanceVolumePainter
   extends PainterBase
{
   private OnBalanceVolumeDomainData onBalanceVolumeDomainData;

   private float[]                   obvRaw;

   private float[]                   pvtRaw;

   public OnBalanceVolumePainter(OnBalanceVolumeDomainData onBalanceVolumeDomainData)
   {
      super(onBalanceVolumeDomainData);

      this.onBalanceVolumeDomainData = onBalanceVolumeDomainData;

      setDomainData(onBalanceVolumeDomainData);
      obvRaw = onBalanceVolumeDomainData.getObvRaw();
      pvtRaw = onBalanceVolumeDomainData.getPvtRaw();
   }

   public void descMe()
   {
      setDesc("OBV_PVT: OBV-green PVT-gray");
   }

   public String getValueString(int index)
   {
      if (obvRaw != null)
      {
         return "OBV=" + MbaUtils.LongFormat.format(obvRaw[index]) + "K" + "   PVT=" + MbaUtils.LongFormat.format(pvtRaw[index]) + "K(Adjusted)";
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
      drawOnBalanceVolume(g);
      drawDesc(g);
   }

   private void drawOnBalanceVolume(Graphics g)
   {
      int skipDays = getSkipDays();

      int[] obvI = MbaUtils.toArray(onBalanceVolumeDomainData.getObv(), skipDays);
      int[] pvtI = MbaUtils.toArray(onBalanceVolumeDomainData.getPvt(), skipDays);

      drawLine(g, obvI, Color.GREEN, 2);
      drawLine(g, pvtI, Color.GRAY, 2);
   }

   @Override
   protected int getSkipDays()
   {
      return obvRaw.length - getDays();
   }
}
