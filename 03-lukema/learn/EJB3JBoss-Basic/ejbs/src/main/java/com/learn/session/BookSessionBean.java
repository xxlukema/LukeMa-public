package com.learn.session;


import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.learn.util.EjbConstants;
import com.learn.entity.Book;
import com.learn.exception.AppException;


@Stateless
public class BookSessionBean
    implements BookSessionBeanLocal, BookSessionBeanRemote {
    private static final long serialVersionUID = 1L;

    protected static final Logger LOG = Logger.getLogger(BookSessionBean.class);

    //@PersistenceContext
    @PersistenceContext(unitName = EjbConstants.UnitName)
    EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public void testCommit()
        throws Exception {
        LOG.info("########## BookSessionBean.test() invoked.");

        List<Book> books = (List<Book>) entityManager.createQuery("from Book").getResultList();

        if (books.size() == 0) {
            Book book1 = new Book();
            book1.setTitle("My first bean book");
            book1.setAuthor("Paul");
            book1.setCreateDate(new Date());
            book1.setUpdateDate(new Date());

            entityManager.merge(book1);

            Book book2 = new Book();
            book2.setTitle("My second bean book");
            book2.setAuthor("John");
            book2.setCreateDate(new Date());
            book2.setUpdateDate(new Date());

            entityManager.persist(book2);

            books = (List<Book>) entityManager.createQuery("from Book").getResultList();
        }

        LOG.info("List books: " + books.size());

        for (Book book : books) {
            LOG.info(book);

            if (book.getAuthor().equalsIgnoreCase("John")) {
                book.setTitle("Paul's third book");
                book.setUpdateDate(new Date());

                entityManager.persist(book);
            }
        }
    }

    @Override
    public void testRollback()
        throws Exception {
        LOG.info("########## BookSessionBean.test() invoked.");

        Book book1 = new Book();
        book1.setTitle("This book will be rollbacked.");
        book1.setAuthor("Tom");
        book1.setCreateDate(new Date());
        book1.setUpdateDate(new Date());

        entityManager.merge(book1);

        throw new AppException("Test rollback on AppException");
    }
}
