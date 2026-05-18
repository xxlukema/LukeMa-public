package com.learn.rest.controller;


import jakarta.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.pojo.ChildPojo;
import com.learn.pojo.NestedPojo;
import com.learn.service.LukeService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/luke", produces = { MediaType.APPLICATION_JSON })
@Log4j2
public class LukeController {
  private final LukeService lukeService;

  private static final String BODY_FORMAT = """
      {
        "memberId": "%s",
        "date": {
          "str": "%s"
        }
      }
      """;

  @GetMapping(value = "/lookup/{memberId}")
  public ResponseEntity<String> getMethodName(@PathVariable String memberId) {
    log.debug("memberId: {}", memberId);

    var body = String.format(BODY_FORMAT, memberId, this.lukeService.getDate());

    return new ResponseEntity<>(body, HttpStatus.OK);
  }

  @GetMapping(value = "/nested")
  public ResponseEntity<NestedPojo> getNestedMethod() {
    log.debug("getNestedMethod called");

    var nestedPojo = new NestedPojo();
    nestedPojo.setField1("Name of nested");
    nestedPojo.setField2(2);
    nestedPojo.setChildPojo(new ChildPojo("Name of child", 3));

    return new ResponseEntity<>(nestedPojo, HttpStatus.OK);
  }

}
