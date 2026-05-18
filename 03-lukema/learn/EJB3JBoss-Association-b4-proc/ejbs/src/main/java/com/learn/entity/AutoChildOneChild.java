package com.learn.entity;


import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

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


@Entity
@Table(name = "Auto_Child_One_Child")
@TableGenerator(name = "AutoChildOneChildIdGenerator", table = "Id_Auto_Child_One_Child", pkColumnName = "pk_name", pkColumnValue = "id", valueColumnName = "next_value", allocationSize = 1, initialValue = 1)
public class AutoChildOneChild
   implements Serializable
{
   private static final long    serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.TABLE, generator = "AutoChildOneChildIdGenerator")
   private Long                 id;

   @Basic(fetch = FetchType.EAGER)
   @Column(name = "name", nullable = false, length = 40, unique = true)
   private String               name;

   @OneToOne(fetch = FetchType.LAZY, mappedBy = "child", cascade = CascadeType.ALL)
   private AutoChildOne         parent;

   private static AtomicInteger ctr              = new AtomicInteger(0);

   public AutoChildOneChild()
   {
      setName("AutoChildOneChild " + ctr.getAndIncrement());
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

   public AutoChildOne getParent()
   {
      return parent;
   }

   public void setParent(AutoChildOne parent)
   {
      this.parent = parent;
   }

}
