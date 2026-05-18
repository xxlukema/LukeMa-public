package com.learn.boot.jms;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class Receiver {

    private static final Logger LOG = LogManager.getLogger();

    @JmsListener(destination = "mailbox.queue", containerFactory = "queueListenerFactory")
    public void receiveQueue1(Email email) {
        LOG.info("receiveQueue1: Received <" + email + ">");
    }

    @JmsListener(destination = "mailbox.queue", containerFactory = "queueListenerFactory")
    public void receiveQueue2(Email email) {
        LOG.info("receiveQueue2: Received <" + email + ">");
    }

    @JmsListener(destination = "mailbox.topic", containerFactory = "topicListenerFactory")
    public void receiveTopic1(Email email) {
        LOG.info("receiveTopic1: Received <" + email + ">");
    }

    @JmsListener(destination = "mailbox.topic", containerFactory = "topicListenerFactory")
    public void receiveTopic2(Email email) {
        LOG.info("receiveTopic2: Received <" + email + ">");
    }
}
