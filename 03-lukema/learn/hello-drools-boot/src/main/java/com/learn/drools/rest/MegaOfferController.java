package com.learn.drools.rest;


import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learn.drools.model.Order;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
public class MegaOfferController {
    @Autowired
    private KieSession kieSession;

    /**
     *  curl -H "Content-Type: application/json" -X POST http://localhost:8080/order -d '{"cardType": "HDFC", "price": 900}'
     *  curl -H "Content-Type: application/json" -X POST http://localhost:8080/order -d '{"cardType": "HDFC", "price": 11000}'
     *  curl -H "Content-Type: application/json" -X POST http://localhost:8080/order -d '{"cardType": "ICICI", "price": 16000}'
     *  curl -H "Content-Type: application/json" -X POST http://localhost:8080/order -d '{"cardType": "DBS", "price": 16000}'
     */
    @PostMapping("/order")
    public ResponseEntity<Order> orderNow(@RequestBody Order order) {

        log.debug("ordr: {}", () -> order);

        kieSession.insert(order);
        kieSession.fireAllRules();

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
