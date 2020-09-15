package com.gladunalexander.orderservice.saga;

import com.gladunalexander.core.commands.CreateInvoiceCommand;
import com.gladunalexander.core.commands.CreateShippingCommand;
import com.gladunalexander.core.commands.UpdateOrderStatusCommand;
import com.gladunalexander.core.events.InvoiceCreatedEvent;
import com.gladunalexander.core.events.OrderCreatedEvent;
import com.gladunalexander.core.events.OrderShippedEvent;
import com.gladunalexander.core.events.OrderUpdatedEvent;
import com.gladunalexander.orderservice.domain.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderManagementSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        String paymentId = UUID.randomUUID().toString();
        log.info("Saga invoked ...order created....");

        //associate Saga
        SagaLifecycle.associateWith("paymentId", paymentId);

        log.info("order id: {}", orderCreatedEvent.getOrderId());

        //send the commands
        commandGateway.send(
                new CreateInvoiceCommand(paymentId, orderCreatedEvent.getOrderId())
        );

        log.info("Dispathed  CreateInvoiceCommand");
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(InvoiceCreatedEvent invoiceCreatedEvent) {
        String shippingId = UUID.randomUUID().toString();

        log.info("Saga continued ..... InvoiceCreatedEvent");

        //associate Saga with shipping
        SagaLifecycle.associateWith("shippingId", shippingId);

        //send the create shipping command
        commandGateway.send(
                new CreateShippingCommand(
                        shippingId,
                        invoiceCreatedEvent.getOrderId(),
                        invoiceCreatedEvent.getPaymentId()
                )
        );
        log.info("Dispathed  CreateShippingCommand");
    }


    @SagaEventHandler(associationProperty = "shippingId")
    public void handle(OrderShippedEvent orderShippedEvent) {
        log.info("Saga continued ..... OrderShippedEvent");

        SagaLifecycle.associateWith("orderId", orderShippedEvent.getOrderId());
        commandGateway.send(new UpdateOrderStatusCommand(orderShippedEvent.getOrderId(), String.valueOf(OrderStatus.SHIPPED)));

    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderUpdatedEvent orderUpdatedEvent) {
        log.info("Saga Ended....");
        SagaLifecycle.end();
    }
}
