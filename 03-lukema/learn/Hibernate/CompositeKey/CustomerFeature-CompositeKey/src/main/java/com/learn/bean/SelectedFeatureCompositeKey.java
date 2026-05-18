package com.learn.bean;


import java.io.Serializable;


public class SelectedFeatureCompositeKey
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private Customer          customer;

   private Feature           feature;

   public void setCustomer(Customer value)
   {
      this.customer = value;
   }

   public Customer getCustomer()
   {
      return customer;
   }

   public void setFeature(Feature value)
   {
      this.feature = value;
   }

   public Feature getFeature()
   {
      return feature;
   }

   public boolean equals(Object o)
   {
      if (o == null)
      {
         return false;
      }

      if (this == o)
      {
         return true;
      }

      if (!(o instanceof SelectedFeatureCompositeKey))
      {
         return false;
      }

      final SelectedFeatureCompositeKey ck = (SelectedFeatureCompositeKey) o;

      if (!customer.getSsn().equals(ck.getCustomer().getSsn()))
      {
         return false;
      }

      if (!feature.getFeatureId().equals(ck.getFeature().getFeatureId()))
      {
         return false;
      }

      return true;
   }

   public int hashCode()
   {
      return customer.getSsn().hashCode() + feature.getFeatureId().hashCode();
   }
}
