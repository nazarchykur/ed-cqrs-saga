package com.example.productservice.controller;

import com.example.productservice.dto.CreateProductCommand;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final Environment environment;
    private final CommandGateway commandGateway;

    public ProductController(Environment environment, CommandGateway commandGateway) {
        this.environment = environment;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProduct(@Valid @RequestBody CreateProductCommand productDto) {
        com.example.productservice.command.CreateProductCommand createProductCommand = com.example.productservice.command.CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build();

        return commandGateway.sendAndWait(createProductCommand);
    }

//    @GetMapping
//    public String getProducts() {
//        return "Get Request: Get All Products " + environment.getProperty("local.server.port");
//    }
//
//    @DeleteMapping
//    public String deleteProduct() {
//        return "Delete Request: Product Deleted";
//    }
}
