package com.learn.shein.mongo.config;


import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Component
public class PropertyHolder {

    public static String Static_File_Location_OS = null;

    /**
     * constructor injection using @Value
     */
    public PropertyHolder(@Value("${spring.web.resources.static-locations:}") String staticFileLocation) {

        String os = System.getProperty("os.name");

        if (os.toLowerCase().contains("windows")) {
            Static_File_Location_OS = staticFileLocation.replace("file:///", "");
        } else {
            Static_File_Location_OS = staticFileLocation.replace("file://", "");
        }

        log.debug("*************** os: {}, file: {}", () -> os, () -> Static_File_Location_OS);

        try {
            File dir = new File(Static_File_Location_OS);

            boolean exists = dir.exists();
            log.debug("dir {} exists: {}", Static_File_Location_OS, exists);

            if (!exists) {
                dir.mkdirs();
                exists = dir.exists();
                log.debug("After mkdirs(), dir {} exists: {}", Static_File_Location_OS, exists);
            }
        } catch (Exception e) {
            log.error("Exception creating {} dir", Static_File_Location_OS, e);
        }
    }
}
