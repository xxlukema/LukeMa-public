package com.learn.namedquery.bean;


import java.io.Serializable;
import java.util.Date;


public class Account
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Date              cobDate;

    private String            accountNumber;

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setCobDate(Date cobDate)
    {
        this.cobDate = cobDate;
    }

    public Date getCobDate()
    {
        return cobDate;
    }

}
