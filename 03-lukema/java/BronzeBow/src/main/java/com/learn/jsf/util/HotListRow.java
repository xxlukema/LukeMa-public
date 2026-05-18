package com.learn.jsf.util;


import java.io.Serializable;


public class HotListRow
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private String            symbol1;

   private String            symbol2;

   private String            symbol3;

   public String getSymbol1()
   {
      return symbol1;
   }

   public void setSymbol1(String symbol1)
   {
      this.symbol1 = symbol1;
   }

   public String getSymbol2()
   {
      return symbol2;
   }

   public void setSymbol2(String symbol2)
   {
      this.symbol2 = symbol2;
   }

   public String getSymbol3()
   {
      return symbol3;
   }

   public void setSymbol3(String symbol3)
   {
      this.symbol3 = symbol3;
   }

}
