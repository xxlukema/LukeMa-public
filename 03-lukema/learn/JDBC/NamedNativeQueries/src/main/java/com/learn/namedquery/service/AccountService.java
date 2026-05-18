package com.learn.namedquery.service;


import java.util.List;

import com.learn.namedquery.bean.Account;


public interface AccountService
{
    public List<Account> getAccounts();

    public int updateAccount();
}
