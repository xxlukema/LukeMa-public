package com.learn.entity;


import java.io.Serializable;
import java.util.Set;

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

import com.learn.util.EJB3Utils;


@Entity
@Table(name = "Auto_Parent")
@TableGenerator(name = "AutoParentIdGenerator", table = "Id_Auto_Parent", pkColumnName = "pk_name", pkColumnValue = "id", valueColumnName = "next_value", allocationSize = 1, initialValue = 1)
//@TableGenerator(name = "AutoParentIdGenerator", table = "id_obj_gen_tmp", pkColumnName = "type", pkColumnValue = "529", valueColumnName = "avail_id", allocationSize = 1, initialValue = 1)
public class AutoParent
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.TABLE, generator = "AutoParentIdGenerator")
   private Long              id;

   @Basic(fetch = FetchType.EAGER)
   @Column(name = "name", nullable = false, length = 40, unique = true)
   private String            name;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
   @OrderBy("name")
   private Set<AutoChildOne> childOneChildren;

   @OneToOne(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
   private AutoChildTwo      childTwo;

   public AutoParent()
   {
      setName("AutoParent " + EJB3Utils.newString());
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
