package com.learn;


import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;


public class PersonalDataUserType
   implements UserType
{
   private int[] types = { Types.VARCHAR, Types.TIMESTAMP };

   public Object assemble(Serializable cached, Object owner)
      throws HibernateException
   {
      return deepCopy(cached);
   }

   public Serializable disassemble(Object value)
      throws HibernateException
   {
      return (Serializable) deepCopy(value);
   }

   public Object deepCopy(Object value)
      throws HibernateException
   {
      if (value == null)
      {
         return null;
      }

      PersonalData personalData = (PersonalData) value;
      PersonalData newPersonalData = new PersonalData();

      String name = null;
      if (personalData.getName() != null)
      {
         name = new String(personalData.getName());
      }
      newPersonalData.setName(name);

      Date date = null;
      if (personalData.getDate() != null)
      {
         date = new Date(personalData.getDate().getTime());
      }
      newPersonalData.setDate(date);

      return newPersonalData;
   }

   public boolean isMutable()
   {
      return true;
   }

   public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
      throws HibernateException, SQLException
   {
      PersonalData personalData = new PersonalData();
      
      personalData.setName((String) Hibernate.STRING.nullSafeGet(rs, names[0]));
      personalData.setDate((Date) Hibernate.TIMESTAMP.nullSafeGet(rs, names[1]));

      return personalData;
   }

   public void nullSafeSet(PreparedStatement st, Object value, int index)
      throws HibernateException, SQLException
   {
      PersonalData personalData = (PersonalData) value;
      Hibernate.STRING.nullSafeSet(st, personalData.getName(), index);
      Hibernate.TIMESTAMP.nullSafeSet(st, personalData.getDate(), index + 1);
   }

   public Object replace(Object original, Object target, Object owner)
      throws HibernateException
   {
      return null;
   }

   public Class<PersonalData> returnedClass()
   {
      return PersonalData.class;
   }

   public int[] sqlTypes()
   {
      return types;
   }

   public boolean equals(Object x, Object y)
      throws HibernateException
   {
      return (x == y) || (x != null && y != null && x.equals(y));
   }

   public int hashCode(Object x)
      throws HibernateException
   {
      if (x instanceof PersonalDataUserType)
      {
         return ((PersonalDataUserType) x).hashCode();
      }
      else
      {
         return 0;
      }
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(types);
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      PersonalDataUserType other = (PersonalDataUserType) obj;
      if (!Arrays.equals(types, other.types))
         return false;
      return true;
   }

}
