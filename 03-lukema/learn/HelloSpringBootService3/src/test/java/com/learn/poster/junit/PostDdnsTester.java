package com.learn.poster.junit;


import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import com.learn.poster.junit.utils.PostUtils;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Execution(ExecutionMode.CONCURRENT)
public class PostDdnsTester {

    /**
     * http://thanksthanks.ddns.net/r.php?t=c&d=21401&l=521&c=4797
     */
    private final String HOST = "thanksthanks.ddns.net";
    private final int PORT = 80;
    private final String GET_PAGE = "GET /r.php?t=c&d=%d&l=%d&c=%d HTTP/1.1";

    private static final List<Socket> SOCKET_LIST = new ArrayList<>();

    @Test
    public void testSocket0()
        throws Exception {
        log.info("Start Test. {}", () -> HOST);
        PostUtils.openSocketAndSendData(HOST, PORT, GET_PAGE, SOCKET_LIST);
    }

    @Test
    public void testSocket1()
        throws Exception {
        log.info("Start Test. {}", () -> HOST);
        PostUtils.openSocketAndSendData(HOST, PORT, GET_PAGE, SOCKET_LIST);
    }

    @Test
    public void testSocket2()
        throws Exception {
        log.info("Start Test. {}", () -> HOST);
        PostUtils.openSocketAndSendData(HOST, PORT, GET_PAGE, SOCKET_LIST);
    }

    @Test
    public void testSocket3()
        throws Exception {
        log.info("Start Test. {}", () -> HOST);
        PostUtils.openSocketAndSendData(HOST, PORT, GET_PAGE, SOCKET_LIST);
    }

    @Test
    public void testSocket4()
        throws Exception {
        log.info("Start Test. {}", () -> HOST);
        PostUtils.openSocketAndSendData(HOST, PORT, GET_PAGE, SOCKET_LIST);
    }

    @Test
    public void testSocket5()
        throws Exception {
        log.info("Start Test. {}", () -> HOST);
        PostUtils.openSocketAndSendData(HOST, PORT, GET_PAGE, SOCKET_LIST);
    }

    @Test
    public void testSocket6()
        throws Exception {
        log.info("Start Test. {}", () -> HOST);
        PostUtils.openSocketAndSendData(HOST, PORT, GET_PAGE, SOCKET_LIST);
    }

    @Test
    public void testSocket7()
        throws Exception {
        log.info("Start Test. {}", () -> HOST);
        PostUtils.openSocketAndSendData(HOST, PORT, GET_PAGE, SOCKET_LIST);
    }

    @Test
    public void testSocket8()
        throws Exception {
        log.info("Start Test. {}", () -> HOST);
        PostUtils.openSocketAndSendData(HOST, PORT, GET_PAGE, SOCKET_LIST);
    }

    @Test
    public void testSocket9()
        throws Exception {
        log.info("Start Test. {}", () -> HOST);
        PostUtils.openSocketAndSendData(HOST, PORT, GET_PAGE, SOCKET_LIST);
    }

}
