package com.learn;


import org.apache.log4j.Logger;

import com.learn.dao.CustomerDAO;
import com.learn.daoFactory.DAOFactory;
import com.learn.daoFactory.DAOFactoryType;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
   {
      LOG.info("Hello World!");

      DAOFactory mySqlDAOFactory = DAOFactory.getDAOFactory(DAOFactoryType.MySqlDAOFactory);

      CustomerDAO mySqlCustomerDAO = mySqlDAOFactory.getCustomerDAO();

      mySqlCustomerDAO.insert();
   }
}
