package com.learn.lombok;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;


@Log4j2
@AllArgsConstructor
@Data
public class LombokDemo {

    private Long id;
    private String description;

    public static void main(String[] args) {

        LombokDemo lombokDemo = new LombokDemo(1L, "desc");

        log.info("Hello {}", () -> lombokDemo);
    }

}
