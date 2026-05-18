package com.learn.transaction.callback;


import org.junit.Test;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.learn.common.TestClientUtils;
import com.learn.util.SpringApplicationContext;


public class CallbackTransactionClient
{
    @Test
    public void runTest()
        throws Exception
    {
        TestClientUtils.addRecord();

        testUpdateTransaction();

        TestClientUtils.queryRecords();
    }

    /////////////////////////////////////////////////////////////////
    /// Callback Transaction Management (Using TransactionTemplate)
    /////////////////////////////////////////////////////////////////
    public void testUpdateTransaction()
        throws Exception
    {
        TransactionTemplate transactionTemplate = SpringApplicationContext.getBean("transactionTemplate");

        TransactionCallback transactionCallback = new TransactionCallbackRollback();
        transactionTemplate.execute(transactionCallback);

        transactionCallback = new TransactionCallbackCommit();
        transactionTemplate.execute(transactionCallback);
    }
}
