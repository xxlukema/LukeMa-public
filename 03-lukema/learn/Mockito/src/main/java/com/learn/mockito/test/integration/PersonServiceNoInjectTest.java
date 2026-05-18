package com.learn.mockito.test.integration;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.learn.mockito.dao.PersonDao;
import com.learn.mockito.service.PersonService;
import com.learn.mockito.service.impl.PersonServiceImpl;
import com.learn.mockito.test.util.PersonServiceTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class PersonServiceNoInjectTest {

    private static final Logger LOG = LogManager.getLogger(PersonServiceNoInjectTest.class);

    @Mock
    private PersonDao personDao;

    private PersonService personService;

    @Before
    public void before()
        throws Exception {

        personService = new PersonServiceImpl(personDao);
    }

    @Test
    public void testFoundThenUpdate() {

        LOG.info("Begin Test.");

        PersonServiceTestUtils.testFoundThenUpdate(personDao, personService);

        LOG.info("End Test.");
    }

    @Test
    public void testNotFoundNoUpdate() {

        LOG.info("Begin Test.");

        PersonServiceTestUtils.testNotFoundNoUpdate(personDao, personService);

        LOG.info("End Test.");
    }
}
