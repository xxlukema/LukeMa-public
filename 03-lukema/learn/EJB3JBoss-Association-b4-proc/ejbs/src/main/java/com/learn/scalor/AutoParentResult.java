package com.learn.scalor;


import java.io.Serializable;

import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.QueryHint;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;


@Entity
@NamedNativeQueries(value = { @NamedNativeQuery(name = "Call_Luke_Proc", query = "{call Luke_Proc(:ticker, :swapNum output, :swapId output, :date output, :rate output)}", hints = { @QueryHint(name = "org.hibernate.callable", value = "true") }, resultSetMapping = "LukeProcResultMapping", resultClass = LukeProcResult.class) })
//@NamedNativeQueries(value = { @NamedNativeQuery(name = "Call_Luke_Proc", query = "{call Luke_Proc(:ticker)}", hints = { @QueryHint(name = "org.hibernate.callable", value = "true") }, resultSetMapping = "LukeProcResultMapping", resultClass = LukeProcResult.class) })
@SqlResultSetMappings(value = {
      @SqlResultSetMapping(name = "LukeProcResultMapping", columns = { @ColumnResult(name = "swapNum"), @ColumnResult(name = "swapId"),
            @ColumnResult(name = "date"), @ColumnResult(name = "rate") }),
      @SqlResultSetMapping(name = "AutoParentResultMapping", entities = { @EntityResult(entityClass = com.learn.scalor.AutoParentResult.class, fields = {
            @FieldResult(name = "id", column = "id"), @FieldResult(name = "name", column = "name") }) }) })
public class AutoParentResult
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
