package com.learn;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tangosol.net.cache.CacheStore;
import com.tangosol.util.Base;


public class DBCacheStore
   extends Base
   implements CacheStore
{
   protected static final Logger LOG = Logger.getLogger(DBCacheStore.class);

   protected Connection          conn;

   @BeforeClass
   public static void beforeClass()
      throws Exception
   {
      LOG.info("beforeClass(). Once for the class.");
      Class.forName("com.mysql.jdbc.Driver");
   }

   @Before
   public void before()
      throws Exception
   {
      LOG.info("before(). For each test.");

      String url = "jdbc:mysql://localhost:3306/test";

      conn = DriverManager.getConnection(url, "root", "");
   }

   @After
   public void after()
      throws Exception
   {
      LOG.info("after(). For each test.");

      conn.close();
   }

   @Test
   public void doTest()
   {
      Integer id = 101;
      String name = "Ess Test";

      erase(id);

      store(id, name);
      Object value = load(id);
      Assert.assertEquals(name, value);

      erase(id);
      value = load(id);
      Assert.assertNull(value);
   }

   @Override
   public Object load(Object oKey)
   {
      PreparedStatement stmt = null;
      ResultSet resultSet = null;

      Object oValue = null;

      String sSQL = "SELECT id, name FROM customers WHERE id = ?";

      try
      {
         Integer id = (Integer) oKey;

         stmt = conn.prepareStatement(sSQL);

         stmt.setInt(1, id);

         resultSet = stmt.executeQuery();
         if (resultSet.next())
         {
            oValue = resultSet.getString(2);
            LOG.info("name = " + oValue);
         }
      }
      catch (Exception e)
      {
         LOG.error("Exception loading object.", e);
      }
      finally
      {
         try
         {
            resultSet.close();
         }
         catch (SQLException e)
         {
            LOG.error("Exception closing ResultSet", e);
         }

         try
         {
            stmt.close();
         }
         catch (SQLException e)
         {
            LOG.error("Exception closing Statement", e);
         }
      }

      return oValue;
   }

   @Override
   public void store(Object oKey, Object oValue)
   {
      PreparedStatement stmt = null;
      String sSQL;

      if (load(oKey) != null)
      {
         sSQL = "UPDATE customers SET name = ? where id = ?";
      }
      else
      {
         sSQL = "INSERT INTO customers (name, id) VALUES (?, ?)";
      }

      try
      {
         Integer id = (Integer) oKey;

         stmt = conn.prepareStatement(sSQL);
         stmt.setString(1, String.valueOf(oValue));
         stmt.setInt(2, id);
         stmt.executeUpdate();
         stmt.close();
      }
      catch (Exception e)
      {
         LOG.error("Exception loading object.", e);
      }
      finally
      {
         try
         {
            stmt.close();
         }
         catch (SQLException e)
         {
            LOG.error("Exception closing Statement", e);
         }
      }
   }

   @Override
   public void erase(Object oKey)
   {
      PreparedStatement stmt = null;
      String sSQL = "DELETE FROM customers WHERE id=?";
      try
      {
         Integer id = (Integer) oKey;

         stmt = conn.prepareStatement(sSQL);

         stmt.setInt(1, id);
         stmt.executeUpdate();
         stmt.close();
      }
      catch (Exception e)
      {
         LOG.error("Exception loading object.", e);
      }
      finally
      {
         try
         {
            stmt.close();
         }
         catch (SQLException e)
         {
            LOG.error("Exception closing Statement", e);
         }
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public void eraseAll(Collection arg0)
   {
      throw new UnsupportedOperationException();

   }

   @SuppressWarnings("unchecked")
   @Override
   public void storeAll(Map arg0)
   {
      throw new UnsupportedOperationException();

   }

   @SuppressWarnings("unchecked")
   @Override
   public Map loadAll(Collection arg0)
   {
      throw new UnsupportedOperationException();
   }

}
