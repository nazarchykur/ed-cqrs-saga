package com.example.productservice.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
