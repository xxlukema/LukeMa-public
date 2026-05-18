package com.learn.service;


import com.learn.bean.BeanBase;


public interface MyService {
    public void list()
        throws Exception;

    /**
     * Throws AppException to cause automatic roll back. See interceptor config.
     */
    public void saveObjects(BeanBase oracleObject, BeanBase mySQLObject)
        throws Exception;

    /**
     * Throws Exception will NOT cause automatic roll back. See interceptor config.
     */
    public void saveObjectsRollback(BeanBase oracleObject, BeanBase mySQLObject)
        throws Exception;

}
