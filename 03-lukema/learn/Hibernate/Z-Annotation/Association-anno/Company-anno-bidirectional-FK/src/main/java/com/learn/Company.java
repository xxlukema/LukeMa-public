package com.learn;


import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "Company")

@NamedQueries
(
   {
      //@NamedQuery(name="test.LukesQuery", query="select n from Night n where n.date >= :date")
      @NamedQuery(name="company.findAll", query="from Company"),
      @NamedQuery(name="company.findByName", query="from Company where name = :name")
   }
)

public class Company
{
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id = null;

   @Column(name = "name", length = 25)
   private String name;

   @OneToMany(mappedBy="company", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
   private Set<Employee> employeeSet = new CopyOnWriteArraySet<Employee>();

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

   public void setEmployeeSet(Set<Employee> value)
   {
      this.employeeSet = value;
   }

   public Set<Employee> getEmployeeSet()
   {
      return employeeSet;
   }

   public void addEmployee(Employee value)
   {
      getEmployeeSet().add(value);
   }

   public void removeEmployee(Employee value)
   {
      getEmployeeSet().remove(value);
   }

   public void clearEmployeeSet()
   {
      getEmployeeSet().clear();
   }
}
