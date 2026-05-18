package com.learn.spring.jms.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.learn.boot.config.BootAppConfig;
import com.learn.boot.jms.Email;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { BootAppConfig.class })
@SpringBootTest
public class SpringJmsTest {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void testQueue() {
        LOG.info("Begin test.");

        // Send a message with a POJO - the template reuse the message converter
        LOG.info("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox.queue", new Email("info@example.com", "Queue"));

        LOG.info("End test.");
    }

    @Test
    public void testTopic() {
        LOG.info("Begin test.");

        // Send a message with a POJO - the template reuse the message converter
        LOG.info("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox.topic", new Email("info@example.com", "Topic"));

        LOG.info("End test.");
    }

}
