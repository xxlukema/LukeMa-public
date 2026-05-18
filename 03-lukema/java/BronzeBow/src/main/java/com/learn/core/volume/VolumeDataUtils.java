package com.learn.core.volume;


import java.util.Vector;

import com.learn.core.raw.RawData;



public class VolumeDataUtils
{
   public static Vector<VolumeData> getNewDataVector(Vector<RawData> rawDataVector)
   {
      Vector<VolumeData> data = new Vector<VolumeData>();

      for (RawData rd : rawDataVector)
      {
         VolumeData volumeData = new VolumeData();
         data.add(volumeData);

         volumeData.setVolume(rd.getVolume());
      }

      return data;
   }
}
