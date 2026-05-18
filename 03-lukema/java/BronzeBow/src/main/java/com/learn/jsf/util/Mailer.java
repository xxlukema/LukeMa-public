package com.learn.jsf.util;


import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Mailer {
    private static final String HOST = "askbuffett.com";

    private static final String FROM = "x.luke.ma@AskBuffett.com";

    public static void sendMail(String subject, String body, String receiverEmailAddress)
        throws Exception {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", "25");

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM));

        List<InternetAddress> receiverList = new LinkedList<InternetAddress>();
        InternetAddress receiverAddress = new InternetAddress(receiverEmailAddress);
        receiverList.add(receiverAddress);

        Address[] toAddresses = (Address[]) receiverList.toArray(new Address[0]);
        Address fromAddress = new InternetAddress(FROM);

        message.setContent(body, "text/html");
        message.setFrom(fromAddress);
        message.setRecipients(Message.RecipientType.TO, toAddresses);
        message.setSubject(subject);
        Transport.send(message);
    }

    public static void main(String args[])
        throws Exception {
        String receiverEmailAddress = "x.luke.ma@gmail.com";
        String subject = "Test mailer";
        String body = "Mailer body.";
        Mailer.sendMail(subject, body, receiverEmailAddress);
    }

}
