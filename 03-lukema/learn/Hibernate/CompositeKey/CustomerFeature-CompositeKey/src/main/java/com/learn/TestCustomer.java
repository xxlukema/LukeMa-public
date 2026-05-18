package com.learn;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.learn.bean.Address;
import com.learn.bean.Customer;
import com.learn.bean.Feature;
import com.learn.bean.SelectedFeature;
import com.learn.bean.SelectedFeatureCompositeKey;


public class TestCustomer
{
   private static SessionFactory sessions;

   public static void main(String[] args)
   {
      try
      {
         Configuration conf = new Configuration().addClass(Customer.class).addClass(Feature.class).addClass(SelectedFeature.class);
         // .addJar(new File("target/customer-1.0.jar"));

         SchemaExport dbExport = new SchemaExport(conf);
         dbExport.setOutputFile("target/sql.txt");
         dbExport.create(true, true);

         sessions = conf.buildSessionFactory();

         //start......
         Session s = sessions.openSession();
         Transaction t = s.beginTransaction();

         Feature f0 = new Feature();
         f0.setFeatureId("00");
         f0.setDescription("Road side assiatance");
         s.saveOrUpdate(f0);

         f0 = new Feature();
         f0.setFeatureId("11");
         f0.setDescription("Free roaming");
         s.saveOrUpdate(f0);

         f0 = new Feature();
         f0.setFeatureId("23");
         f0.setDescription("Unlimited internet access");
         s.saveOrUpdate(f0);

         Customer c1 = new Customer();

         c1.setSsn("111223333");
         c1.setName("Luke Ma 213");

         Address address = new Address();
         address.setCity("Dallas");
         address.setState("TX");

         c1.setAddress(address);

         // Customer c1 = (Customer) s.get(Customer.class, "111223333");

         SelectedFeatureCompositeKey sfck = new SelectedFeatureCompositeKey();
         Feature f = (Feature) s.get(Feature.class, "11");
         sfck.setFeature(f);

         SelectedFeature sf = new SelectedFeature();
         sf.setSelectedFeatureCompositeKey(sfck);

         c1.addSelectedFeature(sf);

         s.saveOrUpdate(c1);

         t.commit();

         // Customer c2 = (Customer) s.get(Customer.class, ck);
         // System.out.println(c2.getName() + " " + (null == null));

         s.close();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
