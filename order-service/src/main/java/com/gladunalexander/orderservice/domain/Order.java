package com.gladunalexander.orderservice.domain;

import com.gladunalexander.core.commands.CreateOrderCommand;
import com.gladunalexander.core.commands.UpdateOrderStatusCommand;
import com.gladunalexander.core.events.OrderCreatedEvent;
import com.gladunalexander.core.events.OrderUpdatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

@Slf4j
@Aggregate
@NoArgsConstructor
public class Order {

    @AggregateIdentifier
    private String orderId;

    private ItemType itemType;

    private BigDecimal price;

    private String currency;

    private OrderStatus orderStatus;

    @CommandHandler
    public Order(CreateOrderCommand createOrderCommand) {
        log.info("executing  CreateOrderCommand");

        AggregateLifecycle.apply(
                new OrderCreatedEvent(
                        createOrderCommand.getOrderId(),
                        createOrderCommand.getItemType(),
                        createOrderCommand.getPrice(),
                        createOrderCommand.getCurrency(),
                        createOrderCommand.getOrderStatus()
                )
        );

        log.info("Dispatching OrderCreatedEvent");
    }

    @EventSourcingHandler
    protected void on(OrderCreatedEvent orderCreatedEvent) {
        log.info("Received OrderCreatedEvent ");
        this.orderId = orderCreatedEvent.getOrderId();
        this.itemType = ItemType.valueOf(orderCreatedEvent.getItemType());
        this.price = orderCreatedEvent.getPrice();
        this.currency = orderCreatedEvent.getCurrency();
        this.orderStatus = OrderStatus.valueOf(orderCreatedEvent.getOrderStatus());
    }

    @CommandHandler
    protected void on(UpdateOrderStatusCommand updateOrderStatusCommand) {
        AggregateLifecycle.apply(
                new OrderUpdatedEvent(
                        updateOrderStatusCommand.getOrderId(),
                        updateOrderStatusCommand.getOrderStatus()
                )
        );
    }

    @EventSourcingHandler
    protected void on(OrderUpdatedEvent orderUpdatedEvent) {
        log.info("Received OrderUpdatedEvent ");
        this.orderStatus = OrderStatus.valueOf(orderUpdatedEvent.getOrderStatus());
    }
}
