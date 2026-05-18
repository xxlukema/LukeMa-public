package com.learn.eshop.repository;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.stereotype.Repository;

import com.learn.eshop.dto.ProductDto;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Repository
public class OrderRepository {

    private static final Map<String, String> orders = new HashMap<>();
    private static final AtomicInteger ORDER_ID = new AtomicInteger(100);
    private final ProductCacheableRepository productRepository;

    public OrderRepository(ProductCacheableRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Map<String, ProductDto> getOrders()
        throws InterruptedException {
        log.info(() -> "Calling service to get Orders data...");
        Map<String, ProductDto> ordersWithDto = new HashMap<>();
        for (Map.Entry<String, String> entry : orders.entrySet()) {
            productRepository.getProduct(entry.getValue()).ifPresent(p -> ordersWithDto.put(entry.getKey(), p));
        }
        return ordersWithDto;
    }

    public ProductDto getOrder(String orderId)
        throws InterruptedException {
        log.info(() -> "Calling service to get Orders data...");
        return productRepository.getProduct(orders.get(orderId))
                .orElseThrow(() -> new RuntimeException("OrderId not found: " + orderId));
    }

    public String addOrder(String productId) {
        String orderId = "O" + ORDER_ID.getAndIncrement();
        Optional<ProductDto> product = productRepository.getProduct(productId);
        if (product.isPresent()) {
            orders.put(orderId, product.get().getProductId());
            return orderId;
        } else {
            throw new RuntimeException("Invalid Product ID: " + productId);
        }
    }

    public ProductDto updateOrder(String orderId, String productId) {
        Optional<ProductDto> product = productRepository.getProduct(productId);
        if (product.isPresent()) {
            orders.put(orderId, product.get().getProductId());
            return product.get();
        } else {
            throw new OpenApiResourceNotFoundException("Invalid Product ID: " + productId);
        }
    }
}
