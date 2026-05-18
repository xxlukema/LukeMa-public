package com.learn;


import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;


public class PersonalDataCompositeUserType
   implements CompositeUserType
{
   private String[] propertyNames  = { "name", "date" };

   private Type[]   peropertyTypes = { Hibernate.STRING, Hibernate.TIMESTAMP };

   public Object assemble(Serializable cached, SessionImplementor session, Object owner)
      throws HibernateException
   {
      return deepCopy(cached);
   }

   public Serializable disassemble(Object value, SessionImplementor session)
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

   public String[] getPropertyNames()
   {
      return propertyNames;
   }

   public Type[] getPropertyTypes()
   {
      return peropertyTypes;
   }

   public Object getPropertyValue(Object component, int property)
      throws HibernateException
   {
      if (component == null)
      {
         return null;
      }

      PersonalData personalData = (PersonalData) component;
      switch (property)
      {
         case 0:
            return personalData.getName();
         case 1:
            return personalData.getDate();
      }

      throw new IllegalArgumentException(property + " is an invalid property index for class type " + component.getClass().getName());
   }

   public void setPropertyValue(Object component, int property, Object value)
      throws HibernateException
   {
      if (component == null)
      {
         return;
      }

      PersonalData personalData = (PersonalData) component;
      switch (property)
      {
         case 0:
            personalData.setName((String) value);
            return;
         case 1:
            personalData.setDate((Date) value);
            return;
      }
   }

   public boolean isMutable()
   {
      return true;
   }

   public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
      throws HibernateException, SQLException
   {
      PersonalData personalData = new PersonalData();

      personalData.setName((String) Hibernate.STRING.nullSafeGet(rs, names[0]));
      personalData.setDate((Date) Hibernate.TIMESTAMP.nullSafeGet(rs, names[1]));

      return personalData;
   }

   public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
      throws HibernateException, SQLException
   {
      PersonalData personalData = (PersonalData) value;
      Hibernate.STRING.nullSafeSet(st, personalData.getName(), index);
      Hibernate.TIMESTAMP.nullSafeSet(st, personalData.getDate(), index + 1);
   }

   public Object replace(Object original, Object target, SessionImplementor session, Object owner)
      throws HibernateException
   {
      return null;
   }

   public Class<PersonalData> returnedClass()
   {
      return PersonalData.class;
   }

   public boolean equals(Object x, Object y)
      throws HibernateException
   {
      return (x == y) || (x != null && y != null && x.equals(y));
   }

   public int hashCode(Object x)
      throws HibernateException
   {
      if (x instanceof PersonalDataCompositeUserType)
      {
         return ((PersonalDataCompositeUserType) x).hashCode();
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
      result = prime * result + Arrays.hashCode(peropertyTypes) + Arrays.hashCode(propertyNames);
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
      PersonalDataCompositeUserType other = (PersonalDataCompositeUserType) obj;
      if (!(Arrays.equals(peropertyTypes, other.peropertyTypes) && Arrays.equals(propertyNames, other.propertyNames)))
         return false;
      return true;
   }

}
