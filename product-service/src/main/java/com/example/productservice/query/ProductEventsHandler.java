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
    public void handle(Exception exception) throws Exception {
        // log error message
        // instead of just log it, we can rethrow the exception
        // since we rethrow the exception, can handle it by creating ProductServiceEventsErrorHandler implements ListenerInvocationErrorHandler
        throw exception;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception) {
        // log error message
    }

    @EventHandler
    public void on(ProductCreatedEvent event) throws Exception {
        Product product = new Product();
        BeanUtils.copyProperties(event, product);
        try {
            productRepository.save(product);
        } catch (IllegalArgumentException e) {
            // log error message
            e.printStackTrace();
        }
        throw new Exception("Forcing an exception in the event handler class");
    }
}
