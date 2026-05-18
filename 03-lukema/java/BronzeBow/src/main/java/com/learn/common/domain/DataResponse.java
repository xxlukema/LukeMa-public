package com.learn.common.domain;


import java.io.Serializable;




public class DataResponse
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private AllDomainData     allDomainData;
 
   private boolean success = false;
   
   private String errorMessage;
   
   public AllDomainData getAllDomainData()
   {
      return allDomainData;
   }

   public void setAllDomainData(AllDomainData allDomainData)
   {
      this.allDomainData = allDomainData;
   }

   public void setSuccess(boolean success)
   {
      this.success = success;
   }

   public boolean isSuccess()
   {
      return success;
   }

   public void setErrorMessage(String errorMessage)
   {
      this.errorMessage = errorMessage;
   }

   public String getErrorMessage()
   {
      return errorMessage;
   }

}
