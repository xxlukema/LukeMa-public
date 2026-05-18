package com.learn.poster.junit.utils;


import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class PostUtils {

    private static final boolean VERBOSE = true;

    private static final int LINE_WIDTH = 60;

    private static final boolean SHOW_STACKTRACE = true;

    private static final AtomicInteger CHAR_COUNTER = new AtomicInteger(0);

    private static final AtomicLong FAILURE_COUNTER = new AtomicLong(0);

    private static final NumberFormat Numbe_Format = NumberFormat.getNumberInstance(Locale.US);

    public static void openSocketAndSendData(String host, int port, String page, List<Socket> list)
        throws Exception {

        int n1 = ThreadLocalRandom.current().nextInt(1_000);
        int n2 = ThreadLocalRandom.current().nextInt(1_000);
        int n3 = ThreadLocalRandom.current().nextInt(1_000);
        String getPage = String.format(page, n1, n2, n3);

        if (VERBOSE) {
            if (CHAR_COUNTER.getAndIncrement() > LINE_WIDTH) {
                CHAR_COUNTER.set(0);
                System.out.println();
                log.info("Host: {}, Page: {}, List size: {}, failure: {}", host, getPage, Numbe_Format.format(list.size()), Numbe_Format.format(FAILURE_COUNTER.get()));
            }
            System.out.print(".");
        }

        try {
            openSocket(host, port, getPage, list);

            if (VERBOSE) {
                System.out.print("+");
            }
        } catch (Exception e) {
            FAILURE_COUNTER.getAndIncrement();
            String msg = e.getMessage();
            boolean skip = false;
            if (VERBOSE) {
                if (msg != null) {
                    if (msg.contains("Connection timed out")) {
                        System.out.print("t");
                        skip = true;
                    }
                } else {
                    System.out.print("?");
                }

                if (CHAR_COUNTER.getAndIncrement() > LINE_WIDTH) {
                    CHAR_COUNTER.set(0);
                    System.out.println();
                }
            }

            if (SHOW_STACKTRACE) {
                if (!skip) {
                    log.error("Unable to open socket. Host: {}, Page: {}, List size: {}", host, getPage, Numbe_Format.format(list.size()), e);
                }
            } else {
                log.error("Unable to open socket. Host: {}, Page: {}, List size: {}", host, getPage, Numbe_Format.format(list.size()));
            }
        }

        /*
        printWriter.close();
        log.debug(() -> "Request stream closed.");
        
        inputStreamReader.close();
        log.debug(() -> "Response stream closed.");
        
        socket.close();
        log.debug(() -> "Socket closed.");
        */

    }

    private static void openSocket(String host, int port, String getPage, List<Socket> list)
        throws Exception {

        Socket socket = new Socket(host, port);
        list.add(socket);

        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        printWriter.println(getPage);
        printWriter.println("Host: " + host);
        printWriter.flush();
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
        
        log.debug(() -> "Outside of reading loop.");
        */
    }

}
