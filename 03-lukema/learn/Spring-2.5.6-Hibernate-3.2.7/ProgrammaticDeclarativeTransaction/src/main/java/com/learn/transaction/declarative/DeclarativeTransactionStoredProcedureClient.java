package com.learn.transaction.declarative;


import org.apache.log4j.Logger;
import org.junit.Test;

import com.learn.common.TestClientUtils;
import com.learn.service.PersonService;
import com.learn.service.StoredProcedureService;
import com.learn.util.SpringApplicationContext;


public class DeclarativeTransactionStoredProcedureClient
{
    private static final Logger LOG = Logger.getLogger(DeclarativeTransactionStoredProcedureClient.class);

    @Test
    public void runTest()
        throws Exception
    {
        TestClientUtils.addRecord();

        testUpdateTransaction();

        TestClientUtils.queryRecords();
    }

    public void testUpdateTransaction()
        throws Exception
    {
        StoredProcedureService storedProcedureService = SpringApplicationContext
                .getBean("storedProcedureService");

        try
        {
            storedProcedureService.updatePersonCommit(10);
        }
        catch (Throwable e)
        {
            LOG.info("Caught exception: ", e);
        }

        try
        {
            storedProcedureService.updatePersonRollback(20);
        }
        catch (Throwable e)
        {
            LOG.info("Caught exception: ", e);
        }

        // With Hibernate: 
        PersonService personService = SpringApplicationContext.getBean("personService");

        try
        {
            personService.updatePersonCommit("Commit. Show New Name.");
        }
        catch (Throwable e)
        {
            LOG.info("Caught exception: " + e.getMessage());
        }

        try
        {
            personService.updatePersonRollback("Rollback. Not Show.");
        }
        catch (Throwable e)
        {
            LOG.info("Caught exception: " + e.getMessage());
        }

    }

}
