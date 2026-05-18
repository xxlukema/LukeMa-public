package com.learn;


import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "Customers")
@NamedQueries( { @NamedQuery(name = "customer.findAll", query = "from Customer"), @NamedQuery(name = "customer.findByName", query = "from Customer where name = :name") })
public class Customer
   implements Serializable
{
   private static final long serialVersionUID = 0L;
   
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long         id          = null;

   @Column(name = "name", length = 25, unique=true)
   private String       name;

   @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinTable(name = "Customers_Products", joinColumns = @JoinColumn(name = "Product_Id"), inverseJoinColumns = @JoinColumn(name = "Customer_id"))
   private Set<Product> productSet = new CopyOnWriteArraySet<Product>();

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

   public boolean addProduct(Product value)
   {
      return getProductSet().add(value);
   }

   public boolean removeProduct(Product value)
   {
      return getProductSet().remove(value);
   }

   public void clearProductSet()
   {
      getProductSet().clear();
   }
   
   public Set<Product> getProductSet()
   {
      return productSet;
   }

}
