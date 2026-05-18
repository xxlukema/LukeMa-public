package com.learn.mail;

import java.io.File;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.learn.classpath.ClassPathPropertyLoader;
import com.learn.util.HeartBeatNotifier;
import com.learn.util.ShutdownNotifier;


public class Mailer
{
   private static final Logger LOGGER = Logger.getLogger(Mailer.class);

   private static final String PROP_FILE_NAME = "mail.properties";

   private static final Properties MAIL_PROPS = new Properties();

   private static Address [] toAddresses = null;
   private static Address [] pageAddresses = null;
   private static Address    fromAddress = null;
   // private static String     host        = null;
   private static boolean    sendMail    = false;

   private static final Map<String, Long> MAIL_TRACK_MAP = new HashMap<String, Long>();
   private static String hostName = null;
   private static String applicationName = null;

   private static final Timer timer = new Timer(true);

   static
   {
      Properties props = ClassPathPropertyLoader.load(PROP_FILE_NAME);
      String strSendMail = props.getProperty("sendMail", "false");
      if (strSendMail.equalsIgnoreCase("true"))
      {
         sendMail = true;
      }

      String mailHost = props.getProperty("host", "nhqsexc21.nam.coair.com");
      MAIL_PROPS.put("mail.smtp.host", mailHost);

      String from = props.getProperty("from", "do.not.reply@coair.com");
      try
      {
         fromAddress = new InternetAddress(from.trim());
      }
      catch (Throwable th)
      {
         th.printStackTrace();

         LOGGER.error(th);
      }

      toAddresses = retrieveAddresses(props, "to");
      pageAddresses = retrieveAddresses(props, "page");
      try
      {
         hostName = InetAddress.getLocalHost().getHostName().toLowerCase();
      }
      catch (Throwable t)
      {
      }

      applicationName = retrieveApplicationName();

      Runtime.getRuntime().addShutdownHook(new ShutdownNotifier());

      Calendar c = Calendar.getInstance();

      c.set(Calendar.AM_PM, Calendar.AM);
      c.set(Calendar.HOUR, 8);
      Date date08am = c.getTime();

      c.set(Calendar.AM_PM, Calendar.PM);
      c.set(Calendar.HOUR, 3);
      Date date03pm = c.getTime();

      int oneDayInMillisec = 24*3600*1000;

      TimerTask tt1 = new HeartBeatNotifier();
      timer.scheduleAtFixedRate(tt1, date08am, oneDayInMillisec);

      TimerTask tt2 = new HeartBeatNotifier();
      timer.scheduleAtFixedRate(tt2, date03pm, oneDayInMillisec);
   }

   public static void cancel()
   {
      //timer.cancel();
   }

   public static Address [] retrieveAddresses(Properties props, String propStr)
   {
      String to   = props.getProperty(propStr, "");
      List<InternetAddress> receiverList = new LinkedList<InternetAddress>();
      StringTokenizer st = new StringTokenizer(to, ";, ");
      while (st.hasMoreElements())
      {
         String receiver = (String) st.nextElement();
         if (receiver != null)
         {
            receiver = receiver.trim();
            if (receiver.length() > 5 && receiver.indexOf("@") > 0)
            {
               InternetAddress receiverAddress = null;
               try
               {
                  receiverAddress = new InternetAddress(receiver);
                  receiverList.add(receiverAddress);
               }
               catch (Throwable th)
               {
               }
            }
         }
      }

      return (Address []) receiverList.toArray(new Address [0]);
   }

   public static String getHostName()
   {
      return hostName;
   }

   public static String getApplicationName()
   {
      return applicationName;
   }

   public static String retrieveApplicationName()
   {
      String usrApp = null;

      Throwable trace = new Throwable();

      StackTraceElement [] stes = trace.getStackTrace();

      String stackMethodName = null;
      String stackFileName   = null;

      for (int i=stes.length-1; i>=0; i--)
      {
         stackMethodName = stes[i].getMethodName();

         if (stackMethodName.equals("main"))
         {
            stackFileName = stes[i].getFileName();
            usrApp = stackFileName.replaceAll("\\.java", "");

            break;
         }
      }

      if (usrApp == null || usrApp.equals("SurefireBooter"))
      {
         String usrDir = System.getProperty("user.dir");
         usrApp = (new File(usrDir)).getName();
      }

      return usrApp;
   }

