package com.learn.util;

import com.learn.session.tx.MovieSessionBeanDefaultRemote;


public class LookupConstants
{
   public static final String   JNDI_Book            = "EJB3JBoss-packaging/BookSessionBean/remote";

   public static final String   JNDI_Movie_Mandatory = "EJB3JBoss-packaging/MovieSessionBeanMandatory/remote";

   public static final String   JNDI_Movie_Default   = "MovieSessionBeanDefault!" + MovieSessionBeanDefaultRemote.class.getName();

   public static final String   JNDI_Movie_Manager   = "EJB3JBoss-packaging/MovieSessionBeanRunAsManager/remote";

   public static final String   JNDI_Movie_Employee  = "EJB3JBoss-packaging/MovieSessionBeanRunAsEmployee/remote";

   public static final String   JNDI_QUEUE_NAME      = "/queue/MyQueue";

   public static final String   JNDI_TOPIC_NAME      = "/topic/GreetingsTopic";

   public static final String   PROVIDER_URL_DB      = "jnp://loneqessappd1.uk.db.com:1199";

   public static final String[] PROVIDER_URLS        = { "jnp://localhost:1099", "jnp://192.168.1.200:1099",
         "jnp://192.168.1.202:1099",                };

}
