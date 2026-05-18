package com.learn.transaction.declarative;


import org.apache.log4j.Logger;
import org.junit.Test;

import com.learn.common.TestClientUtils;
import com.learn.service.PersonService;
import com.learn.util.SpringApplicationContext;


public class DeclarativeTransactionClient
{
    private static final Logger LOG = Logger.getLogger(DeclarativeTransactionClient.class);

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
