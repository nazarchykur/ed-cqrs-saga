package com.example.orderservice.query;

import com.example.orderservice.command.OrderSummary;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderQueriesHandler {

    private final OrderRepository orderRepository;

    @QueryHandler
    public OrderSummary findOrder(FindOrderQuery findOrderQuery) {
        return orderRepository.findByOrderId(findOrderQuery.getOrderId())
                .map(order -> new OrderSummary(order.getOrderId(), order.getOrderStatus(), ""))
                .orElse(null);
    }
}
