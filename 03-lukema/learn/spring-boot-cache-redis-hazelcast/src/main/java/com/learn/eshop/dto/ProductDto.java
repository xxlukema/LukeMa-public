package com.learn.eshop.dto;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class ProductDto
    implements Serializable {
    private static final long serialVersionUID = 1L;
    private String productId;
    private String name;
    private double price;
    private String description;
}
