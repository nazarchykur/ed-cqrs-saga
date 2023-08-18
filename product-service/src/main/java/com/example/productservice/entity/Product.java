package com.example.productservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @Column(unique = true)
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
