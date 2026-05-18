package com.learn.util;


public class LookupConstants
{
   public static final String   JNDI_AutoAssociationSessionBean     = "EJB3JBoss-packaging/AutoAssociationSessionBean/remote";

   public static final String   JNDI_SwapSessionBean                = "EJB3JBoss-packaging/SwapSessionBean/remote";

   public static final String   JNDI_LukeProcSessionBean            = "EJB3JBoss-packaging/LukeProcSessionBean/remote";

   public static final String   JNDI_BasketEventSessionBean         = "EJB3JBoss-packaging/BasketEventSessionBean/remote";

   public static final String   JNDI_QUEUE_NAME                     = "/queue/MyQueue";

   public static final String   JNDI_TOPIC_NAME                     = "/topic/GreetingsTopic";

   public static final String   JNDI_TOPIC_NAME_CHANGE_NOTIFICATION = "/topic/ChangeNotificationTopic";

   public static final String   PROVIDER_URL_DB                     = "jnp://loneqessappd1.uk.db.com:1399";

   public static final String[] PROVIDER_URLS                       = { "jnp://localhost:1099",
         "jnp://192.168.1.200:1099", "jnp://192.168.1.202:1099",   };

}
