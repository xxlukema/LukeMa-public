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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.learn.util.EJB3Utils;


@Entity
@Table(name = "Auto_Child_One")
@TableGenerator(name = "AutoChildOneIdGenerator", table = "Id_Auto_Child_One", pkColumnName = "pk_name", pkColumnValue = "id", valueColumnName = "next_value", allocationSize = 1, initialValue = 1)
public class AutoChildOne
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.TABLE, generator = "AutoChildOneIdGenerator")
   private Long              id;

   @Basic(fetch = FetchType.EAGER)
   @Column(name = "name", nullable = false, length = 40, unique = true)
   private String            name;

   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private AutoParent        parent;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private AutoChildOneChild child;

   public AutoChildOne()
   {
      setName("AutoChildOne " + EJB3Utils.newString());
   }

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public AutoParent getParent()
   {
      return parent;
   }

   public void setParent(AutoParent parent)
   {
      this.parent = parent;
   }

   public AutoChildOneChild getChild()
   {
      return child;
   }

   public void setChild(AutoChildOneChild child)
   {
      this.child = child;
   }

}
