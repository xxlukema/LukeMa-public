package com.learn;


import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.learn.hibernate.HibernateUtils;


/**
 * For transactions work with MySQL, the tables must be of InnoDB type.
 * That is, use InnoDB dialect for hibernate.
 */
public class TestCustomerOrder
{
   protected static final Logger LOG = Logger.getLogger(TestCustomerOrder.class);

   @Test
   public void doTest()
      throws Exception
   {
      initInventory();

      buyProduct();

      checkout();

      //deleteLastCompany();
   }

   public void initInventory()
      throws Exception
   {
      Product p1 = new Product();
      p1.setName("Apple");
      p1.setPrice(0.99F);

      Product p2 = new Product();
      p2.setName("Orange");
      p2.setPrice(1.29F);

      Product p3 = new Product();
      p3.setName("Grape");
      p3.setPrice(2.89F);

      HibernateUtils.saveOrUpdate(p1);
      HibernateUtils.saveOrUpdate(p2);
      HibernateUtils.saveOrUpdate(p3);

      LOG.info("Products saved.");
   }

   public Product findProductByName(String name)
   {
      Session session = null;

      Product product = null;

      try
      {
         session = HibernateUtils.openSession();

         Query query = session.getNamedQuery("product.findByName");

         query.setString("name", name);

         product = (Product) query.uniqueResult();
      }
      finally
      {
         HibernateUtils.close(session);
      }

      return product;
   }

   public void buyProduct()
      throws Exception
   {
      Customer luke = new Customer();
      luke.setName("Luke Ma");

      Product p1 = findProductByName("Apple");
      Product p2 = findProductByName("Orange");
      // The owner is responsible for the association column(s) update 
      luke.addProduct(p1);
      luke.addProduct(p2);

      HibernateUtils.saveOrUpdate(luke);

      Customer hong = new Customer();
      hong.setName("Hong Lin");

      Product p3 = findProductByName("Grape");
      Product p4 = findProductByName("Orange");
      // The owner is responsible for the association column(s) update 
      hong.addProduct(p3);
      hong.addProduct(p4);

      HibernateUtils.saveOrUpdate(hong);

      LOG.info("Company and employees saved.");
   }

   public void checkout()
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         Query query = session.getNamedQuery("customer.findAll");

         @SuppressWarnings(value = "unchecked")
         List<Customer> customers = query.list();

         if (customers.size() > 0)
         {
            for (Customer customer : customers)
            {
               //Company company = (Company) (companies.get(0));

               String name = customer.getName();
               LOG.info("Customer name: " + name);

               Set<Product> productSet = customer.getProductSet();

               int size = productSet.size();

               LOG.info("Number of products: " + size);

               for (Product p : productSet)
               {
                  String eName = p.getName();
                  float price = p.getPrice();

                  LOG.info("Product name: " + eName + ". Price: " + price);
               }
            }
         }
         else
         {
            LOG.error("No company found.");
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }
}
