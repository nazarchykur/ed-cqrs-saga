package com.example.productservice.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {
    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        // Validate the command
        if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        if (createProductCommand.getTitle() == null || createProductCommand.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }
}
