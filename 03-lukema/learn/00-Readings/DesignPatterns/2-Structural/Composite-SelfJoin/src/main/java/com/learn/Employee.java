package com.learn;


import java.util.LinkedList;
import java.util.List;


public class Employee
{
   private String         name;

   private double         salary;

   private List<Employee> subordinates = new LinkedList<Employee>();

   // constructor
   public Employee(String name, double salary)
   {
      this.name = name;
      this.salary = salary;
   }

   public List<Employee> getSubordinates()
   {
      return subordinates;
   }

   public void setSubordinates(List<Employee> subordinates)
   {
      this.subordinates = subordinates;
   }

   public String getName()
   {
      return name;
   }

   public double getSalary()
   {
      return salary;
   }

   public void add(Employee employee)
   {
      subordinates.add(employee);
   }

   public void remove(Employee employee)
   {
      subordinates.remove(employee);
   }
}
