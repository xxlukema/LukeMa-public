package com.learn;


import com.learn.bean.BeanBase;


public class PersonBase
   extends BeanBase
{
   private static final long serialVersionUID = 0L;

   private String            name;

   private Integer           age;

   private Gender            gender;

   private String            ssn;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public void setGender(Gender gender)
   {
      this.gender = gender;
   }

   public Gender getGender()
   {
      return gender;
   }

   public void setAge(Integer age)
   {
      this.age = age;
   }

   public Integer getAge()
   {
      return age;
   }

   public void setSsn(String ssn)
   {
      this.ssn = ssn;
   }

   public String getSsn()
   {
      return ssn;
   }

}

