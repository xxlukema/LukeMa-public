package com.learn;


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.learn.bean.Person;
import com.learn.service.PersonService;
import com.learn.util.SpringApplicationContext;


public class PersonDriver {
    private static final Logger LOG = Logger.getLogger(PersonDriver.class);

    public static void main(String[] args)
        throws Exception {
        addPerson();
        queryPerson();
    }

    public static void addPerson()
        throws Exception {
        Person person = new Person();
        person.setName("Luke Ma");
        person.setWeight(160);

        PersonService personService = SpringApplicationContext.getBean("personService");
        personService.saveOrUpdate(person);
    }

    public static void queryPerson()
        throws Exception {
        PersonService personService = SpringApplicationContext.getBean("personService");

        List<Person> people = personService.list();
        Assert.assertTrue(people.size() > 0);
        LOG.info("people.size() = " + people.size());

        for (Person person : people) {
            LOG.info("Name   = " + person.getName());
            LOG.info("Weight = " + person.getWeight());
        }
    }
}
