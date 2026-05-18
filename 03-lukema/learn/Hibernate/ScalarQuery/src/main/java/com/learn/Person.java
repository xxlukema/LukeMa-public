package com.learn;


public class Person
   extends PersonBase
{
   private static final long serialVersionUID = 0L;

   private String            ssn;

   private float             height;

   private String            city;

   private String            state;

   public void setSsn(String value)
   {
      this.ssn = value;
   }

   public String getSsn()
   {
      return ssn;
   }

   public void setHeight(float value)
   {
      this.height = value;
   }

   public float getHeight()
   {
      return height;
   }

   public void setCity(String value)
   {
      this.city = value;
   }

   public String getCity()
   {
      return city;
   }

   public void setState(String value)
   {
      this.state = value;
   }

   public String getState()
   {
      return state;
   }

   public String toString()
   {
      StringBuilder sb = new StringBuilder();

      sb.append("Id = ").append(getId()).append('\n').append("SSN = ").append(ssn).append('\n').append("Name = ").append(getName()).append('\n').append('\n')
            .append("Height = ").append(height).append('\n').append("city = ").append(city).append('\n').append("State = ").append(state).append('\n');

      return sb.toString();
   }
}
