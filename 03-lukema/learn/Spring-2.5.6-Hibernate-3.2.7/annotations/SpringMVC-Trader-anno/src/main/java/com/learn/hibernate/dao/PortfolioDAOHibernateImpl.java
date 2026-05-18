package com.learn.hibernate.dao;


import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import org.apache.log4j.Logger;

import com.learn.hibernate.orm.Portfolio;


/**
 * Priorities: 
 * The annotation closest to the method has the highest priority.
 * Class level annotation has less priority than the method level annotaion.
 * Descriptives in the advisor has the least priority.
 *
 * Defaults:
 * The default propagartion is PROPAGATION_REQUIRED.
 *
 */
// @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
@Transactional(readOnly = false)
public class PortfolioDAOHibernateImpl 
extends HibernateDaoSupport 
implements PortfolioDAO
{
   private static final Logger LOG = Logger.getLogger(PortfolioDAOHibernateImpl.class);

   @Transactional(readOnly = true)
   public void displayPortfolios()
   {
      List<Portfolio> c = getPortfolios();

      LOG.debug("Number of records in portfolio table: " + c.size());

      for (Portfolio w : c)
      {
         String s = w.toString();
         LOG.info(s);
      }
   }

   public List<Portfolio> getPortfolios()
   {
      return getHibernateTemplate().loadAll(Portfolio.class);
   }

   public Portfolio getPortfolioById(Long id)
   {
      return(Portfolio) getHibernateTemplate().get(Portfolio.class, id); 
   }

   // The annotation closest to the method has the highest priority.
   // @Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
   @Transactional(readOnly = false, rollbackFor = MySQLException.class)
   public Portfolio savePortfolio(Portfolio portfolio)
   throws Exception
   {
      getHibernateTemplate().save(portfolio);

      // Throw RuntimeException will cause rollback as defined in advisor.
      // throw new RuntimeException("Test Rollback.");

      // Throw MySQLException will cause rollback as defined in method annotation.
      // throw new MySQLException("Test Rollback.");

      // Throw Exception will NOT cause rollback as defined in advisor.
      // throw new Exception("Test Rollback.");

      return portfolio;
   }

   public Portfolio saveOrUpdatePortfolio(Portfolio portfolio)
   {
      getHibernateTemplate().saveOrUpdate(portfolio);
      return portfolio;
   }

   public Portfolio updatePortfolio(Portfolio portfolio)
   {
      getHibernateTemplate().update(portfolio);
      return portfolio;
   }

   public void deletePortfolio(Long id)
   {
      Portfolio portfolio = getPortfolioById(id);
      deletePortfolio(portfolio);
   }

   public void deletePortfolio(Portfolio portfolio)
   {
      getHibernateTemplate().delete(portfolio);
   }

   public void testHQL()
   {
   }
}



