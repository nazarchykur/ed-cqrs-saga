package com.example.orderservice.event;

import com.example.orderservice.command.OrderStatus;
import lombok.Value;

@Value
public class OrderRejectedEvent {
    String orderId;
    String reason;
    OrderStatus orderStatus = OrderStatus.REJECTED;
}

