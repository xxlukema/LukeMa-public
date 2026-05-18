package com.learn.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


//@SqlResultSetMapping(name="named.implicit", entities=@EntityResult(entityClass=Person.class))

@Entity
@Table(name = "people")

@NamedQueries
(
   {
      //@NamedQuery(name="test.LukesQuery", query="select n from Night n where n.date >= :date")
      @NamedQuery(name="named.query.object", query="from Person"),
      @NamedQuery(name="named.query.scalar", query="select firstName, lastName, height, city, state from Person")
   }
)

/*
@NamedNativeQueries
(
	{
		@NamedNativeQuery(name="named.query.native", query="select name from widget")
	}
)
*/

/*
@SqlResultSetMapping(name="named.explicit", entities = {
                        @EntityResult(entityClass=Name.class, fields = {
                                         @FieldResult(name="firstName", column="firstName"),
                                         @FieldResult(name="lastName", column="lastName")
                                      }),
                        @EntityResult(entityClass=Address.class, fields = {
                                         @FieldResult(name="city", column="city"),
                                         @FieldResult(name="state", column="state")
                                      })
                     }
                    )

@NamedNativeQuery(name="named.scalar", query="select firstName, lastName, height, city, state from Person", resultSetMapping="named.explicit")
*/

public class Person implements Serializable
{
	private static final long serialVersionUID = 0L;

   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.AUTO, generator = "Person_Id_SEQ")  
   @SequenceGenerator(name = "Person_Id_SEQ", sequenceName = "Person_Id_SEQ")  

   private Long id;

   @Column(name = "firstName", length = 25)
   private String firstName;

   @Column(name = "lastName", length = 25)
   private String lastName;

   @Column(name = "SSN", length = 11)
   private String ssn;

   @Column(name = "height")
   private float height;

   @Column(name = "city", length = 40)
   private String city;

   @Column(name = "state", length = 40)
   private String state;

   public void setId(Long value)
   {
      this.id=value;
   }
   public Long getId()
   {
      return id;
   }

   public void setFirstName(String value)
   {
      this.firstName=value;
   }
   public String getFirstName()
   {
      return firstName;
   }

   public void setLastName(String value)
   {
      this.lastName=value;
   }
   public String getLastName()
   {
      return lastName;
   }

   public void setSsn(String value)
   {
      this.ssn=value;
   }
   public String getSsn()
   {
      return ssn;
   }

   public void setHeight(float value)
   {
      this.height=value;
   }
   public float getHeight()
   {
      return height;
   }

   public void setCity(String value)
   {
      this.city=value;
   }
   public String getCity()
   {
      return city;
   }

   public void setState(String value)
   {
      this.state=value;
   }
   public String getState()
   {
      return state;
   }

   public String toString()
   {
      StringBuilder sb = new StringBuilder();

      sb.append("Id = ").append(id).append('\n')
        .append("SSN = ").append(ssn).append('\n')
        .append("FirstName = ").append(firstName).append('\n')
        .append("LastName = ").append(lastName).append('\n')
        .append("Height = ").append(height).append('\n')
        .append("city = ").append(city).append('\n')
        .append("State = ").append(state).append('\n');

      return sb.toString();
   }
}
