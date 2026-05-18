package com.learn.core.momentum;


import java.util.Vector;

import com.learn.core.raw.RawData;



public class MomentumDataUtils
{
   public static Vector<MomentumData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<MomentumData> data = new Vector<MomentumData>();

      RawData rdOneDayBack = rawDataVector.get(0);
      MomentumData mdOneDayBack = new MomentumData();
      data.add(mdOneDayBack);
      RawData rdTwoDaysBack = rawDataVector.get(1);
      MomentumData mdTwoDaysBack = new MomentumData();
      data.add(mdTwoDaysBack);

      for (int i = 2; i < rawDataVector.size(); i++)
      {
         RawData rdTodayData = rawDataVector.get(i);

         MomentumData mdTodayData = new MomentumData();
         data.add(mdTodayData);

         mdTodayData.setMf(rdTodayData.getClose() - rdTwoDaysBack.getClose());

         float trA = Math.abs(rdTodayData.getHigh() - rdTodayData.getLow());
         float trB = Math.abs(rdTodayData.getHigh() - rdOneDayBack.getClose());
         float trC = Math.abs(rdTodayData.getLow() - rdOneDayBack.getClose());
         mdTodayData.setTr(Math.max(trA, Math.max(trB, trC)));

         mdTodayData.setAverageX((rdTodayData.getHigh() + rdTodayData.getLow() + rdTodayData.getClose()) / 3);

         mdTodayData.setHighTBP(rdTwoDaysBack.getClose() + Math.max(mdOneDayBack.getMf(), mdTwoDaysBack.getMf()));
         mdTodayData.setLowTBP(rdTwoDaysBack.getClose() + Math.min(mdOneDayBack.getMf(), mdTwoDaysBack.getMf()));

         mdTodayData.setHighStop(mdTodayData.getAverageX() + mdTodayData.getTr());
         mdTodayData.setLowStop(mdTodayData.getAverageX() - mdTodayData.getTr());

         mdTodayData.setHighTarget(mdTodayData.getAverageX() * 2 - rdTodayData.getLow());
         mdTodayData.setLowTarget(mdTodayData.getAverageX() * 2 - rdTodayData.getHigh());

         rdTwoDaysBack = rdOneDayBack;
         rdOneDayBack = rdTodayData;
         mdTwoDaysBack = mdOneDayBack;
         mdOneDayBack = mdTodayData;
      }

      return data;
   }

}
