package com.learn.bean;


import java.io.Serializable;


public class Feature
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private String            featureId;

   private int               version          = -1;

   private String            description;

   public void setFeatureId(String value)
   {
      this.featureId = value;
   }

   public String getFeatureId()
   {
      return featureId;
   }

   public void setVersion(int value)
   {
      version = value;
   }

   public int getVersion()
   {
      return version;
   }

   public void setDescription(String value)
   {
      this.description = value;
   }

   public String getDescription()
   {
      return description;
   }
}
