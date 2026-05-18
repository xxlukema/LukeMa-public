package com.learn.aop.bean.impl;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.learn.aop.aspect.annotation.MyAspect;
import com.learn.aop.bean.CustomerService;


@Service("customerService")
public class CustomerServiceImpl
    implements CustomerService {

    private static final Logger LOG = Logger.getLogger(CustomerServiceImpl.class);

    public void addCustomer() {
        LOG.info("addCustomer() is running ");
    }

    public String addCustomerReturnValue() {
        LOG.info("addCustomerReturnValue() is running ");
        return "Retun value: abc";
    }

    public void addCustomerThrowException()
        throws Exception {
        LOG.info("addCustomerThrowException() is running ");
        throw new Exception("Generic Error");
    }

    public void addCustomerAround(String name) {
        LOG.info("addCustomerAround() is running, args : " + name);
    }

    @MyAspect
    public String logAnnotationAround(String name) {
        LOG.info("logAnnotationAround() is running, args : " + name);

        if (name == null) {
            return null;
        } else {
            return name.toUpperCase();
        }
    }
}
