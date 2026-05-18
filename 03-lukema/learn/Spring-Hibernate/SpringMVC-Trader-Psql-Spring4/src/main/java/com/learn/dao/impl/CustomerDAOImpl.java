package com.learn.dao.impl;


import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.learn.bean.Customer;
import com.learn.bean.Portfolio;
import com.learn.bean.PortfolioItem;
import com.learn.dao.CustomerDAO;


@Repository("customerDAO")
public class CustomerDAOImpl
    extends CommonDAOImpl
    implements CustomerDAO {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public Customer getCustomerByUsername(String username) {
        LOG.info("Entering function.");

        LOG.info("Do getHibernateTemplate().findByExample(customer).");

        @SuppressWarnings("unchecked")

        List<Customer> list = sessionFactory.getCurrentSession().createQuery("from Customer where username =:username ")
                .setParameter("username", username, StringType.INSTANCE).list();

        LOG.info("list.size() = " + list.size());

        if (list.size() == 1) {
            Customer customer = list.get(0);

            // Touch the lazy initializations.
            /**
             * TODO 
             * 
             * Make it work for OpenSessionInViewFilter
             * Now: Use "Touch the lazy initializations" as an alternative for OpenSessionInViewFilter.
             */

            LOG.info("customer.getId() = " + customer.getId());
            LOG.info("customer.getUsername() = " + customer.getUsername());

            Portfolio portfolio = customer.getPortfolio();
            List<PortfolioItem> portfolioItems = portfolio.getPortfolioItems();
            portfolioItems.size();

            LOG.info("portfolioItems.size() = " + portfolioItems.size());

            return customer;
        }

        if (list.size() > 1) {
            LOG.error("More than one Customer for name = " + username);
        }

        LOG.info("No customer found. Leaving function.");

        return null;
    }
}
