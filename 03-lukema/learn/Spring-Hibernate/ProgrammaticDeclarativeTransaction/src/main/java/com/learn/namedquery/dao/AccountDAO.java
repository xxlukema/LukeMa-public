package com.learn.namedquery.dao;


import java.util.List;

import com.learn.namedquery.bean.Account;


public interface AccountDAO
{
    public List<Account> getAccounts();

    public int updateAccount();
}
