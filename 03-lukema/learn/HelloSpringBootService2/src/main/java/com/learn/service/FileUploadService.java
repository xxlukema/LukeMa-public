package com.learn.service;


import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class FileUploadService {

    public void sayHello(String name) {
        log.info("Hello {} from FileUploadService", () -> name);
    }

}
