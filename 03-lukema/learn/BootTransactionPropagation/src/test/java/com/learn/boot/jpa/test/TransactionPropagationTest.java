package com.learn.boot.jpa.test;


import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.learn.boot.config.BootAppConfig;
import com.learn.boot.jpa.dao.StudentService;
import com.learn.boot.jpa.exception.AppException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BootAppConfig.class }, loader = AnnotationConfigContextLoader.class)
public class TransactionPropagationTest {

    private static final Logger LOG = LogManager.getLogger();

    @Resource
    private StudentService studentService;

    @Test
    public void testTransaction() {

        LOG.info("Begin Test =====================================");

        try {
            studentService.doTransaction();
        } catch (AppException e) {
            LOG.error("Exception occurred.");
        }

        LOG.info("End Test. =====================================");
    }

}
