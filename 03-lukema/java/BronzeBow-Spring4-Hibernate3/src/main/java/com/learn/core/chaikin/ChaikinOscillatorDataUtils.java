package com.learn.core.chaikin;


import java.util.Vector;

import com.learn.core.accumDist.AccumDistLineData;
import com.learn.core.accumDist.AccumDistLineDataUtils;
import com.learn.core.raw.RawData;



public class ChaikinOscillatorDataUtils
{
   public static Vector<ChaikinOscillatorData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<ChaikinOscillatorData> data = new Vector<ChaikinOscillatorData>();

      float expPerct5 = (float) (2.0 / (5 + 1));
      float expPerct10 = (float) (2.0 / (10 + 1));

      float lastEMA5 = 0;
      float lastEMA10 = 0;

      for (int i = 0; i < rawDataVector.size(); i++)
      {
         AccumDistLineData adlData = AccumDistLineDataUtils.getNewDataVector(rawDataVector).get(i);

         ChaikinOscillatorData chaikinData = new ChaikinOscillatorData();
         data.add(chaikinData);

         float ema5 = (float) (lastEMA5 * (1.0 - expPerct5) + adlData.getAccumDistLine() * expPerct5);
         float ema10 = (float) (lastEMA10 * (1.0 - expPerct10) + adlData.getAccumDistLine() * expPerct10);
         chaikinData.setEma5(ema5);
         chaikinData.setEma10(ema10);

         chaikinData.setEma5_ema10(ema5 - ema10);

         float max = Integer.MIN_VALUE;
         for (int k = 0; k < 5; k++)
         {
            int m = Math.max(0, i - k);

            ChaikinOscillatorData oldData = data.get(m);
            max = Math.max(max, Math.abs(oldData.getEma5_ema10()));
         }

         chaikinData.setDimensionless(chaikinData.getEma5_ema10() / max);

         lastEMA5 = ema5;
         lastEMA10 = ema10;
      }

      return data;
   }
}
