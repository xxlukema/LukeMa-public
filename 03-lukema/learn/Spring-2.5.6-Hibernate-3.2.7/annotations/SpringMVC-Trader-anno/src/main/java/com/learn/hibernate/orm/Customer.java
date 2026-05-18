package com.learn.hibernate.orm;


import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "customers",
       uniqueConstraints = {@UniqueConstraint(columnNames={"usr"})}
)
public class Customer
implements Serializable
{
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Integer   id = 0;

   @Column(name = "usr", length = 10)
   private String usr;

   @Column(name = "passwd", length = 10)
   private String passwd;

   @OneToOne(cascade = CascadeType.ALL)
   @JoinTable(name = "CustomerPortfolios",
              joinColumns = @JoinColumn(name="customer_fk"),
              inverseJoinColumns = @JoinColumn(name="portfolio_fk")
             )
   private Portfolio portfolio;

   public void setId(Integer value)
   {
      this.id = value;
   }

   public Integer getId()
   {
      return id;
   }

   public void setUsr(String value)
   {
      this.usr = value;
   }

   public String getUsr()
   {
      return usr;
   }

   public void setPasswd(String value)
   {
      this.passwd = value;
   }

   public String getPasswd()
   {
      return passwd;
   }

   public void setPortfolio(Portfolio value)
   {
      this.portfolio = value;
   }

   public Portfolio getPortfolio()
   {
      return portfolio;
   }
}

