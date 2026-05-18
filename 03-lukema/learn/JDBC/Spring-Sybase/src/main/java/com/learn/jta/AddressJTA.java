package com.learn.jta;

import java.util.List;

import com.learn.bean.Address;


public interface AddressJTA
{
   public static final String SQL = "select ADDRESS_ID, PROVINCE, COUNTRY, P_CODE, STREET, CITY from Address where ADDRESS_ID > ? ";
   
   public List<Address> list()
      throws Exception;
}