   public static void sendMail(String subject, Throwable t)
   {
      if (subject == null || t == null)
      {
         return;
      }

      String text = "<B>"+t.getMessage()+"</B><BR>";

      StackTraceElement [] stes = t.getStackTrace();

      for (int i=0; i<stes.length; i++)
      {
         text += "&nbsp;&nbsp;&nbsp;at "+stes[i].toString()+"<BR>";

         if (i > 2)
         {
            text += "&nbsp;&nbsp;&nbsp;<B>...</B><BR>";
            break;
         }
      }

      sendMail(subject, text);
   }

   public static void sendPage(String subject, String htmlMessage)
   {
      send(subject, htmlMessage, pageAddresses);
   }

   public static void sendMail(String subject, String htmlMessage)
   {
      send(subject, htmlMessage, toAddresses);
   }

   private static void send(String subject, String htmlMessage, Address [] addresses)
   {
      if (!sendMail || subject == null || htmlMessage == null || addresses == null || addresses.length == 0)
      {
         LOGGER.info("Mail not sent. \nMail Subject: "+subject+"\nMail Message: "+htmlMessage);
         return;
      }

      String id = manageMailId(subject, htmlMessage, addresses);

      if (!sameMailSentTimeout(id))
      {
         return;
      }

      Session session = Session.getDefaultInstance(MAIL_PROPS, null);

      Message msg = new MimeMessage(session);

      String textMessage = "Mail Sent: <B>"+(new Date())+
                           "</B><BR><BR>Application Host: <B>"+hostName+
                           "</B><BR>Application Name: <B>"+applicationName+"</B><BR><BR>"+
                           id;

      int index = textMessage.indexOf("-RECEIVERS:-");
      if (index > 0)
      {
         textMessage = textMessage.substring(0, index);
      }

      try
      {
         msg.setContent(textMessage, "text/html");
         msg.setFrom(fromAddress);
         msg.setRecipients(Message.RecipientType.TO, addresses);
         msg.setSubject(getHostName()+"-"+getApplicationName()+": "+subject);
         Transport.send(msg);

         /*
         String logMsg = textMessage.replaceAll("<[/]?[Bb]>", "");
         logMsg = logMsg.replaceAll("<[Bb][Rr][/]?>", "\n");
         logMsg = logMsg.replaceAll("&nbsp;", " ");

         LOGGER.info(logMsg);
         */
      }
      catch (Throwable th)
      {
         LOGGER.error("Unable to send mail: "+th.getMessage());
      }
   }

   public static StackTraceElement retrieveStackTraceElement()
   {
      Throwable throwable = new Throwable();

      StackTraceElement [] stes = throwable.getStackTrace();

      int index = 0;

      String lastStackClassName = null;
      String stackClassName = null;
      for (int i=0; i<stes.length; i++)
      {
         stackClassName = stes[i].getClassName();
         if (lastStackClassName != null &&
             lastStackClassName.equals(Mailer.class.getName()) && 
             !stackClassName.equals(Mailer.class.getName()))
         {
            index = i;
            break;
         }

         lastStackClassName = stackClassName;
      }

      return stes[index];
   }

   private static String manageMailId(String subject, String message, Address [] addresses)
   {
      StackTraceElement ste = retrieveStackTraceElement();

      String stackClassName = ste.getClassName();
      String methodName     = ste.getMethodName();
      int    lineNumber     = ste.getLineNumber();

      String id = "Subject: <B>"+subject+"<BR><BR>"
                 +stackClassName+"("+lineNumber+") "+methodName+"(): </B><BR><BR>"
                 +message;

      String receivers = "-RECEIVERS:-";
      for (int i=0; i<addresses.length; i++)
      {
         receivers += addresses[i].toString();
      }

      id += receivers;

      return id;
   }

   private static boolean sameMailSentTimeout(String id)
   {
      Long lastTimeSent = (Long) (MAIL_TRACK_MAP.get(id));

      long now = System.currentTimeMillis();

      if (lastTimeSent != null)
      {
         // If last mail with same id sent is less than 30 minutes skip sending mail.
         if ((now - lastTimeSent.longValue()) < 1000*60*30)
         {
            return false;
         }
      }

      MAIL_TRACK_MAP.put(id, new Long(now));

      return true;
   }
}


