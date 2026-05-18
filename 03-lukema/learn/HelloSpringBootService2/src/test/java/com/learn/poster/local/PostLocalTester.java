package com.learn.poster.local;


import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class PostLocalTester {

    private RestTemplate restTemplate = null;

    // private static int REPEATER = 1_000;
    private static int REPEATER = 3;

    @SuppressWarnings("unused")
    private HttpEntity<String> entity = null;

    private static final List<Socket> SOCKET_LIST = new ArrayList<>();

    @Before
    public void before()
        throws Exception {
        log.info("before(). For each test.");
        restTemplate = new RestTemplate();

        // Set the Accept header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
        entity = new HttpEntity<String>("parameters", headers);
    }

    // @Test
    public void testRestTemplate() {

        String uri = "https://localhost:8443/spring/ping2?i=";
        Random rand = new Random();

        int i = 0;
        while (i++ < REPEATER) {
            if (i == REPEATER - 1) {
                // i = 0;
                log.debug("URI: " + uri + i);
            }

            restTemplate.getForObject(uri + rand.nextInt(), String.class);
            // String response = restTemplate.getForObject(uri + i, String.class);
            // log.debug(response);
        }

    }

    public void testDDNS() {

        //String uri = "https://localhost:8443/spring/ping2?i=";
        String uri = "http://thanksthanks.ddns.net/r.php?t=c&d=%d&l=%d&c=%d";
        Random rand = new Random();
        int n = rand.nextInt();

        uri = String.format(uri, n, n, n);

        log.info("uri = {}", uri);

        int i = 0;
        while (i++ < REPEATER) {
            if (i == REPEATER - 1) {
                // i = 0;
                log.debug("URI: {}", uri);
            }

            String response = restTemplate.getForObject(uri, String.class);
            // String response = restTemplate.getForObject(uri + i, String.class);
            log.debug(response);
        }

    }

    // @Test
    public void testSocket2()
        throws Exception {

        // String host = "localhost";
        String host = "thanksthanks.ddns.net";
        // int port = 8080;
        int port = 80;
        // int port = 443;

        String getPage = "GET /r.php?t=c&d=%d&l=%d&c=%d HTTP/1.1";
        Random rand = new Random();
        int n = rand.nextInt();

        getPage = String.format(getPage, n, n, n);

        Socket socket = new Socket(host, port);

        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        printWriter.println(getPage);
        printWriter.println("Host: " + host);
        printWriter.println();
        printWriter.flush();

        /*
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        StringBuilder sb = new StringBuilder();
        for (String response = null; (response = bufferedReader.readLine()) != null;) {
            sb.append(response).append(System.lineSeparator());
            log.debug(response);
        }
        log.debug(sb.toString());
        bufferedReader.close();
        */

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        for (int ch = 0; (ch = inputStreamReader.read()) != -1;) {
            System.out.print((char) ch);
        }

        log.debug("Outside of reading loop.");

        printWriter.close();
        log.debug("Request stream closed.");

        inputStreamReader.close();
        log.debug("Response stream closed.");

        socket.close();
        log.debug("Socket closed.");

    }

    @Test
    public void testSocket()
        throws Exception {

        // String host = "localhost";
        String host = "thanksthanks.ddns.net";
        // int port = 8080;
        int port = 80;
        // int port = 443;

        String getPage = "GET /r.php?t=c&d=%d&l=%d&c=%d HTTP/1.1";

        for (int i = 0; i < 100_000_000; i++) {
            /*
            Random rand = new Random(System.currentTimeMillis());
            int n1 = rand.nextInt(10_000);
            int n2 = rand.nextInt(10_000);
            int n3 = rand.nextInt(10_000);
            */
            int n1 = ThreadLocalRandom.current().nextInt(1_000);
            int n2 = ThreadLocalRandom.current().nextInt(1_000);
            int n3 = ThreadLocalRandom.current().nextInt(1_000);

            getPage = String.format(getPage, n1, n2, n3);

            try {
                doSocket(host, port, getPage);
            } catch (Exception e) {
                log.error("Unable to doSocket. List size: {}", SOCKET_LIST.size());
            }
        }

        /*
        printWriter.close();
        log.debug("Request stream closed.");

        inputStreamReader.close();
        log.debug("Response stream closed.");

        socket.close();
        log.debug("Socket closed.");
        */

    }

    private void doSocket(String host, int port, String getPage)
        throws Exception {

        Socket socket = new Socket(host, port);
        SOCKET_LIST.add(socket);

        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        printWriter.println(getPage);
        printWriter.println("Host: " + host);
        // printWriter.println();
        // printWriter.flush();

        /*
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        StringBuilder sb = new StringBuilder();
        for (String response = null; (response = bufferedReader.readLine()) != null;) {
            sb.append(response).append(System.lineSeparator());
            log.debug(response);
        }
        log.debug(sb.toString());
        bufferedReader.close();
        */

        /*
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        for (int ch = 0; (ch = inputStreamReader.read()) != -1;) {
            System.out.print((char) ch);
        }

        log.debug("Outside of reading loop.");
        */
    }

}
