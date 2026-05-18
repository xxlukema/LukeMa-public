package com.learn;


import java.sql.Date;


public class PersonImpl
   implements Person
{
   private String   firstName;

   private String   middeName;

   private String   lastName;

   private Date     birthDate;

   private Person[] parents;

   private Person[] children;

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   public String getMiddeName()
   {
      return middeName;
   }

   public void setMiddeName(String middeName)
   {
      this.middeName = middeName;
   }

   public String getLastName()
   {
      return lastName;
   }

   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }

   public Date getBirthDate()
   {
      return birthDate;
   }

   public void setBirthDate(Date birthDate)
   {
      this.birthDate = birthDate;
   }

   public Person[] getParents()
   {
      return parents;
   }

   public void setParents(Person[] parents)
   {
      this.parents = parents;
   }

   public Person[] getChildren()
   {
      return children;
   }

   public void setChildren(Person[] children)
   {
      this.children = children;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
      result = prime * result + ((middeName == null) ? 0 : middeName.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (obj == null)
      {
         return false;
      }
      if (getClass() != obj.getClass())
      {
         return false;
      }
      PersonImpl other = (PersonImpl) obj;
      if (firstName == null)
      {
         if (other.firstName != null)
         {
            return false;
         }
      }
      else if (!firstName.equals(other.firstName))
      {
         return false;
      }
      if (middeName == null)
      {
         if (other.middeName != null)
         {
            return false;
         }
      }
      else if (!middeName.equals(other.middeName))
      {
         return false;
      }

      return true;
   }

}
