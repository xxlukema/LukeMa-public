package com.learn;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.learn.entity.Book;
import com.learn.util.HibernateUtils;


public class BookDriver
{
   private static final Logger LOG = Logger.getLogger(BookDriver.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.debug("Test begin.");

      addRecord();
      queryRecord();

      LOG.debug("Test complete.");
   }

   public static void addRecord()
      throws Exception
   {
      LOG.debug("Inside addRecord()...");

      int booksInDB = 0;
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         List<Book> books = (List<Book>) HibernateUtils.list(session, Book.class);

         booksInDB = books.size();
      }
      finally
      {
         HibernateUtils.close(session);
      }

      if (booksInDB == 0)
      {
         LOG.debug("Adding records...");

         Book book1 = new Book();
         book1.setTitle("My first bean book");
         book1.setAuthor("Paul");
         book1.setCreateDate(new Date());
         book1.setUpdateDate(new Date());

         HibernateUtils.saveOrUpdate(book1);

         Book book2 = new Book();
         book2.setTitle("My second bean book");
         book2.setAuthor("John");
         book2.setCreateDate(new Date());
         book2.setUpdateDate(new Date());

         HibernateUtils.saveOrUpdate(book2);

         LOG.debug("Two books added.");
      }
      else
      {
         LOG.debug("Number of books in DB: " + booksInDB);
      }
   }

   public static void queryRecord()
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         List<Book> books = (List<Book>) HibernateUtils.list(session, Book.class);

         for (Book book : books)
         {
            System.out.println(book);

            if (book.getAuthor().equalsIgnoreCase("John"))
            {
               book.setTitle("Paul's third book");
               book.setUpdateDate(new Date());

               HibernateUtils.saveOrUpdate(book);
            }
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

}
