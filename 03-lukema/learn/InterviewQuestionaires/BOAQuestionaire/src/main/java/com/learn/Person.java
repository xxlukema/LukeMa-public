package com.learn;


import java.sql.Date;


public interface Person
{
   String getFirstName();

   String getMiddeName();

   String getLastName();

   Date getBirthDate();

   Person[] getParents();

   Person[] getChildren();

}
