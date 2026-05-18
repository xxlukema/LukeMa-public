package com.freddiemac.mf.dms.util;


import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.enterprise.config.serverbeans.Config;


/*
 * Utility class that defines methods that are used across the project
 */
public class DMSServiceUtil {

    private static final Logger LOG = LogManager.getLogger();

    /*
     * Creates DefaultHttpClient object to perform HTTP calls.
     */
    public DefaultHttpClient getHttpClient(String serviceUrl, String soapAction)
        throws DMSException {

        DefaultHttpClient httpclient = null;

        try {

            String proxyHost = Config.getStringValue("mf.il.proxy.Host");
            int proxyPort = Integer.parseInt(Config.getStringValue("mf.il.proxy.Port"));

            // If operating system is Windows then read the values from config files
            // if linux then read the macvaulted userID and password
            String operSystem = System.getProperty("os.name");
            logger.info("Operating system=" + operSystem);
            String proxyUsername = null;
            String proxyPassword = null;
            /**
            proxyUsername = Config
            		.getStringValue("mf.il.proxy.Username");
            proxyPassword = Config
            		.getStringValue("mf.il.proxy.Password");
            **/

            if (operSystem != null && operSystem.startsWith("Windows")) {
                logger.info("Getting proxy ID and password form config file for Windows.");
                proxyUsername = Config.getStringValue("mf.il.proxy.Username");
                proxyPassword = Config.getStringValue("mf.il.proxy.Password");
            } else {
                //get macvaulted ID
                logger.info("Getting macvaulted ID for bproxy:");
                String aliasName = Config.getStringValue("mf.il.proxy.scfALIAS");
                logger.info("Getting macvaulted ID for bproxy: scfALIAS=" + aliasName);
                SecureCredentialFetchImpl scfClient = new SecureCredentialFetchImpl();
                SCFResult scfResult = scfClient.getCredential(aliasName, "true");
                proxyUsername = scfResult.getUsername();
                proxyPassword = scfResult.getPassword();

            }

            logger.info("loaded proxyHost :: proxyPort :: proxyUserName  " + proxyHost + " -- " + proxyPort + " -- " + proxyUsername);

            String dmsUserName = Config.getStringValue("mf.il.dms.Username");
            String dmsPassword = Config.getStringValue("mf.il.dms.Password");

            String protocol = Config.getStringValue("mf.il.dms.protocol");
            String dmsHost = Config.getStringValue("mf.il.dms.Host");
            int dmsPort = Integer.parseInt(Config.getStringValue("mf.il.dms.Port"));

            logger.info(" loaded dmsUserName " + dmsUserName);

            //setting internet proxy  	
            httpclient = new DefaultHttpClient();
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            httpclient.getCredentialsProvider().setCredentials(new AuthScope(proxyHost, proxyPort), new UsernamePasswordCredentials(proxyUsername, proxyPassword));
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

            //setting dms server
            httpclient.getCredentialsProvider().setCredentials(new AuthScope(dmsHost, dmsPort), new UsernamePasswordCredentials(dmsUserName, dmsPassword));
            httpclient.getParams().setParameter("http.useragent", "Web Service Client");

            // setting scheme
            SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
            Scheme sch = new Scheme(protocol, dmsPort, socketFactory);
            httpclient.getConnectionManager().getSchemeRegistry().register(sch);

        } catch (IntegrationException ie) {
            logger.info("exception while creating  DefaultHttpClient :: " + ie.getMessage());
            logger.error("exception while creating  DefaultHttpClient:" + ie);
            throw new DMSException("Exceptiom while reading the configuration data ");

        } catch (Exception e) {
            logger.info("exception while creating  DefaultHttpClient :: " + e.getMessage());
            logger.error("exception while creating  DefaultHttpClient:" + e);
            throw new DMSException("exception while creating  DefaultHttpClient ");
        }

        return httpclient;
    }

    /*
     * Reports the errors to users thru email 
     */
    public boolean reportUploadErrors(HashMap<String, String> errMap)
        throws DMSException {
        for (Map.Entry<String, String> entry : errMap.entrySet()) {
            logger.info(entry.getKey() + "	-- " + entry.getValue());
        }

        //add email logic here
        EmailServiceImpl emailService = new EmailServiceImpl();
        emailService.initialize();
        StringBuffer failureMessage = new StringBuffer();
        for (Map.Entry<String, String> entry : errMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            failureMessage.append(key + " - " + value + "\n");

        }
        try {
            emailService.sendErrorEmail(failureMessage.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;

    }

}
