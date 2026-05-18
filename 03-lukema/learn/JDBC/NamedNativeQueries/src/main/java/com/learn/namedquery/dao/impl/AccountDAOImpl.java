package com.learn.namedquery.dao.impl;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.learn.namedquery.bean.Account;
import com.learn.namedquery.dao.AccountDAO;


public class AccountDAOImpl
    extends HibernateDaoSupport
    implements AccountDAO
{
    protected static final Logger LOG               = Logger.getLogger(AccountDAOImpl.class);

    protected static final String SQL_TARGET_SELECT = "Account.findAccount";

    protected static final String SQL_TARGET_UPDATE = "Account.updateAccount";

    @Override
    public List<Account> getAccounts()
    {
        Query query = getHibernateTemplate().getSessionFactory().openSession()
                .getNamedQuery(SQL_TARGET_SELECT)
                .setResultTransformer(Transformers.aliasToBean(Account.class));

        query.setString("SOURCE_FEED_NAME", "LCH%JPMSL");

        @SuppressWarnings("unchecked")
        List<Account> accounts = query.list();

        return accounts;
    }

    @Override
    public int updateAccount()
    {
        Query query = getHibernateTemplate().getSessionFactory().openSession()
                .getNamedQuery(SQL_TARGET_UPDATE);

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 7);

        query.setDate("UPDATE_DATETIME", gregorianCalendar.getTime());
        query.setString("SOURCE_FEED_NAME", "LCHEMEAIAJPMSL");

        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        gregorianCalendar2.set(Calendar.YEAR, 2011);
        gregorianCalendar2.set(Calendar.MONTH, Calendar.JANUARY);
        gregorianCalendar2.set(Calendar.DAY_OF_MONTH, 26);
        gregorianCalendar2.set(Calendar.HOUR, 0);
        gregorianCalendar2.set(Calendar.MINUTE, 0);
        gregorianCalendar2.set(Calendar.SECOND, 0);

        LOG.info("COB_DATE = " + gregorianCalendar2.getTime());

        query.setDate("COB_DATE", gregorianCalendar2.getTime());
        query.setString("CH_ACCT_NBR", "JPEUCCTEST2B");

        return query.executeUpdate(); //throw new RuntimeException();
    }

}
