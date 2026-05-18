package com.learn.hibernate.dao;


import java.util.*;

import com.learn.hibernate.orm.Portfolio;


public interface PortfolioDAO
{
   public List getPortfolios();

   public void displayPortfolios();

   public Portfolio getPortfolioById(Long id);

   public Portfolio savePortfolio(Portfolio portfolio)
   throws Exception;

   public Portfolio updatePortfolio(Portfolio portfolio);

   public Portfolio saveOrUpdatePortfolio(Portfolio portfolio);

   public void deletePortfolio(Long id);

   public void deletePortfolio(Portfolio portfolio);

   public void testHQL();
}



