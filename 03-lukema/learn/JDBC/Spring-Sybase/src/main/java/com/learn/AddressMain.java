package com.learn;


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.learn.bean.Address;
import com.learn.jdbc.AddressJDBC;
import com.learn.jta.AddressJTA;


public class AddressMain
{
   protected static final Logger LOG = Logger.getLogger(AddressMain.class);

   @Test
   public void jtaQueryPerson()
      throws Exception
   {
      Resource resource = new ClassPathResource("jta.xml");
      BeanFactory beanFactory = new XmlBeanFactory(resource);

      // JTA
      AddressJTA addressJTA = (AddressJTA) (beanFactory.getBean("addressJTA"));

      List<Address> addresses = addressJTA.list();
      Assert.assertTrue(addresses.size() > 0);
      LOG.info("addresses.size() = " + addresses.size());

      for (Address address : addresses)
      {
         LOG.info("Address = " + address);
      }

   }

   @Test
   public void jdbcQueryPerson()
      throws Exception
   {
      Resource resource = new ClassPathResource("jta.xml");
      BeanFactory beanFactory = new XmlBeanFactory(resource);

      // JDBC
      AddressJDBC addressJDBC = (AddressJDBC) (beanFactory.getBean("addressJDBC"));

      List<Address> addresses = addressJDBC.list();
      Assert.assertTrue(addresses.size() > 0);
      LOG.info("addresses.size() = " + addresses.size());

      for (Address address : addresses)
      {
         LOG.info("Address = " + address);
      }

   }
}
