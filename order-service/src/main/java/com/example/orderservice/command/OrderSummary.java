package com.example.orderservice.command;

import lombok.Value;

@Value
public class OrderSummary {
    String orderId;
    OrderStatus orderStatus;
}
