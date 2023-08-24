package com.example.core.command;

import com.example.core.model.PaymentDetails;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class ProcessPaymentCommand {
    @TargetAggregateIdentifier
    private final String paymentId;
    public final String orderId;
    private final PaymentDetails paymentDetails;
}
