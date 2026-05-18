package com.learn;


import java.util.Comparator;


/**
 * Compare two person's names so that it can be used to sort persons by first name and middle name
 */
public class PersonComparator
   implements Comparator<Person>
{
   /**
    * Compares its two persons for order. Returns a negative integer, zero, or a positive integer 
    * as the first argument is less than, equal to, or greater than the second. 
    * It is used to sort persons by first name and middle name.
    * If first names are not the same, return the result based on first name comparison. 
    * If first names are the same, compare middle names.
    * 
    * @param person1 - the first person to be compared.
    * @param person2 - the second person to be compared. 
    * @return a negative integer, zero, or a positive integer as the 
    * first argument is less than, equal to, or greater than the second. 
    */
   @Override
   public int compare(Person person1, Person person2)
   {
      // Compare first names
      int firstNameComparison = person1.getFirstName().compareTo(person2.getFirstName());
      if (firstNameComparison == 0)
      {
         // If first names are the same, compare middle names
         return person1.getMiddeName().compareTo(person2.getMiddeName());
      }
      else
      {
         // If first names are not the same, return the result based on first name comparison
         return firstNameComparison;
      }
   }
}
