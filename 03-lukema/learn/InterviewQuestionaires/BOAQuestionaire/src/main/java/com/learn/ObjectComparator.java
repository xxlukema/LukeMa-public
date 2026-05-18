package com.learn;


import java.util.Comparator;


/**
 * Compare two person's names so that it can be used to sort persons by first name and middle name
 */
public class ObjectComparator
   implements Comparator<Object>
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
    * @throws ClassCastException - if the arguments' types prevent them from being compared by this comparator.
    * @throws NullPointerException - if the parameters are null or first names and last names are null.
    */
   @Override
   public int compare(Object o1, Object o2)
   {
      Person person1 = (Person) o1;
      Person person2 = (Person) o2;
      // Compare first names
      int firstNameComparison = person1.getFirstName().compareTo(person2.getFirstName());
      if (firstNameComparison == 0)
      {
         // If first names are the same, compare middle names
         return convertReturnResultToSepcification(person1.getMiddeName().compareTo(person2.getMiddeName()));
      }
      else
      {
         // If first names are not the same, return the result based on first name comparison
         return convertReturnResultToSepcification(firstNameComparison);
      }
   }

   /**
    * Return result convertor. Converts the string comparison result to -1, 0, or 1.
    * @param actualValue
    * @return -1 if o1's first name, middle name are lexically less
    * than o2's first name, middle name, 0 if o1's first name 
    * and middle name are lexically the same as o2's,
    * and 1 otherwise.
    */
   private int convertReturnResultToSepcification(int actualValue)
   {
      if (actualValue < 0)
      {
         return -1;
      }
      else if (actualValue > 0)
      {
         return 1;
      }
      else
      {
         return 0;
      }
   }
}
