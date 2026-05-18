package com.learn.poster;


import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.learn.poster.junit.utils.PostUtils;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class Post02Tester {

    /**
     * http://tinyurl.com/yckwqbwr/r.php?t=c&d=21401&l=521&c=4797
     */
    private final String HOST = "tinyurl.com";
    private final int PORT = 80;
    private final String GET_PAGE = "GET /yckwqbwr/r.php?t=c&d=%d&l=%d&c=%d HTTP/1.1";

    private static final List<Socket> SOCKET_LIST = new ArrayList<>();

    // @Disabled
    @Test
    public void testSocket()
        throws Exception {
        log.info("Start Test. {}", () -> HOST);
        PostUtils.openSocketAndSendData(HOST, PORT, GET_PAGE, SOCKET_LIST);
    }
}
