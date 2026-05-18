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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "products")
@NamedQueries( { @NamedQuery(name = "product.findAll", query = "from Product"),
      @NamedQuery(name = "product.findByName", query = "from Product where name = :name") })
public class Product
   implements Serializable
{
   private static final long serialVersionUID = 0L;

   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long              id               = null;

   @Column(name = "name", length = 25, unique = true)
   private String            name;

   @Column(name = "price")
   private Float             price            = null;

   @ManyToMany(mappedBy = "productSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   private Set<Customer>     customerSet      = new CopyOnWriteArraySet<Customer>();

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

   public Float getPrice()
   {
      return price;
   }

   public void setPrice(Float price)
   {
      this.price = price;
   }

   public boolean addCustomer(Customer customer)
   {
      return getCustomerSet().add(customer);
   }

   public boolean removeCustomer(Customer customer)
   {
      return getCustomerSet().remove(customer);
   }

   public void clearCustomerSet()
   {
      getCustomerSet().clear();
   }

   public Set<Customer> getCustomerSet()
   {
      return customerSet;
   }

}
