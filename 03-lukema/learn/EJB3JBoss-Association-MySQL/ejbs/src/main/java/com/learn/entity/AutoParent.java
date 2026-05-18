package com.learn.entity;


import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@Table(name = "AutoParent")
@TableGenerator(name = "AutoParentIdGenerator", table = "IdAutoParent", pkColumnName = "pk_name", pkColumnValue = "id", valueColumnName = "next_value", allocationSize = 1, initialValue = 1)
public class AutoParent
   implements Serializable
{
   private static final long    serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.TABLE, generator = "AutoParentIdGenerator")
   private Long                 id;

   @Basic(fetch = FetchType.EAGER)
   @Column(name = "name", nullable = false, length = 40, unique = true)
   private String               name;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
   @OrderBy("name")
   private Set<AutoChildOne>    childOneChildren;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private AutoChildTwo         childTwo;

   private static AtomicInteger ctr              = new AtomicInteger(0);

   public AutoParent()
   {
      setName("AutoParent " + ctr.getAndIncrement());
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

   public void setChildOneChildren(Set<AutoChildOne> childOneChildren)
   {
      this.childOneChildren = childOneChildren;
   }

   public Set<AutoChildOne> getChildOneChildren()
   {
      return childOneChildren;
   }

   public void setChildOne(Set<AutoChildOne> childOneChildren)
   {
      this.childOneChildren = childOneChildren;
   }

   public AutoChildTwo getChildTwo()
   {
      return childTwo;
   }

   public void setChildTwo(AutoChildTwo childTwo)
   {
      this.childTwo = childTwo;
   }

}
