package com.learn.util;


import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


public class EjbLookupUtils {
    protected static final Logger LOG = Logger.getLogger(EjbLookupUtils.class);

    public static Context getInitialContext()
        throws NamingException {
        return new InitialContext();
    }

    public static Context getInitialContext2(String providerUrl)
        throws NamingException {
        Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        properties.setProperty(Context.PROVIDER_URL, providerUrl);

        return new InitialContext(properties);
    }

    @SuppressWarnings("unchecked")
    public static <T> T lookup(String jndi) {
        Context context = null;
        try {
            context = getInitialContext();
        }
        catch (NamingException e) {
            LOG.error("Unable to get InitialContext().", e);
        }

        if (context == null) {
            return null;
        }

        T t = null;
        try {
            t = (T) context.lookup(jndi);
        }
        catch (NamingException e) {
            LOG.error("Unable to do context.lookup(jndi). ", e);
        }

        return t;
    }
}
