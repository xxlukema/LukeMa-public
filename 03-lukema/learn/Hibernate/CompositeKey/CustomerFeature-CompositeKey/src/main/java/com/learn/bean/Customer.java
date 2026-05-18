package com.learn.bean;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class Customer
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private String            ssn;

   private int               version          = -1;

   private String            name;

   private Address           address;

   private List              selectedFeatures = new LinkedList();

   public void setSsn(String value)
   {
      this.ssn = value;
   }

   public String getSsn()
   {
      return ssn;
   }

   public void setVersion(int value)
   {
      version = value;
   }

   public int getVersion()
   {
      return version;
   }

   public void setName(String value)
   {
      this.name = value;
   }

   public String getName()
   {
      return name;
   }

   public void setAddress(Address value)
   {
      this.address = value;
   }

   public Address getAddress()
   {
      return address;
   }

   public void setSelectedFeatures(List value)
   {
      selectedFeatures = value;
   }

   public List getSelectedFeatures()
   {
      return selectedFeatures;
   }

   public void addSelectedFeature(SelectedFeature value)
   {
      value.getSelectedFeatureCompositeKey().setCustomer(this);
      selectedFeatures.add(value);
   }

   public void removeSelectedFeature(SelectedFeature value)
   {
      selectedFeatures.remove(value);
   }

   public void clearSelectedFeature()
   {
      selectedFeatures.clear();
   }
}
