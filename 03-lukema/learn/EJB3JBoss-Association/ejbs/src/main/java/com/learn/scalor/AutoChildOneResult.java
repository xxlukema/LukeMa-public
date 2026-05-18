package com.learn.scalor;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;


@Entity
@SqlResultSetMapping(name = "AutoChildOneResultMapping", entities = { @EntityResult(entityClass = com.learn.scalor.AutoChildOneResult.class, fields = {
      @FieldResult(name = "id", column = "id"), @FieldResult(name = "name", column = "name") }) })
public class AutoChildOneResult
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Id
   private Long              id;

   private String            name;

   public void setName(String name)
   {
      this.name = name;
   }

   public String getName()
   {
      return name;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public Long getId()
   {
      return id;
   }
}
