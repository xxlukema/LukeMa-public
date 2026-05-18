package com.learn;


import org.apache.commons.validator.EmailValidator;


/**
 * Hello world!
 *
 */
public class App 
{
   public static void main( String[] args )
   {
      System.out.println( "Hello World!" );

      /*
      String regex = "[@#$%^&*\\|+=]";

      String str = "This is *:@:$:^:\\:| the line";

      String newStr = str.replaceAll(regex, "-AT-");

      System.out.println(newStr);
      */

      /*
      String validEMailAddress = "^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+[.])+([a-zA-Z0-9]{2,4})+$";

      String email = "luke.ma@yah-oo.com";

      if (email.matches(validEMailAddress))
      {
         System.out.println("Valid");
      }
      else
      {
         System.out.println("### Not Valid");
      }
      */

      String email = "lu%ke.ma@yah-o&o.com";

      if (EmailValidator.getInstance().isValid(email))
      {
         System.out.println("Valid");
      }
      else
      {
         System.out.println("### Not Valid");
      }


   }
}
