package com.learn;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;


@Entity
@Table(name = "widget")
public class Widget
{
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy=GenerationType.AUTO)
   private long   id = 0;

   @Column(name = "name", length = 25)
   private String name = null;

   @Column(name = "size")
   private int    size = 0;

   public void setId(long id)
   {
      this.id = id;
   }

   public long getId()
   {
      return id;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getName()
   {
      return name;
   }

   public void setSize(int size)
   {
      this.size = size;
   }

   public int getSize()
   {
      return size;
   }

   public String toString()
   {
      String ret =
         "Widget: \n"+
         "id   = "+id+"\n"+
         "name = "+name+"\n"+
         "size = "+size;

      return ret;
   }
}
