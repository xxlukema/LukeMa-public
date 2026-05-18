package com.fuelquest.users;


import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.fuelquest.users.model.User;
import com.fuelquest.users.model.UserSecurityAnswer;
import com.fuelquest.users.model.UserSecurityAnswerPK;
import com.fuelquest.users.model.UserSecurityComputerId;
import com.fuelquest.users.model.UserSecurityComputerIdPK;
import com.fuelquest.users.model.UserSecurityQuestion;


public class UserQuerier
{
   private static final Logger        LOG           = Logger.getLogger(UserQuerier.class);

   private static final Configuration CONFIGURATION = new Configuration();

   static
   {
      getConfig().addClass(User.class);
      getConfig().addClass(UserSecurityQuestion.class);
      getConfig().addClass(UserSecurityComputerId.class);
      getConfig().addClass(UserSecurityAnswer.class);
   }

   public static Configuration getConfig()
   {
      return CONFIGURATION;
   }

   public static void test()
   {
      createTable();
      insert();
   }

   public static void createTable()
   {
      try
      {
         SchemaExport dbExport = new SchemaExport(getConfig());
         dbExport.setOutputFile("target/sql.txt");
         dbExport.create(true, true);
      }
      catch (Throwable t)
      {
         LOG.error("Unable to create table.", t);
      }
   }

   public static Session openSession()
   {
      Session session = null;

      try
      {
         SessionFactory sessionFactory = getConfig().buildSessionFactory();

         session = sessionFactory.openSession();
      }
      catch (Throwable t)
      {
         LOG.error("Unable to open session.", t);
         closeSession(session);
      }

      return session;
   }

   public static void closeSession(Session session)
   {
      if (session != null)
      {
         try
         {
            session.close();
         }
         catch (Throwable t)
         {
            LOG.error("Unable to close session.", t);
         }
      }
   }

   public static void insert()
   {
      Session session = null;

      try
      {
         session = openSession();

         Transaction transaction = session.beginTransaction();

         User user = new User();
         user.setUserName("Luke Ma");
         user.setContactId(123l);

         session.save(user);

         UserSecurityQuestion userSecurityQuestion = new UserSecurityQuestion();
         userSecurityQuestion.setQuestion("What's your date of birth?");
         userSecurityQuestion.setGroupingNumber(1);
         userSecurityQuestion.setSequenceInGroup(5);

         session.save(userSecurityQuestion);

         LOG.debug("User id: " + user.getId());

         UserSecurityComputerIdPK userSecurityComputerIdPK = new UserSecurityComputerIdPK();
         userSecurityComputerIdPK.setUser(user);
         userSecurityComputerIdPK.setComputerId("123.33.445");

         UserSecurityComputerId userSecurityComputerId = new UserSecurityComputerId();
         userSecurityComputerId.setId(userSecurityComputerIdPK);

         session.save(userSecurityComputerId);

         UserSecurityAnswerPK userSecurityAnswerPK = new UserSecurityAnswerPK();
         userSecurityAnswerPK.setUser(user);
         userSecurityAnswerPK.setUserSecurityQuestion(userSecurityQuestion);

         UserSecurityAnswer userSecurityAnswer = new UserSecurityAnswer();
         userSecurityAnswer.setId(userSecurityAnswerPK);
         userSecurityAnswer.setAnswer("Oct 1, 1949");
         userSecurityAnswer.setCreatedBy(user);
         userSecurityAnswer.setCreateDate(new Date());
         userSecurityAnswer.setLastUpdatedBy(user);
         userSecurityAnswer.setLastUpdateDate(new Date());

         session.save(userSecurityAnswer);

         transaction.commit();

         LOG.debug("Transaction successful.");
      }
      catch (Exception e)
      {
         LOG.error("Hibernate Exception", e);
      }
      finally
      {
         closeSession(session);
      }
   }
}
