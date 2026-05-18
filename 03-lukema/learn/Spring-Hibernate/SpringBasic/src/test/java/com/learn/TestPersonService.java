package com.learn;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.learn.aop.bean.CustomerService;
import com.learn.bean.Person;
import com.learn.service.PersonService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:SpringBeanConfig.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class TestPersonService {

    protected static final Logger LOG = Logger.getLogger(TestPersonService.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PersonService personService;

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Test
    public void testJdbc()
        throws Exception {
        LOG.info("Start testJdbc().");

        Session session = sessionFactory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("select 'text field', sysdate from dual");

        @SuppressWarnings("unchecked")
        List<Object[]> list = sqlQuery.list();

        LOG.info("list.size() = " + list.size());

        for (Object[] rows : list) {
            for (Object column : rows) {
                System.out.println("column = " + column);
            }
        }

        LOG.info("End testJdbc().");
    }

    @Test
    public void testAop1()
        throws Exception {
        LOG.info("Start testing AOP 1.");

        customerService.addCustomer();
        customerService.addCustomerAround("Luke Ma");
        customerService.addCustomerReturnValue();

        customerService.logAnnotationAround("X. Luke Ma");

        LOG.info("End testing AOP 1.");
    }

    @Test(expected = Exception.class)
    // @Ignore
    public void testAop2()
        throws Exception {
        LOG.info("Start testing AOP 2.");

        customerService.addCustomerThrowException();

        LOG.info("End testing AOP 2.");
    }

    @Test
    //@Ignore
    public void testApp()
        throws Exception {
        addPerson();
        queryPerson();
    }

    public void addPerson()
        throws Exception {
        Person person = new Person();
        person.setName("Luke Ma");
        person.setWeight(160);

        personService.saveOrUpdate(person);

        person = new Person();
        person.setName("Hong Lin");
        person.setWeight(120);

        personService.saveOrUpdate(person);
    }

    public void queryPerson()
        throws Exception {

        List<Person> people = personService.list();
        Assert.assertTrue(people.size() > 0);

        StringBuilder sb = new StringBuilder();

        sb.append("people.size() = " + people.size()).append('\n');

        for (Person person : people) {
            sb.append("Name = " + person.getName()).append(", ");
            sb.append("Weight = " + person.getWeight()).append('\n');
        }

        LOG.info(sb);
    }
}
