package com.learn.session.auto;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.learn.entity.AutoChildOne;
import com.learn.entity.AutoChildOneChild;
import com.learn.entity.AutoChildTwo;
import com.learn.entity.AutoParent;


@Stateless
public class AutoAssociationSessionBean
   implements AutoAssociationSessionBeanLocal, AutoAssociationSessionBeanRemote
{
   private static final long     serialVersionUID = 1L;

   protected static final Logger LOG              = Logger.getLogger(AutoAssociationSessionBean.class);

   @PersistenceContext(unitName = "lab-entity-demo4")
   EntityManager                 entityManager;

   @Override
   public void addRecord()
      throws Exception
   {
      LOG.info("########## BookSessionBean.addRecord() invoked.");

      /**
       * Refere to MappedBy-Chart.txt 
       * 
       * 
       *     Parent (1)--------------(n*) ChildOne (1*) ------------- (1) ChildOneChild
       *     (1*)
       *      |
       *      |--------------------(1) ChildTwo
       *     
       *     
       *     (*) Controller of the map. Using "mappedBy" by the associated party.
       *
       * 
       */

      // Parent1
      AutoParent parent1 = new AutoParent();
      parent1 = entityManager.merge(parent1);

      AutoChildOne childOne1 = new AutoChildOne();
      childOne1.setParent(parent1);
      entityManager.merge(childOne1);

      AutoChildOne childOne2 = new AutoChildOne();
      childOne2.setParent(parent1);
      childOne2 = entityManager.merge(childOne2); //

      AutoChildOne childOne3 = new AutoChildOne();
      childOne3.setParent(parent1);
      entityManager.merge(childOne3);

      AutoChildTwo childTwo1 = new AutoChildTwo();
      parent1.setChildTwo(childTwo1);
      parent1 = entityManager.merge(parent1);

      AutoChildOneChild childOneChild1 = new AutoChildOneChild();
      childOne2.setChild(childOneChild1);
      entityManager.merge(childOne2); //

      AutoParent parent2 = new AutoParent();
      parent2 = entityManager.merge(parent2);

      AutoChildOne childOne4 = new AutoChildOne();
      childOne4.setParent(parent2);
      entityManager.merge(childOne4);

      AutoParent parent3 = new AutoParent();
      entityManager.merge(parent3);

      LOG.info("Data Initiated.");
   }

   @Override
   public void retrieveData()
      throws Exception
   {
      LOG.info("########## BookSessionBean.retrieveData() invoked.");

      selectForAutoChildOneChild();
      selectAutoParent();
   }

   /**
    * TODO: Implemet LIKE
    */
   protected void selectForAutoChildOneChild()
      throws Exception
   {
      LOG.info("Inside selectForAutoChildOneChild()");

      /**
       * CriteriaBuilder belongs to JPA 2.0
       */
      /*      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<AutoChildOne> criteriaQuery = criteriaBuilder.createQuery(AutoChildOne.class);
            Root<AutoChildOneChild> autoChildOneChild = criteriaQuery.from(AutoChildOneChild.class);
            criteriaQuery.where(criteriaBuilder.equal(autoChildOneChild.get("name"), "AutoChildOneChild 1"));
            List<AutoChildOne> autoChildOnes = entityManager.createQuery(criteriaQuery).getResultList();
      */

      String oql = "select autoChildOne from AutoChildOne autoChildOne where autoChildOne.name = ?";
      Query query = entityManager.createQuery(oql);
      query.setParameter(1, "AutoChildOneChild 1");

      @SuppressWarnings("unchecked")
      List<AutoChildOne> autoChildOnes = query.getResultList();

      if (autoChildOnes.size() == 1)
      {
         AutoChildOne autoChildOne = autoChildOnes.get(0);

         LOG.info("Filling the lazy loading...");
         AutoParent autoParent = autoChildOne.getParent();
         LOG.info("autoParent.getName() = " + autoParent.getName());
         LOG.info("Done with the lazy loading.");
      }
      else
      {
         LOG.error("No AutoChildOneChild found.");
      }
   }

   protected void selectAutoParent()
      throws Exception
   {
      LOG.info("Inside selectAutoParent()");

      /**
       * CriteriaBuilder belongs to JPA 2.0
       */
      /*
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<AutoParent> criteriaQuery = criteriaBuilder.createQuery(AutoParent.class);
      Root<AutoParent> autoParent = criteriaQuery.from(AutoParent.class);
      criteriaQuery.where(criteriaBuilder.equal(autoParent.get("name"), "AutoParent 1"));
      List<AutoParent> autoParents = entityManager.createQuery(criteriaQuery).getResultList();
      */

      String oql = "select autoParent from AutoParent autoParent where autoParent.name like ?";
      Query query = entityManager.createQuery(oql);
      query.setParameter(1, "AutoParent %");

      @SuppressWarnings("unchecked")
      List<AutoParent> autoParents = query.getResultList();

      if (autoParents.size() > 0)
      {
         LOG.info("autoParents.size() = " + autoParents.size());
         for (AutoParent autoParent : autoParents)
         {
            LOG.info("Filling the lazy loading...");
            AutoChildTwo autoChildTwo = autoParent.getChildTwo();
            if (autoChildTwo != null)
            {
               LOG.info("autoChildTwo.autoChildTwo() = " + autoChildTwo.getName());
            }
            LOG.info("Done with the lazy loading.");
         }
      }
      else
      {
         LOG.error("No Parent found.");
      }
   }
}
