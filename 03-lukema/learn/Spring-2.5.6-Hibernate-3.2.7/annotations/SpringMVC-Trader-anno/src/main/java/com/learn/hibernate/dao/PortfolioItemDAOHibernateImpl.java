package com.learn.hibernate.dao;


import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import org.apache.log4j.Logger;

import com.learn.hibernate.orm.PortfolioItem;


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
public class PortfolioItemDAOHibernateImpl 
extends HibernateDaoSupport 
implements PortfolioItemDAO
{
   private static final Logger LOG = Logger.getLogger(PortfolioDAOHibernateImpl.class);

   @Transactional(readOnly = true)
   public void displayPortfolioItems()
   {
      List<PortfolioItem> c = getPortfolioItems();

      LOG.debug("Number of records in portfolioItem table: " + c.size());

      for (PortfolioItem w : c)
      {
         String s = w.toString();
         LOG.info(s);
      }
   }

   public List<PortfolioItem> getPortfolioItems()
   {
      return getHibernateTemplate().loadAll(PortfolioItem.class);
   }

   public PortfolioItem getPortfolioItemById(Long id)
   {
      return(PortfolioItem) getHibernateTemplate().get(PortfolioItem.class, id); 
   }

   // The annotation closest to the method has the highest priority.
   // @Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
   @Transactional(readOnly = false, rollbackFor = MySQLException.class)
   public PortfolioItem savePortfolioItem(PortfolioItem portfolioItem)
   throws Exception
   {
      getHibernateTemplate().save(portfolioItem);

      // Throw RuntimeException will cause rollback as defined in advisor.
      // throw new RuntimeException("Test Rollback.");

      // Throw MySQLException will cause rollback as defined in method annotation.
      // throw new MySQLException("Test Rollback.");

      // Throw Exception will NOT cause rollback as defined in advisor.
      // throw new Exception("Test Rollback.");

      return portfolioItem;
   }

   public PortfolioItem saveOrUpdatePortfolioItem(PortfolioItem portfolioItem)
   {
      getHibernateTemplate().saveOrUpdate(portfolioItem);
      return portfolioItem;
   }

   public PortfolioItem updatePortfolioItem(PortfolioItem portfolioItem)
   {
      getHibernateTemplate().update(portfolioItem);
      return portfolioItem;
   }

   public void deletePortfolioItem(Long id)
   {
      PortfolioItem portfolioItem = getPortfolioItemById(id);
      deletePortfolioItem(portfolioItem);
   }

   public void deletePortfolioItem(PortfolioItem portfolioItem)
   {
      getHibernateTemplate().delete(portfolioItem);
   }

   public void testHQL()
   {
   }
}



