package com.example.core.event;

import lombok.Value;

@Value
public class PaymentProcessedEvent {
    private final String orderId;
    private final String paymentId;
}
