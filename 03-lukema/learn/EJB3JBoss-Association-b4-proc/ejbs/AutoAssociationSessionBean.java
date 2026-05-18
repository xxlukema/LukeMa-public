package com.learn.session.auto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
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
import com.learn.scalor.AutoChildOneResult;
import com.learn.scalor.AutoParentResult;


@Stateless
public class AutoAssociationSessionBean
   implements AutoAssociationSessionBeanLocal, AutoAssociationSessionBeanRemote
{
   private static final long     serialVersionUID        = 1L;

   protected static final Logger LOG                     = Logger.getLogger(AutoAssociationSessionBean.class);

   private int                   numQueries              = 10000;

   private int                   numInserts              = 100;

   protected final String        oqlChildOneByName       = "select autoChildOne from AutoChildOne autoChildOne where autoChildOne.name = ?";

   protected final String        oqlParentByName         = "select autoParent from AutoParent autoParent where autoParent.name like ?";

   protected final String        oqlParentByChildOneName = "select autoParent from AutoParent autoParent join autoParent.childOneChildren childOneChild where childOneChild.name = ?";

   protected final String        oqlChildOneByParentName = "select autoChildOne from AutoChildOne autoChildOne join autoChildOne.parent parent where parent.name = ?";

   protected final String        nqlParentByChildOneName = "select autoParent.id, autoParent.name from Auto_Parent autoParent, Auto_Child_One autoChildOne where autoParent.id = autoChildOne.parent_id and autoChildOne.name = ?";

   protected final String        nqlChildOneByParentName = "select autoChildOne.id, autoChildOne.name from Auto_Parent autoParent, Auto_Child_One autoChildOne where autoParent.id = autoChildOne.parent_id and autoParent.name = ?";

   protected final String        sqlParentByChildOneName = "select autoParent.name from Auto_Parent autoParent, Auto_Child_One autoChildOne where autoParent.id = autoChildOne.parent_id and autoChildOne.name = ?";

   protected final String        sqlChildOneByParentName = "select autoChildOne.name from Auto_Parent autoParent, Auto_Child_One autoChildOne where autoParent.id = autoChildOne.parent_id and autoParent.name = ?";

   @PersistenceContext(unitName = "lab-entity-demo4")
   private EntityManager         entityManager;

   @Resource(name = "jdbc/MySqlDS")
   private DataSource            dataSource;

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

      long start = System.currentTimeMillis();
      for (int i = 0; i < numInserts; i++)
      {
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

      selectForAutoChildOneChild();
      selectAutoParent();

      selectAutoParentForAutoChild();
      selectAutoChildForAutoParent();

      selectAutoParentForAutoChildNative();
      selectAutoChildForAutoParentNative();

      selectAutoParentForAutoChildJDBC();
      selectAutoChildForAutoParentJDBC();

      LOG.info("########## BookSessionBean.retrieveData() completed.");
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

      Query query = entityManager.createQuery(oqlChildOneByName);
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

   /**
    * TODO: Implemet LIKE
    */
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

      Query query = entityManager.createQuery(oqlParentByName);
      query.setParameter(1, "AutoPare%t 1");

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

   protected void selectAutoParentForAutoChild()
      throws Exception
   {
      LOG.info("Inside selectAutoParentForAutoChild() ###");

      long start = System.currentTimeMillis();
      for (int i = 0; i < numQueries; i++)
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
      long end = System.currentTimeMillis();
      LOG.info("Time (seconds): " + (end - start) / 1000);
   }

   protected void selectAutoChildForAutoParent()
      throws Exception
   {
      LOG.info("Inside selectAutoChildForAutoParent() ###");

      long start = System.currentTimeMillis();
      for (int i = 0; i < numQueries; i++)
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
      long end = System.currentTimeMillis();
      LOG.info("Time (seconds): " + (end - start) / 1000);
   }

   protected void selectAutoParentForAutoChildNative()
      throws Exception
   {
      LOG.info("Inside selectAutoParentForAutoChildNative() ###");

      long start = System.currentTimeMillis();
      for (int i = 0; i < numQueries; i++)
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
      long end = System.currentTimeMillis();
      LOG.info("Time (seconds): " + (end - start) / 1000);
   }

   protected void selectAutoChildForAutoParentNative()
      throws Exception
   {
      LOG.info("Inside selectAutoChildForAutoParentNative() ###");

      long start = System.currentTimeMillis();
      for (int i = 0; i < numQueries; i++)
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
      long end = System.currentTimeMillis();
      LOG.info("Time (seconds): " + (end - start) / 1000);
   }

   protected void selectAutoParentForAutoChildJDBC()
      throws Exception
   {
      LOG.info("Inside selectAutoParentForAutoChildJDBC() ###");

      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;

      try
      {
         /*  
          Class.forName("net.sourceforge.jtds.jdbc.Driver");
          String url = "jdbc:jtds:sybase://esssybd1.uk.db.com:5000;DatabaseName=ETS_ESS_TEST";
          conn = DriverManager.getConnection(url, "ess_batch", "ess_batch01");
          */

         /*
         Class.forName("com.mysql.jdbc.Driver");
         String url = "jdbc:mysql://localhost:3306/test";
         conn = DriverManager.getConnection(url, "root", "");
         */

         conn = dataSource.getConnection();

         // Print all warnings
         for (SQLWarning warn = conn.getWarnings(); warn != null; warn = warn.getNextWarning())
         {
            LOG.info("SQL Warning:");
            LOG.info("State  : " + warn.getSQLState());
            LOG.info("Message: " + warn.getMessage());
            LOG.info("Error  : " + warn.getErrorCode());
         }

         long start = System.currentTimeMillis();
         for (int i = 0; i < numQueries; i++)
         {
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

            if (rs != null)
            {
               rs.close();
               rs = null;
            }

            if (stmt != null)
            {
               stmt.close();
               stmt = null;
            }
         }
         long end = System.currentTimeMillis();
         LOG.info("Time (seconds): " + (end - start) / 1000);
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
         if (rs != null && !rs.isClosed())
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

         if (stmt != null && !stmt.isClosed())
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

         if (conn != null && !conn.isClosed())
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
      LOG.info("Inside selectAutoChildForAutoParentJDBC() ###");

      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;

      try
      {
         /*    
          Class.forName("net.sourceforge.jtds.jdbc.Driver");
          String url = "jdbc:jtds:sybase://esssybd1.uk.db.com:5000;DatabaseName=ETS_ESS_TEST";
          conn = DriverManager.getConnection(url, "ess_batch", "ess_batch01");
          */

         /*
         Class.forName("com.mysql.jdbc.Driver");
         String url = "jdbc:mysql://localhost:3306/test";
         conn = DriverManager.getConnection(url, "root", "");
         */

         conn = dataSource.getConnection();

         // Print all warnings
         for (SQLWarning warn = conn.getWarnings(); warn != null; warn = warn.getNextWarning())
         {
            LOG.info("SQL Warning:");
            LOG.info("State  : " + warn.getSQLState());
            LOG.info("Message: " + warn.getMessage());
            LOG.info("Error  : " + warn.getErrorCode());
         }

         long start = System.currentTimeMillis();
         for (int i = 0; i < numQueries; i++)
         {
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

            if (rs != null)
            {
               rs.close();
               rs = null;
            }

            if (stmt != null)
            {
               stmt.close();
               stmt = null;
            }
         }
         long end = System.currentTimeMillis();
         LOG.info("Time (seconds): " + (end - start) / 1000);
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
         if (rs != null && !rs.isClosed())
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

         if (stmt != null && !stmt.isClosed())
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

         if (conn != null && !conn.isClosed())
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
