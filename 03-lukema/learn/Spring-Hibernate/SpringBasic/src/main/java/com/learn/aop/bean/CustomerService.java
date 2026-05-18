package com.learn.aop.bean;


public interface CustomerService {
    void addCustomer();

    String addCustomerReturnValue();

    void addCustomerThrowException()
        throws Exception;

    void addCustomerAround(String name);

    String logAnnotationAround(String name);
}
