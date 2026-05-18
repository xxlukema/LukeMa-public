package com.learn.resource.bsft.ps.utils;


import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TrustAllX509TrustManager
    implements X509TrustManager {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        LOG.info("Called.");
        //return new X509Certificate[0];
        return null;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] certs, String authType) {
        LOG.info("Called.");
    }

    @Override
    public void checkServerTrusted(X509Certificate[] certs, String authType) {
        LOG.info("Called.");
    }

}
