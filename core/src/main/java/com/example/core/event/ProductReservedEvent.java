package com.example.core.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReservedEvent {
    private final String productId;
    private final Integer quantity;
    private final String orderId;
    private final String userId;
}
