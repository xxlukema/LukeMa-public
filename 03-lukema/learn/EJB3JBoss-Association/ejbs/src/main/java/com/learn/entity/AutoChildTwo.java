package com.learn.entity;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.learn.util.EJB3Utils;


@Entity
@Table(name = "Auto_Child_Two")
@TableGenerator(name = "AutoChildTwoIdGenerator", table = "Id_Auto_Child_Two", pkColumnName = "pk_name", pkColumnValue = "id", valueColumnName = "next_value", allocationSize = 1, initialValue = 1)
public class AutoChildTwo
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.TABLE, generator = "AutoChildTwoIdGenerator")
   private Long              id;

   @Basic(fetch = FetchType.LAZY)
   @Column(name = "name", nullable = false, length = 40, unique = true)
   private String            name;

   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private AutoParent        parent;

   public AutoChildTwo()
   {
      setName("AutoChildTwo " + EJB3Utils.newString());
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public AutoParent getParent()
   {
      return parent;
   }

   public void setParent(AutoParent parent)
   {
      this.parent = parent;
   }

}
