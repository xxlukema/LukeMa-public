package com.learn.bean;


import java.util.ArrayList;
import java.util.List;


public class Portfolio
   extends BeanBase
{
   private static final long   serialVersionUID = 1L;

   private Customer            customer;

   private Float               cash;

   private List<PortfolioItem> portfolioItems;

   public List<PortfolioItem> getPortfolioItems()
   {
      if (portfolioItems == null)
      {
         portfolioItems = new ArrayList<PortfolioItem>();
      }

      return portfolioItems;
   }

   public void setPortfolioItems(List<PortfolioItem> portfolioItems)
   {
      this.portfolioItems = portfolioItems;
   }

   public void setCash(Float cash)
   {
      this.cash = cash;
   }

   public Float getCash()
   {
      return cash;
   }

   public void setCustomer(Customer value)
   {
      this.customer = value;
   }

   public Customer getCustomer()
   {
      return customer;
   }
}
