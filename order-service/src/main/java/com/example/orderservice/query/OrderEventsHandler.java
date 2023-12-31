package com.example.orderservice.query;

import com.example.orderservice.entity.Order;
import com.example.orderservice.event.OrderApprovedEvent;
import com.example.orderservice.event.OrderCreatedEvent;
import com.example.orderservice.event.OrderRejectedEvent;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("order-group")
@RequiredArgsConstructor
public class OrderEventsHandler {

    private final OrderRepository orderRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        Order order = new Order();
        BeanUtils.copyProperties(event, order);
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderApprovedEvent event) {
        orderRepository.findByOrderId(event.getOrderId())
                .ifPresentOrElse(order -> {
                    order.setOrderStatus(event.getOrderStatus());
                    orderRepository.save(order);
                },
                () -> {
                    // TODO: do something when order not found
                });
    }

    @EventHandler
    public void on(OrderRejectedEvent event) {
        orderRepository.findByOrderId(event.getOrderId())
                .ifPresent(order -> {
                    order.setOrderStatus(event.getOrderStatus());
                    orderRepository.save(order);
                });
    }
}
