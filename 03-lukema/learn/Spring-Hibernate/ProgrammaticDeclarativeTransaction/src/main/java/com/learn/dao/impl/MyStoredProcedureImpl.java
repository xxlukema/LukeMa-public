package com.learn.dao.impl;


import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.learn.common.TestClientUtils;
import com.learn.dao.MyStoredProcedure;


public class MyStoredProcedureImpl
   extends StoredProcedure
   implements MyStoredProcedure
{
   private static final Logger LOG              = Logger.getLogger(MyStoredProcedureImpl.class);

   private static final String STORED_PROC_NAME = "update_weight";

   public MyStoredProcedureImpl(DataSource dataSource)
   {
      super(dataSource, STORED_PROC_NAME);

      declareParameter(new SqlParameter("v_name", Types.VARCHAR));
      declareParameter(new SqlInOutParameter("v_weight", Types.FLOAT));

      compile();
   }

   public void execute(float weight)
   {
      String name = TestClientUtils.LUKE;

      Map<String, Object> inParams = new HashMap<String, Object>();
      inParams.put("v_name", name);
      inParams.put("v_weight", weight);

      @SuppressWarnings("unchecked")
      Map<String, Object> outParams = execute(inParams);

      double outWeight = (Double) outParams.get("v_weight");
      weight = (float) outWeight;

      LOG.info("New weight: " + weight);
   }

}
