package com.learn.namedquery;


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import com.learn.namedquery.bean.Account;
import com.learn.namedquery.service.AccountService;
import com.learn.util.SpringApplicationContext;


public class NamedQueryTest
{
    protected static final Logger LOG = Logger.getLogger(NamedQueryTest.class);

    @Test
    public void testUpdate()
        throws Exception
    {
        LOG.info("Hello World!");

        AccountService accountService = SpringApplicationContext.getBean("accountService");

        int rows = accountService.updateAccount();

        LOG.info("rows = " + rows);
    }

    @Ignore
    @Test
    public void testSelect()
        throws Exception
    {
        LOG.info("Hello World!");

        AccountService accountService = SpringApplicationContext.getBean("accountService");

        List<Account> accounts = accountService.getAccounts();
        for (Account account : accounts)
        {
            LOG.info(account.getCobDate() + ": " + account.getAccountNumber());
        }

        LOG.info("accounts.size() = " + accounts.size());
    }

    /*
    @Ignore
    @Test
    public void testUpdate()
        throws Exception
    {
        LOG.info("Hello World!");

        AccountDAO accountDAO = SpringApplicationContext.getBean("accountDAO");

        int rows = accountDAO.updateAccount();

        LOG.info("rows = " + rows);
    }

    @Ignore
    @Test
    public void testSelect()
        throws Exception
    {
        LOG.info("Hello World!");

        AccountDAO accountDAO = SpringApplicationContext.getBean("accountDAO");

        List<Account> accounts = accountDAO.getAccounts();
        for (Account account : accounts)
        {
            LOG.info(account.getCobDate() + ": " + account.getAccountNumber());
        }

        LOG.info("accounts.size() = " + accounts.size());
    }
    */
}
