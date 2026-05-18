package com.learn;


import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:SpringBeanConfig.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class TestConfig {

    protected static final Logger LOG = Logger.getLogger(TestConfig.class);

    @Autowired
    private Environment environment;

    @Value("${luke.name}")
    private String lukeName;

    @Test
    public void testEnvironment() {
        LOG.info("Begining of Test");

        LOG.info("user.name = " + environment.getProperty("user.name"));
        LOG.info("os.name = " + environment.getProperty("os.name"));

        LOG.info("End of Test");
    }

    @Test
    public void testValue() {
        LOG.info("Begining of Test");

        LOG.info("lukeName = " + lukeName);

        LOG.info("End of Test");
    }

}
