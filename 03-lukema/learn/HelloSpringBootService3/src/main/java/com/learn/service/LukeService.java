package com.learn.service;


import java.util.Date;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class LukeService {

    private final LukeNestedService lukeNestedService;

    public String getDate() {
        return String.format("%s from %s", new Date().toString(), this.lukeNestedService.getName());
    }
}
