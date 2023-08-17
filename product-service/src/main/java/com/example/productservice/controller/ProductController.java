package com.example.productservice.controller;

import com.example.productservice.command.CreateProductCommand;
import com.example.productservice.model.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private Environment environment;

    @GetMapping
    public String getProducts() {
        return "Get Request: Get All Products " + environment.getProperty("local.server.port");
    }

    @PostMapping
    public String createProduct(@RequestBody ProductDto productDto) {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build();

        return "Post Request: Product Created with title: " + productDto.getTitle();
    }

    @DeleteMapping
    public String deleteProduct() {
        return "Delete Request: Product Deleted";
    }
}
