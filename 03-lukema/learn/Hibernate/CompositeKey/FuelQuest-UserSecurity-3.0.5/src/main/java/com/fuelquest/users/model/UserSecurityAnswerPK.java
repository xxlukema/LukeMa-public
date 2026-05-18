package com.fuelquest.users.model;


public class UserSecurityAnswerPK 
implements java.io.Serializable
{
   private static final long serialVersionUID = 0l;
   
   private User                 user;
   private UserSecurityQuestion userSecurityQuestion;

   public void setUser(User value)
   {
      this.user = value;
   }

   public User getUser()
   {
      return this.user;
   }

   public void setUserSecurityQuestion(UserSecurityQuestion value)
   {
      this.userSecurityQuestion = value;
   }

   public UserSecurityQuestion getUserSecurityQuestion()
   {
      return this.userSecurityQuestion;
   }

   public boolean equals(Object o)
   {
      if (o == null)
      {
         return false;
      }

      if (this == o)
      {
         return true;
      }

      if (! (o instanceof UserSecurityAnswerPK))
      {
         return false;
      }

      final UserSecurityAnswerPK pk = (UserSecurityAnswerPK) o;

      if (! user.equals(pk.getUser()))
      {
         return false;
      }

      if (! userSecurityQuestion.equals(pk.getUserSecurityQuestion()))
      {
         return false;
      }

      return true;
   }

   public int hashCode()
   {
      return user.hashCode() + userSecurityQuestion.hashCode();
   }
}

