package com.learn;


public class Person
   extends PersonBase
{
   private static final long serialVersionUID = 0L;

   private PersonalData      personalData;

   public void setPersonalData(PersonalData personalData)
   {
      this.personalData = personalData;
   }

   public PersonalData getPersonalData()
   {
      return personalData;
   }

}
