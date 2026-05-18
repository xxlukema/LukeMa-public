package com.learn.bean;


import java.io.Serializable;


public class SelectedFeature
   implements Serializable
{
   private static final long           serialVersionUID = 1L;

   private SelectedFeatureCompositeKey selectedFeatureCompositeKey;

   private int                         version          = -1;

   public void setSelectedFeatureCompositeKey(SelectedFeatureCompositeKey value)
   {
      this.selectedFeatureCompositeKey = value;
   }

   public SelectedFeatureCompositeKey getSelectedFeatureCompositeKey()
   {
      return selectedFeatureCompositeKey;
   }

   public void setVersion(int value)
   {
      version = value;
   }

   public int getVersion()
   {
      return version;
   }
}
