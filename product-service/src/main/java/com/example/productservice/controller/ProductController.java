package com.example.productservice.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public String getProducts() {
        return "Get Request: Get All Products";
    }

    @PostMapping
    public String createProduct() {
        return "Post Request: Product Created";
    }

    @DeleteMapping
    public String deleteProduct() {
        return "Delete Request: Product Deleted";
    }
}
