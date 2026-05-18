package com.learn.mockito.test.integration;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.Stage;
import com.google.inject.util.Modules;
import com.learn.mockito.dao.PersonDao;
import com.learn.mockito.dao.impl.PersonDaoImpl;
import com.learn.mockito.service.PersonService;
import com.learn.mockito.service.impl.PersonServiceImpl;
import com.learn.mockito.test.util.PersonServiceTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class PersonServiceMockInterfaceTest {

    private static final Logger LOG = LogManager.getLogger(PersonServiceMockInterfaceTest.class);

    @Mock
    private PersonDao personDao;

    @Inject
    private PersonService personService;

    @Before
    public void before()
        throws Exception {

        Module prodModule = new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(PersonDao.class).to(PersonDaoImpl.class).in(Scopes.SINGLETON);
                binder.bind(PersonServiceImpl.class).in(Scopes.SINGLETON);
            }
        };

        Module testModule = new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(PersonDao.class).toInstance(personDao);
            }
        };

        Injector injector = Guice.createInjector(Stage.PRODUCTION, Modules.override(prodModule).with(testModule));
        injector.injectMembers(this);
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
