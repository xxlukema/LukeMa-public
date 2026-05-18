package com.learn;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "employee")
public class Employee
{
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id = null;

   @Column(name = "name", length = 25)
   private String name;

   @Column(name = "age")
   private int age = 0;

   @ManyToOne
   @JoinColumn(name="company_fk", nullable=false)
   private Company company;


   public void setId(Long value)
   {
      this.id = value;
   }

   public Long getId()
   {
      return id;
   }

   public void setName(String value)
   {
      this.name = value;
   }

   public String getName()
   {
      return name;
   }

   public void setAge(int value)
   {
      this.age = value;
   }

   public int getAge()
   {
      return age;
   }

   public void setCompany(Company value)
   {
      this.company = value;
   }

   public Company getCompany()
   {
      return company;
   }
}
