package com.example.productservice.query;

import com.example.productservice.entity.Product;
import com.example.productservice.event.ProductCreatedEvent;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
@RequiredArgsConstructor
public class ProductEventsHandler {

    private final ProductRepository productRepository;

    // this @ExceptionHandler from axonframework interceptors can handle any exception only within this class, and not globally
    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception) {
        // log error message
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception) {
        // log error message
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        Product product = new Product();
        BeanUtils.copyProperties(event, product);
        try {
            productRepository.save(product);
        } catch (IllegalArgumentException e) {
            // log error message
            e.printStackTrace();
        }
    }
}
