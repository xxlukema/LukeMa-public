package com.learn.service.impl;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.learn.bean.Customer;
import com.learn.bean.Portfolio;
import com.learn.bean.PortfolioItem;
import com.learn.dao.CustomerDAO;
import com.learn.service.AppException;
import com.learn.service.CustomerService;
import com.learn.util.StringConstants;


@Service("customerService")
public class CustomerServiceImpl
    implements CustomerService {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    @Qualifier("customerDAO")
    private CustomerDAO customerDAO;

    public Customer getCustomer(String username)
        throws AppException {
        LOG.info("Entering function.");

        Customer customer = customerDAO.getCustomerByUsername(username);

        if (customer == null) {
            initCustomer();
            customer = customerDAO.getCustomerByUsername(username);
            if (customer == null) {
                throw new RuntimeException("Unable to find customer.");
            }
        }

        return customer;
    }

    private void initCustomer() {
        LOG.info("Entering function.");

        Customer newCustomer = new Customer();

        newCustomer.setUsername(StringConstants.GUEST_USERNAME);
        newCustomer.setPassword(StringConstants.GUEST_PASSWORD);

        Portfolio portfolio = new Portfolio();
        newCustomer.setPortfolio(portfolio);

        portfolio.setCustomer(newCustomer);

        PortfolioItem ibm = new PortfolioItem();
        ibm.setSymbol("IBM");
        ibm.setShares(50);

        PortfolioItem sun = new PortfolioItem();
        sun.setSymbol("UAL");
        sun.setShares(300);

        PortfolioItem dell = new PortfolioItem();
        dell.setSymbol("LVS");
        dell.setShares(200);

        portfolio.setCash(1000.0F);

        ibm.setPortfolio(portfolio);
        sun.setPortfolio(portfolio);
        dell.setPortfolio(portfolio);

        portfolio.getPortfolioItems().add(ibm);
        portfolio.getPortfolioItems().add(sun);
        portfolio.getPortfolioItems().add(dell);

        customerDAO.saveOrUpdate(ibm);
        customerDAO.saveOrUpdate(sun);
        customerDAO.saveOrUpdate(dell);

        customerDAO.saveOrUpdate(portfolio);

        customerDAO.saveOrUpdate(newCustomer);

        LOG.info("initCustomer() completed.");
    }

}
