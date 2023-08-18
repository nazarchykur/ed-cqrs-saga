package com.example.productservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @Min(value = 1, message = "Price must be greater than 0")
    private BigDecimal price;

    @Min(value = 1, message = "Quantity must be greater than 0")
    @Max(value = 5, message = "Quantity must be less than 5")
    private Integer quantity;
}
