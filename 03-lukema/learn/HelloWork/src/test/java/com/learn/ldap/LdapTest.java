package com.learn.ldap;


import java.util.Hashtable;

import javax.inject.Named;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


@Named
public class LdapTest {
    private static final Logger LOG = LogManager.getLogger();

    protected static final String USA_DOMAIN = "USA";
    protected static final String DEVSUB_DOMAIN = "DEVSUB";
    protected static final String NO_DOMAIN = "";

    protected static final String USA_LADP_DOMAIN = "usa.dce.usps.gov";
    private static final String DEVSUB_LADP_DOMAIN = "devsub.dev.dce.usps.gov";

    protected static final String USA_SEARCH_BASE = "dc=usa, dc=dce, dc=usps, dc=gov";
    protected static final String DEVSUB_SEARCH_BASE = "dc=devsub, dc=dev, dc=dce, dc=usps, dc=gov";

    protected static final String LDAPS_PROTOCOL = "ldaps";
    private static final String LDAP_PROTOCOL = "ldap";

    private static final String LDAPS_PORT = "636";
    protected static final String LDAP_PORT = "389";

    private static final String SEARCH_HOST = "eagandcs";

    private static final String LDAP_URL_FORMAT = "%s://%s:%s/";

    private static final String USER_NAME = "yz0yj0";
    private static final String PASSWORD = "Usps1234";

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        String ldapHostnameDomainname = SEARCH_HOST + "." + DEVSUB_LADP_DOMAIN;
        
        //String ldapUrl = String.format(LDAP_URL_FORMAT, LDAPS_PROTOCOL, ldapHostnameDomainname, LDAPS_PORT);
        String ldapUrl = String.format(LDAP_URL_FORMAT, LDAP_PROTOCOL, ldapHostnameDomainname, LDAPS_PORT);

        LdapContext ctx = getLdapContext(ldapUrl);

        LOG.info("ctx == null ? " + (ctx == null));

        LOG.info("End Test.");

    }

    public LdapContext getLdapContext(String ldapUrl) {
        LdapContext ctx = null;

        try {
            Hashtable<String, String> env = new Hashtable<>();

            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, USER_NAME + "@" + DEVSUB_DOMAIN);
            env.put(Context.SECURITY_CREDENTIALS, PASSWORD);
            env.put(Context.PROVIDER_URL, ldapUrl);
            env.put(Context.SECURITY_PROTOCOL, "ssl");
            env.put("com.sun.jndi.ldap.connect.pool", "true");

            ctx = new InitialLdapContext(env, null);

            LOG.info("Connection Successful.");
        } catch (NamingException nex) {
            LOG.error("LDAP Connection: FAILED", nex);
        }

        return ctx;
    }
}
