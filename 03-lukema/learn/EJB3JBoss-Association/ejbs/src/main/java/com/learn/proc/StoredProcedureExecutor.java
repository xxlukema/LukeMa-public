package com.learn.proc;


import java.util.Map;


public interface StoredProcedureExecutor
{
   public Map<String, Object> executeProc(Map<String, Object> inputParams);
}
