package com.learn.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Luke_Test")
public class LukeTest
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer           id;

   @Column(length = 20)
   private String            name;

   private int               num;

   private Integer           i;

   public Integer getId()
   {
      return id;
   }

   public void setId(Integer id)
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

   public int getNum()
   {
      return num;
   }

   public void setNum(int num)
   {
      this.num = num;
   }

   public Integer getI()
   {
      return i;
   }

   public void setI(Integer i)
   {
      this.i = i;
   }

}
