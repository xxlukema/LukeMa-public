package com.learn.mockito.dao.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.mockito.bean.Person;
import com.learn.mockito.dao.PersonDao;


public class PersonDaoImpl
    implements PersonDao {

    private static final Logger LOG = LogManager.getLogger(PersonDaoImpl.class);

    @Override
    public Person find(Integer personID) {
        LOG.info("PersonDaoImpl.find() called.");
        return null;
    }

    @Override
    public void update(Person person) {
        LOG.info("PersonDaoImpl.update() called.");
    }

}
