package com.learn.test.session;


import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.learn.session.PingLocal;


/**
 * 
 * https://netbeans.org/kb/docs/javaee/javaee-entapp-junit.html
 * 
 * @author lma
 *
 */

public class PingSessionBeanTest {

    private static final Logger LOG = LogManager.getLogger(PingSessionBeanTest.class);

    private EJBContainer ejbContainer;
    private Context context;

    @Before
    public void setup() {

        //Properties properties = new Properties();
        //properties.setProperty(EJBContainer.MODULES, "EntityManager");
        //properties.put(EJBContainer.PROVIDER, "tomee-embedded");
        //properties.setProperty("openejb.embedded.remotable", "true");
        //ejbContainer = EJBContainer.createEJBContainer(properties);

        ejbContainer = EJBContainer.createEJBContainer();

        context = ejbContainer.getContext();
    }

    @After
    public void tearDown()
        throws NamingException {
        if (ejbContainer != null) {
            ejbContainer.close();
        }
    }

    @Test
    public void testPing()
        throws Exception {
        LOG.info("Begin Test");

        PingLocal pingSessionBean = (PingLocal) context.lookup("java:global/EntityManager/PingSessionBean");

        String hello = pingSessionBean.ping("Hello world!");

        LOG.info(hello);

        LOG.info("End Test");
    }

}
