package com.fuelquest.users.model;


public class UserSecurityQuestion
   implements java.io.Serializable
{
   private static final long serialVersionUID = 1L;

   private Long              id;

   private String            question;

   private int               groupingNumber;

   private int               sequenceInGroup;

   public Long getId()
   {
      return this.id;
   }

   public void setId(Long value)
   {
      this.id = value;
   }

   public String getQuestion()
   {
      return this.question;
   }

   public void setQuestion(String value)
   {
      this.question = value;
   }

   public int getGroupingNumber()
   {
      return this.groupingNumber;
   }

   public void setGroupingNumber(int value)
   {
      this.groupingNumber = value;
   }

   public int getSequenceInGroup()
   {
      return this.sequenceInGroup;
   }

   public void setSequenceInGroup(int value)
   {
      this.sequenceInGroup = value;
   }
}
