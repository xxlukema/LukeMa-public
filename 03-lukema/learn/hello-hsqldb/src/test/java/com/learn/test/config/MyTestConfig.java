package com.learn.test.config;


import java.io.IOException;
import java.sql.SQLException;

import org.hsqldb.server.ServerAcl.AclFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.DependsOn;

import lombok.extern.log4j.Log4j2;


@TestConfiguration
@DependsOn("hsqldbInitConfig")
@Log4j2
public class MyTestConfig {

    @Autowired
    public void autowired()
        throws ClassNotFoundException, IOException, AclFormatException, SQLException {
        log.info(() -> "Inside MyTestConfig.autowired()");
    }

}
