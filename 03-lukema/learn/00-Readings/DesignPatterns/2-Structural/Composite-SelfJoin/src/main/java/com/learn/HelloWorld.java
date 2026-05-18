package com.learn;


import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
   {
      LOG.info("Hello World!");

      Employee cfo = new Employee("CFO", 30000);

      Employee headFinance1 = new Employee("Head Finance. North Zone", 20000);
      cfo.add(headFinance1);
      Employee headFinance2 = new Employee("Head Finance. West Zone", 22000);
      cfo.add(headFinance2);

      Employee accountant1 = new Employee("Accountant1", 10000);
      headFinance1.add(accountant1);
      Employee accountant2 = new Employee("Accountant2", 9000);
      headFinance1.add(accountant2);

      Employee accountant3 = new Employee("Accountant3", 11000);
      headFinance2.add(accountant3);
      Employee accountant4 = new Employee("Accountant4", 12000);
      headFinance2.add(accountant4);
   }
}
