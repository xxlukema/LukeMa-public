package com.learn.persistence.dao.impl;


import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


public class AccessAlarmsCleanupStoredProcedure
   extends StoredProcedure
{
   protected static final Logger LOG                           = Logger.getLogger(AccessAlarmsCleanupStoredProcedure.class);

   private static final String   STORED_PROC_NAME              = "Access_Alarms_Cleanup_Proc";

   private static final String   AccessAlarmsCleanuoParamName  = "RowNumToKeep";

   private static final int      AccessAlarmsCleanuoParamValue = 10 * 1000;

   public AccessAlarmsCleanupStoredProcedure(DataSource dataSource)
   {
      super(dataSource, STORED_PROC_NAME);

      declareParameter(new SqlParameter(AccessAlarmsCleanuoParamName, Types.INTEGER));

      compile();
   }

   public void execute()
   {
      Map<String, Object> inParams = new HashMap<String, Object>();
      inParams.put(AccessAlarmsCleanuoParamName, AccessAlarmsCleanuoParamValue);

      execute(inParams);
   }

}
