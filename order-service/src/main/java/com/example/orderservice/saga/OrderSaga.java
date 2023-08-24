package com.example.orderservice.saga;

import com.example.core.command.CancelProductReservationCommand;
import com.example.core.command.ProcessPaymentCommand;
import com.example.core.command.ReserveProductCommand;
import com.example.core.event.PaymentProcessedEvent;
import com.example.core.event.ProductReservationCancelledEvent;
import com.example.core.event.ProductReservedEvent;
import com.example.core.model.User;
import com.example.core.query.FetchUserPaymentDetailsQuery;
import com.example.orderservice.command.ApproveOrderCommand;
import com.example.orderservice.command.RejectOrderCommand;
import com.example.orderservice.event.OrderApprovedEvent;
import com.example.orderservice.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Saga
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(event.getOrderId())
                .productId(event.getProductId())
                .quantity(event.getQuantity())
                .userId(event.getUserId())
                .build();

        log.info("OrderCreatedEvent handled for orderId: " + event.getOrderId() + " and productId: " + event.getProductId());

        commandGateway.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                // start a compensation transaction
            }
        });
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent) {
        // process user payment
        log.info("ProductReservedEvent is called for orderId: " + productReservedEvent.getOrderId() + " and productId: " + productReservedEvent.getProductId());

        FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery = new FetchUserPaymentDetailsQuery(productReservedEvent.getUserId());

        User userPaymentDetails;
        try {
            userPaymentDetails = queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
        } catch (Exception e) {
            log.error(e.getMessage());
            // start a compensation transaction
            cancelProductReservation(productReservedEvent, e.getMessage());
            return;
        }
        if (userPaymentDetails == null) {
            // start a compensation transaction
            cancelProductReservation(productReservedEvent, "Could not fetch user payment details");
            return;
        }

        log.info("Successfully fetched user payment details for user: " + userPaymentDetails.getFirstName());

        ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
                .orderId(productReservedEvent.getOrderId())
                .paymentDetails(userPaymentDetails.getPaymentDetails())
                .paymentId(UUID.randomUUID().toString())
                .build();

        String result = null;
        try {
            result = commandGateway.sendAndWait(processPaymentCommand, 10, TimeUnit.SECONDS );
        } catch (Exception e) {
            log.error(e.getMessage());
            // start a compensation transaction
            cancelProductReservation(productReservedEvent, e.getMessage());
            return;
        }
        if (result == null) {
            // start a compensation transaction
            cancelProductReservation(productReservedEvent, "Could not process payment with provided user payment details");
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent paymentProcessedEvent) {
        // send an ApproveOrderCommand
        ApproveOrderCommand approveOrderCommand = new ApproveOrderCommand(paymentProcessedEvent.getOrderId());

        commandGateway.send(approveOrderCommand);
    }

    @EndSaga // 1 way
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent orderApprovedEvent) {
        log.info("Order is approved. Order Saga is completed for orderId: " + orderApprovedEvent.getOrderId());
        // SagaLifecycle.end(); // 2 way
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservationCancelledEvent productReservationCancelledEvent) {
        // create and send a RejectOrderCommand
        RejectOrderCommand rejectOrderCommand = new RejectOrderCommand(productReservationCancelledEvent.getOrderId(), productReservationCancelledEvent.getReason());

        commandGateway.send(rejectOrderCommand);
    }

    private void cancelProductReservation(ProductReservedEvent productReservedEvent, String reason) {
        CancelProductReservationCommand cancelProductReservationCommand = CancelProductReservationCommand.builder()
                .orderId(productReservedEvent.getOrderId())
                .productId(productReservedEvent.getProductId())
                .quantity(productReservedEvent.getQuantity())
                .userId(productReservedEvent.getUserId())
                .reason(reason)
                .build();

        commandGateway.send(cancelProductReservationCommand);
    }
}
