package com.learn.namedquery.service.impl;


import java.util.List;

import org.apache.log4j.Logger;

import com.learn.namedquery.bean.Account;
import com.learn.namedquery.dao.AccountDAO;
import com.learn.namedquery.service.AccountService;


public class AccountServiceImpl
    implements AccountService
{
    protected static final Logger LOG = Logger.getLogger(AccountServiceImpl.class);

    private AccountDAO            accountDAO;

    public AccountDAO getAccountDAO()
    {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    @Override
    public List<Account> getAccounts()
    {
        return accountDAO.getAccounts();
    }

    @Override
    public int updateAccount()
    {
        return accountDAO.updateAccount(); // throw new RuntimeException();
    }

}
