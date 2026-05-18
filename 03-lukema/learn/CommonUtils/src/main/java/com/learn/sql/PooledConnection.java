/*package com.learn.sql;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;

import org.apache.log4j.Logger;


final class PooledConnection
implements Connection
{
   private static final Logger LOG  = Logger.getLogger(PooledConnection.class);
   private ConnectionPoolManager connectionPoolManager = null;
   private Connection conn = null;
   private boolean isClosed = false;

   protected PooledConnection(Connection conn, ConnectionPoolManager connectionPoolManager)
   {
      this.conn = conn;
      this.connectionPoolManager = connectionPoolManager;
   }

   public void close()
   throws SQLException
   {
      if (conn != null)
      {
         try
         {
            if (!conn.isClosed())
            {
               connectionPoolManager.reuseConnection(conn);
               conn = null;
            }
         }
         catch (Throwable th)
         {
            LOG.error("Unable to close the Connection: "+th.getMessage());
            th.printStackTrace();
         }
      }

      isClosed = true;
   }

   void closeAndDumpThisConnection()
   throws SQLException
   {
      SQLResourceManager.close(conn, "Exception closing sql Connection.");
      connectionPoolManager = null;

      isClosed = true;
   }

   public boolean isClosed()
   throws SQLException
   {
      return isClosed;
   }

   private Connection getConn()
   throws SQLException
   {
      if (isClosed)
      {
         throw new SQLException("[Microsoft][SQLServer 2000 Driver for JDBC]Object has been closed.");
      }

      return conn;
   }

   public Statement createStatement()
   throws SQLException
   {
      return getConn().createStatement();
   }

   public PreparedStatement prepareStatement(String sql)
   throws SQLException
   {
      return getConn().prepareStatement(sql);
   }

   public CallableStatement prepareCall(String sql)
   throws SQLException
   {
      return getConn().prepareCall(sql);
   }

   public String nativeSQL(String sql)
   throws SQLException
   {
      return getConn().nativeSQL(sql);
   }

   public void setAutoCommit(boolean autoCommit)
   throws SQLException
   {
      getConn().setAutoCommit(autoCommit);
   }

   public boolean getAutoCommit()
   throws SQLException
   {
      return getConn().getAutoCommit();
   }

   public void commit()
   throws SQLException
   {
      getConn().commit();
   }

   public void rollback()
   throws SQLException
   {
      getConn().rollback();
   }

   public DatabaseMetaData getMetaData()
   throws SQLException
   {
      return getConn().getMetaData();
   }

   public void setReadOnly(boolean readOnly)
   throws SQLException
   {
      getConn().setReadOnly(readOnly);
   }

   public boolean isReadOnly()
   throws SQLException
   {
      return getConn().isReadOnly();
   }

   public void setCatalog(String catalog)
   throws SQLException
   {
      getConn().setCatalog(catalog);
   }

   public String getCatalog()
   throws SQLException
   {
      return getConn().getCatalog();
   }

   public void setTransactionIsolation(int level)
   throws SQLException
   {
      getConn().setTransactionIsolation(level);
   }

   public int getTransactionIsolation()
   throws SQLException
   {
      return getConn().getTransactionIsolation();
   }

   public SQLWarning getWarnings()
   throws SQLException
   {
      return getConn().getWarnings();
   }

   public void clearWarnings()
   throws SQLException
   {
      getConn().clearWarnings();
   }

   public Statement createStatement(int resultSetType,
                                    int resultSetConcurrency)
   throws SQLException
   {
      return getConn().createStatement(resultSetType, resultSetConcurrency);
   }

   public PreparedStatement prepareStatement(String sql,
                                             int resultSetType,
                                             int resultSetConcurrency)
   throws SQLException
   {
      return getConn().prepareStatement(sql, resultSetType, resultSetConcurrency);
   }

   public CallableStatement prepareCall(String sql,
                                        int resultSetType,
                                        int resultSetConcurrency)
   throws SQLException
   {
      return getConn().prepareCall(sql, resultSetType, resultSetConcurrency);
   }

   public Map<String,Class<?>> getTypeMap()
   throws SQLException
   {
      return getConn().getTypeMap();
   }

   public void setTypeMap(Map<String,Class<?>> map)
   throws SQLException
   {
      getConn().setTypeMap(map);
   }

   public void setHoldability(int holdability)
   throws SQLException
   {
      getConn().setHoldability(holdability);
   }

   public int getHoldability()
   throws SQLException
   {
      return getConn().getHoldability();
   }

   public Savepoint setSavepoint()
   throws SQLException
   {
      return getConn().setSavepoint();
   }

   public Savepoint setSavepoint(String name)
   throws SQLException
   {
      return getConn().setSavepoint(name);
   }

   public void rollback(Savepoint savepoint)
   throws SQLException
   {
      getConn().rollback(savepoint);
   }

   public void releaseSavepoint(Savepoint savepoint)
   throws SQLException
   {
      getConn().releaseSavepoint(savepoint);
   }

   public Statement createStatement(int resultSetType,
                                    int resultSetConcurrency,
                                    int resultSetHoldability)
   throws SQLException
   {
      return getConn().createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
   }

   public PreparedStatement prepareStatement(String sql,
                                             int resultSetType,
                                             int resultSetConcurrency,
                                             int resultSetHoldability)
   throws SQLException
   {
      return getConn().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
   }

   public CallableStatement prepareCall(String sql,
                                        int resultSetType,
                                        int resultSetConcurrency,
                                        int resultSetHoldability)
   throws SQLException
   {
      return getConn().prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
   }

   public PreparedStatement prepareStatement(String sql,
                                             int autoGeneratedKeys)
   throws SQLException
   {
      return getConn().prepareStatement(sql, autoGeneratedKeys);
   }

   public PreparedStatement prepareStatement(String sql,
                                             int[] columnIndexes)
   throws SQLException
   {
      return getConn().prepareStatement(sql, columnIndexes);
   }

   public PreparedStatement prepareStatement(String sql,
                                             String[] columnNames)
   throws SQLException
   {
      return getConn().prepareStatement(sql, columnNames);
   }
}

*/