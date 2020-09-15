package com.gladunalexander.shipmentservice.domain;

import com.gladunalexander.core.commands.CreateShippingCommand;
import com.gladunalexander.core.events.OrderShippedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Slf4j
@Aggregate
@NoArgsConstructor
public class ShippingAggregate {

    @AggregateIdentifier
    private String shippingId;

    private String orderId;

    private String paymentId;

    @CommandHandler
    public ShippingAggregate(CreateShippingCommand createShippingCommand) {

        log.info("Executing CreateShippingCommand");
        AggregateLifecycle.apply(
                new OrderShippedEvent(
                        createShippingCommand.getShippingId(),
                        createShippingCommand.getOrderId(),
                        createShippingCommand.getPaymentId()
                )
        );
        log.info("Dispatched  OrderShippedEvent");
    }

    @EventSourcingHandler
    protected void on(OrderShippedEvent orderShippedEvent) {
        this.shippingId = orderShippedEvent.getShippingId();
        this.orderId = orderShippedEvent.getOrderId();
    }
}
