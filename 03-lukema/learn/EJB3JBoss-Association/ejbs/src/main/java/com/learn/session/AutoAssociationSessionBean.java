package com.learn.session;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.learn.entity.AutoChildOne;
import com.learn.entity.AutoChildOneChild;
import com.learn.entity.AutoChildTwo;
import com.learn.entity.AutoParent;
import com.learn.entity.LukeTest;
import com.learn.scalor.AutoChildOneResult;
import com.learn.scalor.AutoParentResult;


@Stateless
public class AutoAssociationSessionBean
   implements AutoAssociationSessionBeanLocal, AutoAssociationSessionBeanRemote
{
   private static final long     serialVersionUID        = 1L;

   protected static final Logger LOG                     = Logger.getLogger(AutoAssociationSessionBean.class);

   private int                   numQueries              = 1;

   private int                   numInserts              = 2;

   private final String          oqlParentByChildOneName = "select autoParent from AutoParent autoParent join autoParent.childOneChildren childOneChild where childOneChild.name = ?";

   private final String          oqlChildOneByParentName = "select autoChildOne from AutoChildOne autoChildOne join autoChildOne.parent parent where parent.name = ?";

   private final String          nqlParentByChildOneName = "select autoParent.id, autoParent.name from Auto_Parent autoParent, Auto_Child_One autoChildOne where autoParent.id = autoChildOne.parent_id and autoChildOne.name = ?";

   private final String          nqlChildOneByParentName = "select autoChildOne.id, autoChildOne.name from Auto_Parent autoParent, Auto_Child_One autoChildOne where autoParent.id = autoChildOne.parent_id and autoParent.name = ?";

   private final String          sqlParentByChildOneName = "select autoParent.name from Auto_Parent autoParent, Auto_Child_One autoChildOne where autoParent.id = autoChildOne.parent_id and autoChildOne.name = ?";

   private final String          sqlChildOneByParentName = "select autoChildOne.name from Auto_Parent autoParent, Auto_Child_One autoChildOne where autoParent.id = autoChildOne.parent_id and autoParent.name = ?";

   @PersistenceContext(unitName = "entity-persistence-unit")
   private EntityManager         entityManager;

   //@Resource(mappedName = "java:MySqlDS")
   @Resource(mappedName = "java:MySybase")
   private DataSource            dataSource;

   @Override
   public void addRecord()
      throws Exception
   {
      LOG.info("########## BookSessionBean.addRecord() invoked.");

      long start = System.currentTimeMillis();
      for (int i = 0; i < numInserts; i++)
      {
         insertData();
      }
      long end = System.currentTimeMillis();
      LOG.info("Time (seconds): " + (end - start) / 1000);
      LOG.info("Data Initiated.");
   }

   @Override
   public void retrieveData()
      throws Exception
   {
      LOG.info("########## BookSessionBean.retrieveData() invoked.");

      // OQL
      LOG.info("Calling selectAutoParentForAutoChild() ###");
      long start = System.currentTimeMillis();
      for (int i = 0; i < numQueries; i++)
      {
         selectAutoParentForAutoChild();
      }
      long end = System.currentTimeMillis();
      LOG.info("Time (seconds): " + (end - start) / 1000);

      LOG.info("Calling selectAutoChildForAutoParent() ###");
      start = System.currentTimeMillis();
      for (int i = 0; i < numQueries; i++)
      {
         selectAutoChildForAutoParent();
      }
      end = System.currentTimeMillis();
      LOG.info("Time (seconds): " + (end - start) / 1000);

      // Native
      LOG.info("Calling selectAutoParentForAutoChildNative() ###");
      start = System.currentTimeMillis();
      for (int i = 0; i < numQueries; i++)
      {
         selectAutoParentForAutoChildNative();
      }
      end = System.currentTimeMillis();
      LOG.info("Time (seconds): " + (end - start) / 1000);

      LOG.info("Calling selectAutoChildForAutoParentNative() ###");
      start = System.currentTimeMillis();
      for (int i = 0; i < numQueries; i++)
      {
         selectAutoChildForAutoParentNative();
      }
      end = System.currentTimeMillis();
      LOG.info("Time (seconds): " + (end - start) / 1000);

      // JDBC
      LOG.info("Calling selectAutoParentForAutoChildJDBC() ###");
      start = System.currentTimeMillis();
      for (int i = 0; i < numQueries; i++)
      {
         selectAutoParentForAutoChildJDBC();
      }
      end = System.currentTimeMillis();
      LOG.info("Time (seconds): " + (end - start) / 1000);

      LOG.info("Calling selectAutoChildForAutoParentJDBC() ###");
      start = System.currentTimeMillis();
      for (int i = 0; i < numQueries; i++)
      {
         selectAutoChildForAutoParentJDBC();
      }
      end = System.currentTimeMillis();
      LOG.info("Time (seconds): " + (end - start) / 1000);

      //LOG.info("Calling callStoredProcNative() ###");
      //start = System.currentTimeMillis();
      //callStoredProcNative();
      //end = System.currentTimeMillis();
      //LOG.info("Time (seconds): " + (end - start) / 1000);

      LOG.info("########## BookSessionBean.retrieveData() completed.");
   }

   protected void insertData()
      throws Exception
   {
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

      // Parent1: 3 AutoChildOne + 1 AutoChildTwo. 
      AutoParent parent1 = new AutoParent();
      parent1 = entityManager.merge(parent1);

      // AutoChildOne 1: 1 AutoChildOneChild
      AutoChildOne childOne1 = new AutoChildOne();
      childOne1.setParent(parent1);
      entityManager.merge(childOne1);

      AutoChildOne childOne2 = new AutoChildOne();
      childOne2.setParent(parent1);
      childOne2 = entityManager.merge(childOne2);

      AutoChildOne childOne3 = new AutoChildOne();
      childOne3.setParent(parent1);
      entityManager.merge(childOne3);

      AutoChildTwo childTwo1 = new AutoChildTwo();
      parent1.setChildTwo(childTwo1);
      parent1 = entityManager.merge(parent1);

      // AutoChildOne 1: 1 AutoChildOneChild
      AutoChildOneChild childOneChild1 = new AutoChildOneChild();
      childOne2.setChild(childOneChild1);
      entityManager.merge(childOne2);

      // Parent 2: 1 AutoChildOne.
      AutoParent parent2 = new AutoParent();
      parent2 = entityManager.merge(parent2);

      AutoChildOne childOne4 = new AutoChildOne();
      childOne4.setParent(parent2);
      entityManager.merge(childOne4);

      // Parent 3: No Child
      AutoParent parent3 = new AutoParent();
      entityManager.merge(parent3);

      // Luke_Test
      LukeTest lukeTest = new LukeTest();
      lukeTest.setName("Luke X. Ma");
      lukeTest.setNum(1);
      entityManager.merge(lukeTest);
   }

   protected void selectAutoParentForAutoChild()
      throws Exception
   {
      Query query = entityManager.createQuery(oqlParentByChildOneName);
      query.setParameter(1, "AutoChildOne 1");

      @SuppressWarnings("unchecked")
      List<AutoParent> autoParents = query.getResultList();

      //    LOG.error("autoParents.size() = " + autoParents.size());

      AutoParent autoParent = autoParents.get(0);

      //    LOG.info("autoParent.getName() = " + autoParent.getName());
      autoParent.getName();

      //     for (AutoChildOne childOne : autoParent.getChildOneChildren())
      //     {
      //   LOG.info("   childOne.getName() = " + childOne.getName());
      //        childOne.getName();
      //     }

      autoParents.clear();
      autoParents = null;
   }

   protected void selectAutoChildForAutoParent()
      throws Exception
   {
      Query query = entityManager.createQuery(oqlChildOneByParentName);
      query.setParameter(1, "AutoParent 1");

      @SuppressWarnings("unchecked")
      List<AutoChildOne> autoChildOnes = query.getResultList();

      //    LOG.info("autoChildOnes.size() = " + autoChildOnes.size());

      //       AutoChildOne autoChildOne = autoChildOnes.get(0);

      //      LOG.info("Filling the lazy loading...");
      //       AutoParent autoParent = autoChildOne.getParent();
      //      LOG.info("autoParent.getName() = " + autoParent.getName());
      //       autoParent.getName();

      for (AutoChildOne childOne : autoChildOnes)
      {
         //      LOG.info("   childOne.getName() = " + childOne.getName());
         childOne.getName();
      }

      //  LOG.info("Done with the lazy loading.");

      autoChildOnes.clear();
      autoChildOnes = null;
   }

   protected void selectAutoParentForAutoChildNative()
      throws Exception
   {
      Query query = entityManager.createNativeQuery(nqlParentByChildOneName, AutoParentResult.class);
      query.setParameter(1, "AutoChildOne 1");

      @SuppressWarnings("unchecked")
      List<AutoParentResult> autoParentResults = query.getResultList();

      //   LOG.info("autoParentResults.size() = " + autoParentResults.size());

      //     for (AutoParentResult autoParentResult : autoParentResults)
      //      {
      //     LOG.info("autoParentResult.getName() = " + autoParentResult.getName());
      //       autoParentResult.getName();
      //     }

      autoParentResults.clear();
      autoParentResults = null;
   }

   protected void selectAutoChildForAutoParentNative()
      throws Exception
   {
      Query query = entityManager.createNativeQuery(nqlChildOneByParentName, AutoChildOneResult.class);
      query.setParameter(1, "AutoParent 1");

      @SuppressWarnings("unchecked")
      List<AutoChildOneResult> autoChildOneResults = query.getResultList();

      //    LOG.info("autoChildOneResults.size() = " + autoChildOneResults.size());

      for (AutoChildOneResult autoChildOneResult : autoChildOneResults)
      {
         //        LOG.info("autoChildOneResult.getName() = " + autoChildOneResult.getName());
         autoChildOneResult.getName();
      }

      autoChildOneResults.clear();
      autoChildOneResults = null;
   }

   /*
   protected void callStoredProcNative()
      throws Exception
   {
      Query query = entityManager.createNamedQuery("Call_Luke_Proc");
      query.setParameter("ticker", ".CSI200UT");
      query.setParameter("swapNum", 1);
      query.setParameter("swapId", 1);
      query.setParameter("date", new Date());
      query.setParameter("rate", 0.0f);

      @SuppressWarnings("unchecked")
      List<LukeProcResult> lukeProcResults = query.getResultList();

      LOG.info("lukeProcResults.size() = " + lukeProcResults.size());

      for (LukeProcResult lukeProcResult : lukeProcResults)
      {
         LOG.info("ticker: " + lukeProcResult.getTicker() + ", swapNum: " + lukeProcResult.getSwapNum() + ", swapId: " + lukeProcResult.getSwapId()
               + ", date: " + lukeProcResult.getDate() + ", rate: " + lukeProcResult.getRate());
      }

      lukeProcResults.clear();
      lukeProcResults = null;
   }
   */

   protected void selectAutoParentForAutoChildJDBC()
      throws Exception
   {
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;

      try
      {
         conn = dataSource.getConnection();
         stmt = conn.prepareStatement(sqlParentByChildOneName);
         stmt.setString(1, "AutoChildOne 1");

         // Execute the query
         rs = stmt.executeQuery();

         // Loop through the result set
         while (rs.next())
         {
            //  LOG.info("name = " + rs.getString(1));
            rs.getString(1);
         }
      }
      catch (SQLException se)
      {
         LOG.info("SQL Exception:", se);

         // Loop through the SQL Exceptions
         while (se != null)
         {
            LOG.info("State  : " + se.getSQLState());
            LOG.info("Message: " + se.getMessage());
            LOG.info("Error  : " + se.getErrorCode());

            se = se.getNextException();
         }
      }
      catch (Exception e)
      {
         LOG.info("Exception:", e);
      }
      finally
      {
         if (rs != null)
         {
            try
            {
               rs.close();
            }
            catch (SQLException e)
            {
               LOG.info("SQL Exception:", e);
            }
         }

         if (stmt != null)
         {
            try
            {
               stmt.close();
            }
            catch (SQLException e)
            {
               LOG.info("SQL Exception:", e);
            }
         }

         if (conn != null)
         {
            try
            {
               conn.close();
            }
            catch (SQLException e)
            {
               LOG.info("SQL Exception:", e);
            }
         }
      }
   }

   protected void selectAutoChildForAutoParentJDBC()
      throws Exception
   {
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;

      try
      {
         conn = dataSource.getConnection();
         stmt = conn.prepareStatement(sqlChildOneByParentName);
         stmt.setString(1, "AutoParent 1");

         // Execute the query
         rs = stmt.executeQuery();

         // Loop through the result set
         while (rs.next())
         {
            //        LOG.info("name = " + rs.getString(1));
            rs.getString(1);
         }
      }
      catch (SQLException se)
      {
         LOG.info("SQL Exception:", se);

         // Loop through the SQL Exceptions
         while (se != null)
         {
            LOG.info("State  : " + se.getSQLState());
            LOG.info("Message: " + se.getMessage());
            LOG.info("Error  : " + se.getErrorCode());

            se = se.getNextException();
         }
      }
      catch (Exception e)
      {
         LOG.info("Exception:", e);
      }
      finally
      {
         if (rs != null)
         {
            try
            {
               rs.close();
            }
            catch (SQLException e)
            {
               LOG.info("SQL Exception:", e);
            }
         }

         if (stmt != null)
         {
            try
            {
               stmt.close();
            }
            catch (SQLException e)
            {
               LOG.info("SQL Exception:", e);
            }
         }

         if (conn != null)
         {
            try
            {
               conn.close();
            }
            catch (SQLException e)
            {
               LOG.info("SQL Exception:", e);
            }
         }
      }
   }

}
