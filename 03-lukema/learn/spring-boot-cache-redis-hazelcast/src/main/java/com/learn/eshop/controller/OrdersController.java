package com.learn.eshop.controller;


import java.util.Map;

import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.eshop.dto.ProductDto;
import com.learn.eshop.repository.OrderRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("v1/")
public class OrdersController {

    private final OrderRepository orderRepository;

    public OrdersController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping(value = "/orders", produces = APPLICATION_JSON_VALUE)
    public Map<String, ProductDto> orders()
        throws InterruptedException {
        return orderRepository.getOrders();
    }

    @Operation(summary = "Get Order", description = "Get Order Details", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Order", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content) })
    @GetMapping(value = "/orders/{orderId}", produces = APPLICATION_JSON_VALUE)
    public ProductDto orderById(@PathVariable String orderId)
        throws InterruptedException {
        return orderRepository.getOrder(orderId);
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<String> addOrder(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.addOrder(productDto.getProductId()));
    }

    @PutMapping(value = "/orders/{orderId}")
    public ResponseEntity<ProductDto> updateOrder(@PathVariable String orderId, @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderRepository.updateOrder(orderId, productDto.getProductId()));
    }
}
