package com.learn.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.log4j.Log4j2;

/**
 * Hello world!
 */
@Log4j2
public class App {
    /**
     * static String url = "https://coderbyte.com/api/challenges/json/all-posts";
     */

     static String url = "https://dummy.restapiexample.com/api/v1/employees";

    /**
     * static String url = "https://localhost:8443/my-properties-boot/rest/house/getPropertyList";
     */

    public static void main(String[] args) {
        int numberOfRecords = 0;

        try {
            numberOfRecords = getNumberOfRecords(url);
        } catch (Exception e) {
            log.error("An error occurred while getting the number of records", e);
        }

        log.info("Hello World! Number of records: {}", numberOfRecords);
    }

    static int getNumberOfRecords(String url) throws Exception {

        /**
         * To call self-signed SSL service
         * 
        // Create a TrustManager that trusts all certificates
        TrustManager[] trustAll = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("TLSv1.2");
        sc.init(null, trustAll, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        */

        URL endpoint = new URI(url).toURL();

        HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("accept", "application/json");
        con.connect();

        int responseCode = con.getResponseCode();
        log.debug("GET Response Code :: {}", responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuilder response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            // print result
            log.info(response.toString());

            JsonElement jsonElement = JsonParser.parseString(response.toString());

            /**
             * For: "https://dummy.restapiexample.com/api/v1/employees";
             */
            jsonElement = jsonElement.getAsJsonObject().get("data");

            JsonArray ja = jsonElement.getAsJsonArray();

            log.debug("JSON Array [0]: {}", ja.get(0).toString());

            int size = ja.size();
            log.info("Number of records: {}", size);

            return size;
        } else {
            log.error("GET request did not work.");
        }

        return 10;
    }

}
