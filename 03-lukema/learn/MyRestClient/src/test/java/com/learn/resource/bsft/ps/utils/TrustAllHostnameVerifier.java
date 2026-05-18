package com.learn.resource.bsft.ps.utils;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TrustAllHostnameVerifier
    implements HostnameVerifier {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public boolean verify(String hostname, SSLSession session) {
        LOG.info("Called.");
        return true;
    }

}
