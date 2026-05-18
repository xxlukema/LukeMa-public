package com.learn.b.swing.all.option.regression;


import java.util.List;


public class ExpirationDateCallListPutListNode
{
   private String           expirationDate = null;

   private List<OptionNode> callList       = null;

   private List<OptionNode> putList        = null;

   public ExpirationDateCallListPutListNode(String expirationDate, List<OptionNode> callList, List<OptionNode> putList)
   {
      this.expirationDate = expirationDate;
      this.callList = callList;
      this.putList = putList;
   }

   public String getExpirationDate()
   {
      return expirationDate;
   }

   public List<OptionNode> getCallList()
   {
      return callList;
   }

   public List<OptionNode> getPutList()
   {
      return putList;
   }
}
