package com.example.productservice.command;

import com.example.productservice.event.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {

    @EventHandler
    public void on(ProductCreatedEvent event) {

    }
}
