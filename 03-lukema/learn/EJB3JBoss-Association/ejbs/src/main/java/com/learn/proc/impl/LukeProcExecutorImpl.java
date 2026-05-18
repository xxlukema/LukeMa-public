package com.learn.proc.impl;


import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.learn.proc.LukeProcExecutor;


public class LukeProcExecutorImpl
   extends StoredProcedure
   implements LukeProcExecutor
{
   protected static final Logger LOG              = Logger.getLogger(LukeProcExecutorImpl.class);

   private static final String   STORED_PROC_NAME = "Luke_Proc";

   public LukeProcExecutorImpl(DataSource dataSource)
   {
      super(dataSource, STORED_PROC_NAME);

      declareParameter(new SqlParameter("ticker", Types.VARCHAR));
      declareParameter(new SqlOutParameter("swapNum", Types.INTEGER));
      declareParameter(new SqlOutParameter("swapId", Types.INTEGER));
      declareParameter(new SqlOutParameter("date", Types.DATE));
      declareParameter(new SqlOutParameter("rate", Types.FLOAT));

      compile();
   }

   @Override
   public Map<String, Object> executeProc(Map<String, Object> inputParams)
   {
      return execute(inputParams);
   }
}
