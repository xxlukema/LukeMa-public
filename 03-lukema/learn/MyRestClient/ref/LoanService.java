/**
 * @author f372609
 *
 * 
 */
package com.freddie.ous.loan.service.rest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.enterprise.config.serverbeans.Config;
//import org.apache.http.client.params.AuthPolicy;
//import org.tempuri.ClientHandlerResolver;
//import org.tempuri.LoanNumberRequestSoap;


/**
 * @author f372609
 * 
 */
@Path(value = "loanservice")
public class LoanService {
    //private static LoanNumberRequestSoap loanNumberRequestSoap = null;
    private static ILoadNumberRequestService iSecureService = null;
    private static final Logger LOG = LogManager.getLogger();

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path(value = "loans")
    public String loanRequest() {
        String responseType = "Hello";
        String user = null;
        String password = null;
        ReadConfig config = new ReadConfig();
        HashMap<String, String> map = config.readConfigXML();
        System.setProperty("javax.net.ssl.trustStore", map.get("jksfile"));
        /*
        System.setProperty("javax.net.ssl.trustStore",
        		"c:/wsdl/weblogic_dev.jks");*/
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {
            public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {

                return true;

            }
        }

        );

        URL url;
        try {
            //url = new URL("https://he3ntvd230/NorthshoreWCF/LoanRequestService.svc?wsdl");
            url = new URL(map.get("webserviceURL"));

            QName qname = new QName("http://tempuri.org/", "Service1");

            Service service = Service.create(url, qname);

            iSecureService = service.getPort(ILoadNumberRequestService.class);

            Map<String, Object> requestContext = ((BindingProvider) iSecureService).getRequestContext();
            String macVaultflag = map.get("macvault");
            if (macVaultflag.equalsIgnoreCase("N")) {
                user = map.get("username");
                password = map.get("password");
                logger.info("Web service user ID:" + user);
            } else // get mac vaulted ID and password
            {
                String aliasName = map.get("macvaultalias");
                logger.info("Macvaulted alias ID:" + aliasName);
                SecureCredentialFetchImpl scfClient = new SecureCredentialFetchImpl();
                SCFResult scfResult = scfClient.getCredential(aliasName, "true");
                user = scfResult.getUsername();
                password = scfResult.getPassword();
                logger.info("SCF userID:" + user);
                System.out.println("SCF userID:" + user);
            }

            /*user = new String(Base64.encodeBase64(user.getBytes()));
            pwd= new String(Base64.encodeBase64(pwd.getBytes()));
                 */
            //requestContext.put(javax.xml.ws.BindingProvider.SESSION_MAINTAIN_PROPERTY, true);

            List<CredentialProvider> credProviders = new ArrayList<CredentialProvider>();
            /*
             * Set up UserNameToken
             */
            /*credProviders.add(new ClientUNTCredentialProvider("ousloanauth"
            		.getBytes(), "ous_mf9012".getBytes()));*/
            credProviders.add(new ClientUNTCredentialProvider(user.getBytes(), password.getBytes()));
            requestContext.put(WSSecurityContext.CREDENTIAL_PROVIDER_LIST, credProviders);

            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url.toString());
            requestContext.put(BindingProvider.USERNAME_PROPERTY, "");
            requestContext.put(BindingProvider.PASSWORD_PROPERTY, "");

            //String responseType = iSecureService.getLoanNumbers();
            //System.out.print(responseType);

            responseType = iSecureService.getLoanNumbers();
            /*System.out.print(responseType);
            	responseType = loanNumberRequestSoap.getLoanNumbers();*/
            // System.out.print(responseType);
            logger.info(responseType);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("Exception" + e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception" + e);
        }

        return responseType;
    }

    public static void main(String args[]) {
        LoanService loanService = new LoanService();
        String xml = loanService.loanRequest();
        System.out.println(xml);
        logger.info("Got the xml.");
    }

    public static String readXMLFile(InputStream is) {

        // InputStream in = /* your InputStream */;
        InputStreamReader isR = new InputStreamReader(is);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(isR);
        String read;
        try {
            read = br.readLine();
            while (read != null) {
                // System.out.println(read);
                sb.append(read);
                read = br.readLine();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("Exception:" + e);
        }

        System.out.print(sb.toString());
        logger.info(sb.toString());
        return sb.toString();
    }

    private static String getWebServiceURL() {
        String urlString = null;
        try {
            urlString = Config.getStringValue("mf.ousLoan.webserviceURL");
        } catch (Exception ex) {
            System.out.println("Exception while loading the config value for webservice URL" + ex.getMessage());
            ex.printStackTrace();
        }
        return urlString;
    }
}
