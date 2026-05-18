package com.learn.hibernate.dao;


import java.util.*;

import com.learn.hibernate.orm.PortfolioItem;


public interface PortfolioItemDAO
{
   public List getPortfolioItems();

   public void displayPortfolioItems();

   public PortfolioItem getPortfolioItemById(Long id);

   public PortfolioItem savePortfolioItem(PortfolioItem portfolioItem)
   throws Exception;

   public PortfolioItem updatePortfolioItem(PortfolioItem portfolioItem);

   public PortfolioItem saveOrUpdatePortfolioItem(PortfolioItem portfolioItem);

   public void deletePortfolioItem(Long id);

   public void deletePortfolioItem(PortfolioItem portfolioItem);

   public void testHQL();
}



